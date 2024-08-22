package com.petweb.sponge.trainer.controller;

import com.petweb.sponge.trainer.dto.TrainerDTO;
import com.petweb.sponge.trainer.service.TrainerService;
import com.petweb.sponge.utils.AuthorizationUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainer")
public class TrainerController {

    private final TrainerService trainerService;
    private final AuthorizationUtil authorizationUtil;

    /**
     * 훈련사 단건조회
     * @param trainerId
     * @return
     */
    @GetMapping("/{trainerId}")
    public ResponseEntity<TrainerDTO> getTrainer(@PathVariable("trainerId") Long trainerId) {
        TrainerDTO trainer = trainerService.findTrainer(trainerId);
        return new ResponseEntity<>(trainer, HttpStatus.OK);
    }

    /**
     * 훈련사 정보 저장
     * @param trainerDTO
     * @return
     */
    @PostMapping()
    public ResponseEntity<TrainerDTO> signup(@RequestBody TrainerDTO trainerDTO) {
        TrainerDTO trainer = trainerService.saveTrainer(authorizationUtil.getLoginId(),trainerDTO);
        return new ResponseEntity<>(trainer, HttpStatus.OK);
    }

//    /**
//     * 훈련사 정보 수정
//     * @param trainerId
//     * @param trainerDTO
//     * @return
//     */
//    @PutMapping("/{trainerId}")
//    public ResponseEntity<TrainerDTO> modifyTrainer(@PathVariable("trainerId") Long trainerId, @RequestBody TrainerDTO trainerDTO) {
//        trainerService.updateTrainer(trainerId, trainerDTO);
//        TrainerDTO trainer = trainerService.findTrainer(trainerId);
//        return new ResponseEntity<>(trainer,HttpStatus.OK);
//    }
//
    /**
     * 회원탈퇴
     * @param trainerId
     */
    @DeleteMapping("/{trainerId}")
    public void removeTrainer(@PathVariable("trainerId") Long trainerId, HttpServletResponse response) {
        trainerService.deleteTrainer(trainerId);

        //로그인 쿠키 삭제
        Cookie cookie = new Cookie("Authorization", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.setStatus(200);
    }
}
