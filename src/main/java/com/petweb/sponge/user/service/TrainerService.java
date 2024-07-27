package com.petweb.sponge.user.service;

import com.petweb.sponge.user.domain.Trainer;
import com.petweb.sponge.user.domain.User;
import com.petweb.sponge.user.dto.TrainerDTO;
import com.petweb.sponge.user.repository.TrainerRepository;
import com.petweb.sponge.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;


    /**
     * 훈련사 조회
     *
     * @param trainerId
     * @return
     */
    @Transactional(readOnly = true)
    public TrainerDTO findTrainer(Long trainerId) {
        Trainer trainer = trainerRepository.findByTrainerId(trainerId);
        return toDTO(trainer);
    }

    /**
     * 훈련사 정보저장
     *
     * @param trainerDTO
     * @return
     */
    @Transactional
    public TrainerDTO saveTrainer(TrainerDTO trainerDTO) {
        User user = userRepository.findById(trainerDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Not Found User"));

        Trainer trainer = toEntity(trainerDTO, user);
        Trainer saveTrainer = trainerRepository.save(trainer);
        return toDTO(saveTrainer);
    }

    /**
     * 훈련사 정보 수정 (더티 체킹 이용)
     * @param trainerId
     * @param trainerDTO
     * @return
     */
    @Transactional
    public TrainerDTO updateTrainer(Long trainerId, TrainerDTO trainerDTO) {
        Trainer trainer = trainerRepository.findByTrainerId(trainerId);

        if (trainer == null) {
            throw new RuntimeException("Trainer not found");
        }
        //trainer 수정
        trainer.setContent(trainerDTO.getContent());
        trainer.setYears(trainerDTO.getYears());
        trainer.setHistory(trainerDTO.getHistory());
        trainer.setCity(trainerDTO.getCity());
        trainer.setTown(trainerDTO.getTown());

        //user 수정
        User user = trainer.getUser();
        user.setName(trainerDTO.getName());
        user.setProfileImgUrl(trainerDTO.getProfileImgUrl());

        Trainer savedTrainer = trainerRepository.save(trainer);
        return toDTO(savedTrainer);
    }


    /**
     * trainer 엔티티 -> DTO 변환
     *
     * @param trainer
     * @return
     */
    private TrainerDTO toDTO(Trainer trainer) {
        return new TrainerDTO(trainer.getUser().getId(),
                trainer.getId(),
                trainer.getUser().getName(),
                trainer.getUser().getProfileImgUrl(),
                trainer.getContent(),
                trainer.getYears(),
                trainer.getHistory(),
                trainer.getCity(),
                trainer.getTown());
    }

    /**
     * DTO -> trainer 엔티티
     *
     * @param trainerDTO
     * @param user
     * @return
     */
    private Trainer toEntity(TrainerDTO trainerDTO, User user) {
        return new Trainer(trainerDTO.getContent(),
                trainerDTO.getYears(),
                trainerDTO.getHistory(),
                trainerDTO.getCity(),
                trainerDTO.getTown(),
                user);
    }


}
