package com.petweb.sponge.user.controller;

import com.petweb.sponge.user.dto.TrainerDTO;
import com.petweb.sponge.user.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trainer")
public class TrainerController {

    private final TrainerService trainerService;

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
        TrainerDTO trainer = trainerService.saveTrainer(trainerDTO);
        return new ResponseEntity<>(trainer, HttpStatus.OK);
    }

    /**
     * 훈련사 정보 수정
     * @param trainerId
     * @param trainerDTO
     * @return
     */
    @PutMapping("/{trainerId}")
    public ResponseEntity<TrainerDTO> modifyTrainer(@PathVariable("trainerId") Long trainerId, @RequestBody TrainerDTO trainerDTO) {
        TrainerDTO trainer = trainerService.updateTrainer(trainerId, trainerDTO);
        return new ResponseEntity<>(trainer,HttpStatus.OK);
    }


}
