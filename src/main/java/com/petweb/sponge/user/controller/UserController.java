package com.petweb.sponge.user.controller;

import com.petweb.sponge.auth.UserAuth;
import com.petweb.sponge.exception.error.LoginIdError;
import com.petweb.sponge.user.dto.UserDTO;
import com.petweb.sponge.user.dto.UserDetailDTO;
import com.petweb.sponge.user.dto.UserUpdateDTO;
import com.petweb.sponge.user.service.UserService;
import com.petweb.sponge.utils.AuthorizationUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.http.auth.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final AuthorizationUtil authorizationUtil;

    /**
     * 유저 단건조회
     *
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDetailDTO> getUser(@PathVariable("userId") Long userId) {
        UserDetailDTO user = userService.findUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * 자신의 계정정보를 불러옴
     *
     * @return
     */
    @GetMapping("/my_info")
    @UserAuth
    public ResponseEntity<UserDetailDTO> getMyInfo() {
        UserDetailDTO user = userService.findMyInfo(authorizationUtil.getLoginId());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * 유저 정보 저장
     *
     * @param userDTO
     * @return
     */
    @PostMapping()
    @UserAuth
    public void signup(@RequestBody UserDTO userDTO) {
        userService.saveUser(authorizationUtil.getLoginId(), userDTO);

    }

    /**
     * 유저 정보 수정
     *
     * @param userId
     */
    @PatchMapping("/{userId}")
    @UserAuth
    public void updateUser(@PathVariable("userId") Long userId, @RequestBody UserUpdateDTO userUpdateDTO) {
        if (authorizationUtil.getLoginId().equals(userId)) {
            userService.updateUser(userId, userUpdateDTO);
        } else {
            throw new LoginIdError();
        }
    }

    /**
     * 회원탈퇴
     *
     * @param userId
     * @param response
     */
    @DeleteMapping("/{userId}")
    @UserAuth
    public void removeUser(@PathVariable("userId") Long userId, HttpServletResponse response) {
        if (authorizationUtil.getLoginId().equals(userId)) {
            userService.deleteUser(userId);
        } else {
            throw new LoginIdError();
        }

        Cookie cookie = new Cookie("Authorization", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.setStatus(200);
    }

}
