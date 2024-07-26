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
     * 훈련사 정보저장
     * Controller에서 넘어온 trainerDTO를 Trainer 엔티티에 넣어주고 Repository로 전달
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
     * trainer 엔티티 -> DTO 변환
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
     * @param trainerDTO
     * @param user
     * @return
     */
    private Trainer toEntity(TrainerDTO trainerDTO,User user) {
        return new Trainer(trainerDTO.getContent(),
                trainerDTO.getYears(),
                trainerDTO.getHistory(),
                trainerDTO.getCity(),
                trainerDTO.getTown(),
                user);
    }

}
