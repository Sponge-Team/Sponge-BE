package com.petweb.sponge.user.service;

import com.petweb.sponge.user.domain.Trainer;
import com.petweb.sponge.user.domain.User;
import com.petweb.sponge.user.dto.TrainerDTO;
import com.petweb.sponge.user.dto.TrainerId;
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


//    /**
//     * 훈련사 조회
//     *
//     * @param trainerId
//     * @return
//     */
//    @Transactional(readOnly = true)
//    public Trainer findTrainer(Long trainerId) {
//        Trainer trainer = trainerRepository.findByTrainerId(trainerId);
//        if (trainer == null) {
//            throw new RuntimeException("Trainer not found");
//        }
//        return trainer;
//    }

    /**
     * 훈련사 정보저장
     *
     * @param trainerDTO
     * @return
     */
    @Transactional
    public TrainerId saveTrainer(TrainerDTO trainerDTO) {
        User findUser = userRepository.findById(trainerDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Not Found User"));
        User user = findUser.changeUserInfo(trainerDTO.getName(), trainerDTO.getGender(), trainerDTO.getProfileImgUrl());
        Trainer trainer = Trainer.createTrainer(trainerDTO, user);
        Trainer savedTrainer = trainerRepository.save(trainer);
        return TrainerId.builder()
                .trainerId(savedTrainer.getId())
                .userId(savedTrainer.getUser().getId()).build();
    }

//    /**
//     * 훈련사 정보 수정 (변경 감지)
//     * @param trainerId
//     * @param trainerDTO
//     * @return
//     */
//    @Transactional
//    public void updateTrainer(Long trainerId, TrainerDTO trainerDTO) {
//        Trainer trainer = trainerRepository.findByTrainerId(trainerId);
//
//        if (trainer == null) {
//            throw new RuntimeException("Trainer not found");
//        }
//        //trainer 수정
//        trainer.changeTrainerInfo(trainerDTO.getContent()
//                , trainerDTO.getYears()
//                , trainerDTO.getHistory()
//                , trainerDTO.getCity()
//                , trainerDTO.getTown());
//        //user 수정
//        User user = trainer.getUser();
//        user.changeUserInfo(trainerDTO.getName(),1,trainerDTO.getProfileImgUrl());
//
//    }
//
    /**
     * 훈련사 정보 삭제
     * @param trainerId
     */
    @Transactional
    public void deleteTrainer(Long trainerId) {
        Trainer trainer = trainerRepository.findByTrainerId(trainerId);
        if (trainer == null) {
            throw new RuntimeException("Trainer not found");
        }
        trainerRepository.deleteById(trainerId);
        userRepository.deleteById(trainer.getUser().getId());
    }

}
