package com.petweb.sponge.jwt;

import com.petweb.sponge.exception.error.NotFoundAnswer;
import com.petweb.sponge.oauth2.dto.CustomOAuth2User;
import com.petweb.sponge.oauth2.dto.LoginAuth;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Value("${spring.jwt.expire-length}")
    private long expireLong;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String requestUri = request.getRequestURI();

        if (requestUri.matches("^\\/login(?:\\/.*)?$")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (requestUri.matches("^\\/oauth2(?:\\/.*)?$")) {
            filterChain.doFilter(request, response);
            return;
        }

        //cookie들을 불러온 뒤 token Key에 담긴 쿠키를 찾음
        String authorization = null;
        Cookie[] cookies = request.getCookies();
        /**
         * P: early return을 쓰는게 어떨까요?
         * if (cookies == null) throw ~~
         */
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("Authorization")) {
                authorization = cookie.getValue();
            }
        }

        //Authorization 검증
        if (authorization == null) {
            log.info("token is null!");
            filterChain.doFilter(request, response);
            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        //토큰
        String token = authorization;

        //토큰 소멸 시간 검증
        try {
            jwtUtil.isExpired(token);
        } catch (ExpiredJwtException e) {
            log.info("token is expired!");
            log.info("jwt error: {}", e.getMessage());

            //토큰쿠키 삭제
            Cookie newToken = new Cookie("Authorization", null);
            newToken.setMaxAge(0);
            newToken.setPath("/");

            response.addCookie(newToken);
            throw new NotFoundAnswer();
            //자동 로그아웃되면 다시 로그인할 경로 재설정
//            // TODO 경로 체크 필요
              // TODO 토큰 만료시 어떻게 받을지
//            response.sendRedirect("/login");
//            return;
        }


        //토큰에서 id 획득
        Long id = jwtUtil.getId(token);
        String loginType = jwtUtil.getLoginType(token);

        LoginAuth loginAuth = LoginAuth.builder()
                .id(id)
                .loginType(loginType)
                .build();

        //UserDetails에 회원 정보 객체 담기
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(loginAuth);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());

        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
