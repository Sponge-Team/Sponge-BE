package com.petweb.sponge.user.controller;

import com.petweb.sponge.user.dto.UserDTO;
import com.petweb.sponge.user.service.UserService;
import com.petweb.sponge.utils.AuthorizationUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final AuthorizationUtil authorizationUtil;

    /**
     * 유저 단건조회
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("userId") Long userId) {
        UserDTO user = userService.findUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * 유저 정보 저장
     * @param userDTO
     * @return
     */
    @PostMapping()
    public ResponseEntity<UserDTO> signup(@RequestBody UserDTO userDTO) {
        UserDTO user = userService.saveUser(authorizationUtil.getLoginId(), userDTO);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * 회원탈퇴
     * @param userId
     * @param response
     * @throws IOException
     */
    @DeleteMapping("/{userId}")
    public void removeUser(@PathVariable("userId") Long userId, HttpServletResponse response) throws IOException {
        userService.deleteUser(userId);

        Cookie cookie = new Cookie("Authorization", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.setStatus(200);
    }

}
