package com.petweb.sponge.post.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.petweb.sponge.pet.domain.Pet;
import com.petweb.sponge.pet.repository.PetRepository;
import com.petweb.sponge.post.domain.PostCategory;
import com.petweb.sponge.post.domain.PostImage;
import com.petweb.sponge.post.domain.ProblemPost;
import com.petweb.sponge.post.domain.Tag;
import com.petweb.sponge.post.dto.PostDetailDto;
import com.petweb.sponge.post.dto.ProblemPostDTO;
import com.petweb.sponge.post.repository.ProblemPostRepository;
import com.petweb.sponge.post.repository.ProblemTypeRepository;
import com.petweb.sponge.user.domain.User;
import com.petweb.sponge.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProblemPostService {

    private final ProblemPostRepository problemPostRepository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final ProblemTypeRepository problemTypeRepository;


    /**
     * 글 단건 조회
     * TODO 컬렉션 다수 조회 최적화 필요
     * @param problemPostId
     * @return
     */
    public PostDetailDto findPost(Long problemPostId) {
        ProblemPost problemPost = problemPostRepository.findPostWithType(problemPostId);

        return PostDetailDto.builder()
                .userId(problemPost.getUser().getId())
                .problemPostId(problemPost.getId())
                .title(problemPost.getTitle())
                .content(problemPost.getContent())
                .duration(problemPost.getDuration())
                .likeCount(problemPost.getLikeCount())
                .petName(problemPost.getPet().getName())
                .breed(problemPost.getPet().getBreed())
                .gender(problemPost.getPet().getGender())
                .age(problemPost.getPet().getAge())
                .weight(problemPost.getPet().getWeight())
                .categoryCodeList(problemPost.getPostCategories().stream()
                        .map(postCategory -> postCategory.getProblemType().getCode()).collect(Collectors.toList()))
                .hasTagList(problemPost.getTags().stream()
                        .map(tag -> tag.getHashtag()).collect(Collectors.toList()))
                .imageUrlList(problemPost.getPostImages().stream()
                        .map(postImage -> postImage.getImageUrl()).collect(Collectors.toList()))
                .build();

    }

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

        ProblemPost problemPost = toEntity(problemPostDTO, user, pet);

        //ProblemType 조회해서 -> PostCategory로 변환 저장
        problemTypeRepository.findAllByCodeIn(problemPostDTO.getCategoryCodeList())
                .stream().map(problemType -> PostCategory.builder()
                        .problemPost(problemPost)
                        .problemType(problemType)
                        .build()).collect(Collectors.toList())
                .forEach(postCategory -> problemPost.getPostCategories().add(postCategory));

        // tag 클래스 생성해서 저장
        problemPostDTO.getHasTagList().stream().map(hashTag -> Tag.builder()
                .hashtag(hashTag)
                .problemPost(problemPost)
                .build()).collect(Collectors.toList()).forEach(tag -> {
            problemPost.getTags().add(tag);
        });

        //PostImage클래스 생성해서 저장
        problemPostDTO.getImageUrlList().stream().map(imageUrl ->
                PostImage.builder()
                        .imageUrl(imageUrl)
                        .problemPost(problemPost)
                        .build()
        ).collect(Collectors.toList())
                .forEach(postImage -> problemPost.getPostImages().add(postImage));


        problemPostRepository.save(problemPost);

    }

    /**
     * 글 삭제
     *
     * @param problemPostId
     */
    @Transactional
    public void deletePost(Long problemPostId) {
        problemPostRepository.deletePost(problemPostId);
    }


    /**
     * entity로 변환
     *
     * @param problemPostDTO
     * @param user
     * @param pet
     * @return
     */
    private ProblemPost toEntity(ProblemPostDTO problemPostDTO, User user, Pet pet) {
        return ProblemPost.builder()
                .title(problemPostDTO.getTitle())
                .content(problemPostDTO.getContent())
                .duration(problemPostDTO.getDuration())
                .user(user)
                .pet(pet).build();

    }


}
