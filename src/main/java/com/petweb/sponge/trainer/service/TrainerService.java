package com.petweb.sponge.trainer.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.petweb.sponge.trainer.domain.Trainer;
import com.petweb.sponge.trainer.dto.AddressDTO;
import com.petweb.sponge.trainer.dto.HistoryDTO;
import com.petweb.sponge.trainer.dto.TrainerDTO;
import com.petweb.sponge.trainer.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerService {

    private final TrainerRepository trainerRepository;


    /**
     * 훈련사 단건 조회
     *
     * @param trainerId
     * @return
     */
    @Transactional(readOnly = true)
    public TrainerDTO findTrainer(Long trainerId) {
        // trainer, address 한번에 조회
        Trainer trainer = trainerRepository.findTrainerWithAddress(trainerId);
        return toDto(trainer);
    }


    /**
     * 훈련사 정보저장
     *
     * @param loginId
     * @param trainerDTO
     * @return
     */
    @Transactional
    public TrainerDTO saveTrainer(Long loginId, TrainerDTO trainerDTO) {
        //로그인하자마자 저장 되어있던 trainer 조회
        Trainer trainer = trainerRepository.findById(loginId).orElseThrow(
                () -> new NotFoundException("NO Found Trainer"));
        //trainer에 정보 셋팅 및 저장
        trainer.settingTrainer(trainerDTO);
        Trainer savedTrainer = trainerRepository.save(trainer);
        return toDto(savedTrainer);

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
     *
     * @param trainerId
     */
    @Transactional
    public void deleteTrainer(Long trainerId) {
        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow(
                () -> new NotFoundException("NO Found Trainer"));

        //벌크성 쿼리로 history, address 한번에 삭제
        trainerRepository.deleteTrainer(trainer.getId());
    }

    /**
     * Dto로 변환하는 메소드 (지연 로딩으로 인해서 쿼리가 추가 2번 나가는 문제 있음)
     *
     * @param trainer
     * @return
     */
    private TrainerDTO toDto(Trainer trainer) {
        List<AddressDTO> addressDTOList = trainer.getTrainerAddresses().stream().map(address -> AddressDTO.builder()
                .city(address.getCity())
                .town(address.getTown())
                .build()).collect(Collectors.toList());
        List<HistoryDTO> historyDTOList = trainer.getHistories().stream().map(history -> HistoryDTO.builder()
                .title(history.getTitle())
                .startDt(history.getStartDt())
                .endDt(history.getEndDt())
                .description(history.getDescription()).build()).collect(Collectors.toList());
        return TrainerDTO.builder()
                .name(trainer.getName())
                .gender(trainer.getGender())
                .phone(trainer.getPhone())
                .profileImgUrl(trainer.getProfileImgUrl())
                .content(trainer.getContent())
                .years(trainer.getYears())
                .addressList(addressDTOList)
                .historyList(historyDTOList)
                .build();
    }

}
