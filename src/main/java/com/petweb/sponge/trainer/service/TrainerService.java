package com.petweb.sponge.trainer.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.petweb.sponge.exception.error.NotFoundTrainer;
import com.petweb.sponge.exception.error.NotFoundUser;
import com.petweb.sponge.trainer.domain.Review;
import com.petweb.sponge.trainer.domain.Trainer;
import com.petweb.sponge.trainer.dto.*;
import com.petweb.sponge.trainer.repository.ReviewRepository;
import com.petweb.sponge.trainer.repository.TrainerRepository;
import com.petweb.sponge.user.domain.User;
import com.petweb.sponge.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;


    /**
     * 훈련사 단건 조회
     *
     * @param trainerId
     * @return
     */
    @Transactional(readOnly = true)
    public TrainerDetailDTO findTrainer(Long trainerId) {
        Trainer trainer = trainerRepository.findTrainerWithAddress(trainerId).orElseThrow(
                NotFoundTrainer::new);
        return toDetailDto(trainer);
    }

    /**
     * 내정보 조회
     * @param loginId
     * @return
     */
    @Transactional(readOnly = true)
    public TrainerDetailDTO findMyInfo(Long loginId) {
        // trainer, address 한번에 조회
        Trainer trainer = trainerRepository.findTrainerWithAddress(loginId).orElseThrow(
                NotFoundTrainer::new);
        return toDetailDto(trainer);
    }


    /**
     * 훈련사 정보저장
     *
     * @param loginId
     * @param trainerDetailDTO
     * @return
     */
    @Transactional
    public void saveTrainer(Long loginId, TrainerDetailDTO trainerDetailDTO) {
        //로그인하자마자 저장 되어있던 trainer 조회
        Trainer trainer = trainerRepository.findById(loginId).orElseThrow(
                NotFoundTrainer::new);
        //trainer에 정보 셋팅 및 저장
        trainer.settingTrainer(trainerDetailDTO);
        trainerRepository.save(trainer);
    }

    /**
     * 훈련사 정보 수정
     * @param trainerId
     * @param trainerDetailDTO
     */
    @Transactional
    public void updateTrainer(Long trainerId, TrainerDetailDTO trainerDetailDTO) {
        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow(
                NotFoundTrainer::new);
        trainerRepository.initTrainer(trainerId);
        //trainer 정보 수정
        trainer.settingTrainer(trainerDetailDTO);
    }

    /**
     * 훈련사 정보 삭제
     *
     * @param trainerId
     */
    @Transactional
    public void deleteTrainer(Long trainerId) {
        Trainer trainer = trainerRepository.findById(trainerId).orElseThrow(
                NotFoundTrainer::new);

        //벌크성 쿼리로 history, address 한번에 삭제
        trainerRepository.deleteTrainer(trainer.getId());
    }

    /**
     * 리뷰 데이터 조회
     *
     * @param trainerId
     * @return
     */
    @Transactional(readOnly = true)
    public List<ReviewDetailDTO> findAllReview(Long trainerId) {
        List<Review> reviewList = reviewRepository.findAllByTrainerId(trainerId);
        return reviewList.stream().map(review -> ReviewDetailDTO.builder()
                .userId(review.getUser().getId())
                .userName(review.getUser().getName())
                .userProfileImgUrl(review.getUser().getProfileImgUrl())
                .score(review.getScore())
                .content(review.getContent()).build()).collect(Collectors.toList());
    }

    /**
     * 리뷰 데이터 저장
     *
     * @param reviewDTO
     * @param loginId
     */
    @Transactional
    public void saveReview(ReviewDTO reviewDTO, Long loginId) {
        Trainer trainer = trainerRepository.findById(loginId).orElseThrow(
                NotFoundTrainer::new);
        User user = userRepository.findById(loginId).orElseThrow(
                NotFoundUser::new);

        Review review = Review.builder()
                .score(reviewDTO.getScore())
                .content(reviewDTO.getContent())
                .user(user)
                .trainer(trainer)
                .build();
        reviewRepository.save(review);
    }

    /**
     * Dto로 변환하는 메소드 (지연 로딩으로 인해서 쿼리가 추가 2번 나가는 문제 있음)
     *
     * @param trainer
     * @return
     */
    private TrainerDetailDTO toDetailDto(Trainer trainer) {
        List<AddressDTO> addressDTOList = trainer.getTrainerAddresses().stream().map(address -> AddressDTO.builder()
                .city(address.getCity())
                .town(address.getTown())
                .build()).collect(Collectors.toList());
        List<HistoryDTO> historyDTOList = trainer.getHistories().stream().map(history -> HistoryDTO.builder()
                .title(history.getTitle())
                .startDt(history.getStartDt())
                .endDt(history.getEndDt())
                .description(history.getDescription()).build()).collect(Collectors.toList());
        return TrainerDetailDTO.builder()
                .trainerId(trainer.getId())
                .name(trainer.getName())
                .gender(trainer.getGender())
                .phone(trainer.getPhone())
                .profileImgUrl(trainer.getProfileImgUrl())
                .content(trainer.getContent())
                .years(trainer.getYears())
                .adoptCount(trainer.getAdopt_count())
                .chatCount(trainer.getChat_count())
                .addressList(addressDTOList)
                .historyList(historyDTOList)
                .build();
    }


}
