package com.petweb.sponge.post.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.petweb.sponge.post.domain.answer.AdoptAnswer;
import com.petweb.sponge.post.domain.answer.Answer;
import com.petweb.sponge.post.domain.post.ProblemPost;
import com.petweb.sponge.post.dto.AnswerDTO;
import com.petweb.sponge.post.dto.AnswerDetailDTO;
import com.petweb.sponge.post.repository.answer.AnswerRepository;
import com.petweb.sponge.post.repository.post.ProblemPostRepository;
import com.petweb.sponge.trainer.domain.Trainer;
import com.petweb.sponge.trainer.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final TrainerRepository trainerRepository;
    private final ProblemPostRepository problemPostRepository;
    private final AnswerRepository answerRepository;

    /**
     * 훈련사 답변 조회
     * @param problemPostId
     * @return
     */
    @Transactional(readOnly = true)
    public List<AnswerDetailDTO> findAnswerList(Long problemPostId) {
        List<Answer> answerList = answerRepository.findAnswerWithTrainer(problemPostId);
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
                () -> new NotFoundException("NO Found Post"));
        Trainer trainer = trainerRepository.findById(loginId).orElseThrow(
                () -> new NotFoundException("NO Found Trainer"));
        Answer answer = Answer.builder()
                .content(answerDTO.getContent())
                .problemPost(problemPost)
                .trainer(trainer).build();
        //답변수 증가
        problemPost.increaseAnswerCount();
        answerRepository.save(answer);
    }

    /**
     * Dto 변환
     * @param answerList
     * @return
     */
    private  List<AnswerDetailDTO> toDetailDto(List<Answer> answerList) {
        return answerList.stream().map(answer ->
        {
            AdoptAnswer adoptAnswer = answer.getAdoptAnswer();
            // 채택되면 true 안되있으면 false
            boolean adoptCheck = adoptAnswer != null;
            return AnswerDetailDTO.builder()
                    .answerId(answer.getId())
                    .trainerId(answer.getTrainer().getId())
                    .trainerName(answer.getTrainer().getName())
                    .adopt_count(answer.getTrainer().getAdopt_count())
                    .chat_count(answer.getTrainer().getChat_count())
                    .content(answer.getContent())
                    .likeCount(answer.getLikeCount())
                    .adoptCheck(adoptCheck)
                    .build();
        }).collect(Collectors.toList());
    }

}
