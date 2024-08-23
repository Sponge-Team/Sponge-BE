package com.petweb.sponge.post.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.petweb.sponge.pet.domain.Pet;
import com.petweb.sponge.pet.repository.PetRepository;
import com.petweb.sponge.post.domain.PostCategory;
import com.petweb.sponge.post.domain.ProblemPost;
import com.petweb.sponge.post.dto.ProblemPostDTO;
import com.petweb.sponge.post.repository.ProblemPostRepository;
import com.petweb.sponge.post.repository.ProblemTypeRepository;
import com.petweb.sponge.user.domain.User;
import com.petweb.sponge.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProblemPostService {

    private final ProblemPostRepository problemPostRepository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final ProblemTypeRepository problemTypeRepository;


    /**
     * 글 작성 저장
     *
     * @param loginId
     * @param problemPostDTO
     */
    @Transactional
    public void savePost(Long loginId, ProblemPostDTO problemPostDTO) {
        //현재 로그인 유저 정보 가져오기
        User user = userRepository.findById(loginId).orElseThrow(
                () -> new NotFoundException("NO Found USER"));
        //선택한 반려동물 정보 가져오기
        Pet pet = petRepository.findById(problemPostDTO.getPetId()).orElseThrow(
                () -> new NotFoundException("NO Found Pet"));

        ProblemPost problemPost = toEntity(problemPostDTO,user,pet);

        //ProblemType 조회해서 -> PostCategory로 변환
        List<PostCategory> postCategories = problemTypeRepository.findAllByCodeIn(problemPostDTO.getCategoryCode())
                .stream().map(problemType -> PostCategory.builder()
                        .problemPost(problemPost)
                        .problemType(problemType)
                        .build()).collect(Collectors.toList());

        postCategories.forEach(postCategory -> problemPost.getPostCategories().add(postCategory));
        problemPostRepository.save(problemPost);


    }

    /**
     * entity로 변환
     * @param problemPostDTO
     * @param user
     * @param pet
     * @return
     */
    private ProblemPost toEntity(ProblemPostDTO problemPostDTO,User user,Pet pet) {
        return ProblemPost.builder()
                .title(problemPostDTO.getTitle())
                .content(problemPostDTO.getContent())
                .duration(problemPostDTO.getDuration())
                .user(user)
                .pet(pet).build();

    }
}
