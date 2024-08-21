package com.petweb.sponge.oauth2.service;

import com.petweb.sponge.oauth2.dto.CustomOAuth2User;
import com.petweb.sponge.oauth2.dto.GoogleResponse;
import com.petweb.sponge.oauth2.dto.LoginAuth;
import com.petweb.sponge.oauth2.dto.OAuth2Response;
import com.petweb.sponge.trainer.domain.Trainer;
import com.petweb.sponge.trainer.repository.TrainerRepository;
import com.petweb.sponge.user.domain.User;
import com.petweb.sponge.user.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;
    private final HttpServletRequest request;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;

        if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        //훈련사인지 유저로그인인지 구분 가져오기
        String loginType = getCookieValue("loginType");
        String email = oAuth2Response.getEmail();


        //훈련사로 로그인시
        if (Objects.equals(loginType, "trainer")) {
            Trainer existData = trainerRepository.findByEmail(email).orElse(null);
            //첫 로그인일 시
            if (existData == null) {
                Trainer trainer = Trainer.builder()
                        .email(email)
                        .name(oAuth2Response.getName()).build();
                Trainer savedTrainer = trainerRepository.save(trainer);

                LoginAuth loginAuth = createLoginAuth(savedTrainer.getId(), oAuth2Response.getName());
                return new CustomOAuth2User(loginAuth);
            }
            //이미 정보가 있을 시
            else {
                LoginAuth loginAuth = createLoginAuth(existData.getId(), oAuth2Response.getName());
                return new CustomOAuth2User(loginAuth);
            }
        }


        //일반 유저로 로그인시
        else if (Objects.equals(loginType, "user")) {
            User existData = userRepository.findByEmail(email).orElse(null);

            //첫 로그인일 시
            if (existData == null) {
                User user = User.builder()
                        .email(email)
                        .name(oAuth2Response.getName())
                        .build();
                User savedUser = userRepository.save(user);
                LoginAuth loginAuth = createLoginAuth(savedUser.getId(), oAuth2Response.getName());
                return new CustomOAuth2User(loginAuth);
            }
            //이미 정보가 있을 시
            else {
                LoginAuth loginAuth = createLoginAuth(existData.getId(), oAuth2Response.getName());
                return new CustomOAuth2User(loginAuth);
            }
        } else {
            return null;
        }

    }

    private String getCookieValue(String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null; // 쿠키가 없으면 null 반환
    }

    /**
     * LoginAuth 객체 만들기
     * @param id
     * @param name
     * @return
     */
    private LoginAuth createLoginAuth(Long id,String name) {
        return LoginAuth.builder()
                .id(id)
                .name(name)
                .build();
    }
}
