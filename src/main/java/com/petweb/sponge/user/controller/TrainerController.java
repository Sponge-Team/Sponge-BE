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

    @PostMapping()
    public ResponseEntity<TrainerDTO> signup(@RequestBody TrainerDTO trainerDTO) {
        TrainerDTO trainer = trainerService.saveTrainer(trainerDTO);
        return new ResponseEntity<>(trainer,HttpStatus.OK);
    }


}
