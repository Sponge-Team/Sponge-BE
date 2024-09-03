package com.petweb.sponge.post.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.petweb.sponge.exception.error.*;
import com.petweb.sponge.post.domain.answer.AdoptAnswer;
import com.petweb.sponge.post.domain.answer.Answer;
import com.petweb.sponge.post.domain.answer.AnswerRecommend;
import com.petweb.sponge.post.domain.post.ProblemPost;
import com.petweb.sponge.post.dto.answer.AdoptAnswerDTO;
import com.petweb.sponge.post.dto.answer.AnswerDTO;
import com.petweb.sponge.post.dto.answer.AnswerDetailDTO;
import com.petweb.sponge.post.dto.answer.AnswerUpdateDTO;
import com.petweb.sponge.post.repository.answer.AdoptAnswerRepository;
import com.petweb.sponge.post.repository.answer.AnswerRecommendRepository;
import com.petweb.sponge.post.repository.answer.AnswerRepository;
import com.petweb.sponge.post.repository.post.ProblemPostRepository;
import com.petweb.sponge.trainer.domain.Trainer;
import com.petweb.sponge.trainer.repository.TrainerRepository;
import com.petweb.sponge.user.domain.User;
import com.petweb.sponge.user.repository.UserRepository;
import com.petweb.sponge.utils.AuthorizationUtil;
import com.petweb.sponge.utils.LoginType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;
    private final ProblemPostRepository problemPostRepository;
    private final AnswerRepository answerRepository;
    private final AdoptAnswerRepository adoptAnswerRepository;
    private final AnswerRecommendRepository answerRecommendRepository;
    private final AuthorizationUtil authorizationUtil;

    /**
     * 훈련사 답변 조회
     *
     * @param problemPostId
     * @return
     */
    @Transactional(readOnly = true)
    public List<AnswerDetailDTO> findAnswerList(Long problemPostId) {
        List<Answer> answerList = answerRepository.findAllAnswerWithTrainer(problemPostId);
        return toDetailDto(answerList);
    }


    /**
     * 훈련사 답변 저장
     *
     * @param loginId
     * @param answerDTO
     */
    @Transactional
    public void saveAnswer(Long loginId, AnswerDTO answerDTO) {
        ProblemPost problemPost = problemPostRepository.findById(answerDTO.getProblemPostId()).orElseThrow(
                NotFoundPost::new);
        Trainer trainer = trainerRepository.findById(loginId).orElseThrow(
                NotFoundTrainer::new);
        Answer answer = Answer.builder()
                .content(answerDTO.getContent())
                .problemPost(problemPost)
                .trainer(trainer).build();
        //답변수 증가
        problemPost.increaseAnswerCount();
        answerRepository.save(answer);
    }

    /**
     * 훈련사 답변내용 수정
     *
     * @param answerId
     * @param answerUpdateDTO
     */
    @Transactional
    public void updateAnswer(Long answerId, AnswerUpdateDTO answerUpdateDTO) {
        Answer answer = answerRepository.findById(answerId).orElseThrow(
                NotFoundAnswer::new);
        answer.setContent(answerUpdateDTO.getContent());
    }

    /**
     * 훈련사 답변 삭제
     *
     * @param answerId
     * @param loginId
     */
    @Transactional
    public void deleteAnswer(Long answerId, Long loginId) {
        Optional<AdoptAnswer> adoptAnswer = adoptAnswerRepository.findAdoptAnswer(answerId, loginId);
        /**
         * 답변과 관련하여 채택이 있다면 채택과 같이 답변삭제, 채택수 -1
         * 답변과 관련하여 채택이 없다면 채택만 삭제, 채택수는 그대로
         */
        if (adoptAnswer.isPresent()) {
            Trainer trainer = adoptAnswer.get().getTrainer();
            trainer.decreaseAdoptCount();
        }
        answerRepository.deleteAnswer(answerId, loginId);
    }

    /**
     * 답변 채택 저장
     *
     * @param adoptAnswerDTO
     * @param loginId
     */
    @Transactional
    public void saveAdoptAnswer(AdoptAnswerDTO adoptAnswerDTO, Long loginId) {
        Answer answer = answerRepository.findAnswer(adoptAnswerDTO.getAnswerId());
        User user = userRepository.findById(loginId).orElseThrow(
                NotFoundUser::new);
        // 글을쓴 유저인지 아닌지 체크
        if (!answer.getProblemPost().getUser().getId().equals(user.getId())) {
            throw new LoginIdError();
        }

        AdoptAnswer adoptAnswer = AdoptAnswer.builder()
                .answer(answer)
                .trainer(answer.getTrainer())
                .user(user)
                .build();
        adoptAnswerRepository.save(adoptAnswer);
        // 답변채택수 증가
        answer.getTrainer().increaseAdoptCount();
    }

    /**
     * 훈련사 답변 추천
     *
     * @param answerId
     * @param loginId
     */
    @Transactional
    public void updateLikeCount(Long answerId, Long loginId) {
        Optional<AnswerRecommend> recommend = answerRecommendRepository.findRecommend(answerId, loginId);
        Answer answer = answerRepository.findAnswer(answerId);
        User user = userRepository.findById(loginId).orElseThrow(
                NotFoundUser::new);

        /**
         * 추천이 이미 있다면 추천을 삭제 추천수 -1
         * 추천이 없다면 추천을 저장 추천수 +1
         */
        if (recommend.isPresent()) {
            answer.decreaseLikeCount();
            answerRecommendRepository.delete(recommend.get());
        } else {
            AnswerRecommend answerRecommend = AnswerRecommend.builder()
                    .answer(answer)
                    .user(user)
                    .build();
            answer.increaseLikeCount();
            answerRecommendRepository.save(answerRecommend);
        }
    }

    /**
     * Dto 변환
     *
     * @param answerList
     * @return
     */
    private List<AnswerDetailDTO> toDetailDto(List<Answer> answerList) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        return answerList.stream().map(answer ->
        {

            AdoptAnswer adoptAnswer = answer.getAdoptAnswer();
            // 채택되면 true 안되있으면 false
            boolean adoptCheck = adoptAnswer != null;

            // 문제행동글 작성자면 true 작성자가 아니라면 false
            boolean postWriter = false;
            if (authorizationUtil.getLoginType().equals(LoginType.USER.getLoginType())) {
                Long userId = answer.getProblemPost().getUser().getId();
                postWriter = authorizationUtil.getLoginId().equals(userId);
            }
            Optional<Trainer> trainer = trainerRepository.findById(answer.getTrainer().getId());
            if (trainer.isPresent()) {
                //훈련사의 정보가 있다면
                return AnswerDetailDTO.builder()
                        .answerId(answer.getId())
                        .trainerId(answer.getTrainer().getId())
                        .trainerName(answer.getTrainer().getName())
                        .adopt_count(answer.getTrainer().getAdopt_count())
                        .chat_count(answer.getTrainer().getChat_count())
                        .content(answer.getContent())
                        .likeCount(answer.getLikeCount())
                        .adoptCheck(adoptCheck)
                        .postWriter(postWriter)
                        .createdAt(formatter.format(answer.getCreatedAt()))
                        .build();
            }
            else {
                //훈련사가 탈퇴했다면
                return AnswerDetailDTO.builder()
                        .answerId(answer.getId())
                        .content(answer.getContent())
                        .likeCount(answer.getLikeCount())
                        .adoptCheck(adoptCheck)
                        .postWriter(postWriter)
                        .createdAt(formatter.format(answer.getCreatedAt()))
                        .build();
            }

        }).collect(Collectors.toList());
    }


}
