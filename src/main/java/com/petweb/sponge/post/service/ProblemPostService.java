package com.petweb.sponge.post.service;

import com.petweb.sponge.exception.error.LoginIdError;
import com.petweb.sponge.exception.error.NotFoundPet;
import com.petweb.sponge.exception.error.NotFoundPost;
import com.petweb.sponge.exception.error.NotFoundUser;
import com.petweb.sponge.pet.domain.Pet;
import com.petweb.sponge.pet.repository.PetRepository;
import com.petweb.sponge.post.domain.post.*;
import com.petweb.sponge.post.dto.post.PostDetailDTO;
import com.petweb.sponge.post.dto.post.ProblemPostDTO;
import com.petweb.sponge.post.dto.post.ProblemPostListDTO;
import com.petweb.sponge.post.repository.post.PostRecommendRepository;
import com.petweb.sponge.post.repository.post.ProblemPostRepository;
import com.petweb.sponge.post.repository.ProblemTypeRepository;
import com.petweb.sponge.user.domain.User;
import com.petweb.sponge.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProblemPostService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final ProblemPostRepository problemPostRepository;
    private final ProblemTypeRepository problemTypeRepository;
    private final PostRecommendRepository postRecommendRepository;


    /**
     * 글 단건 조회
     *
     * @param problemPostId
     * @return
     */
    @Transactional(readOnly = true)
    public PostDetailDTO findPost(Long problemPostId) {
        ProblemPost problemPost = problemPostRepository.findPostWithType(problemPostId).orElseThrow(
                NotFoundPost::new);;
        return toDetailDto(problemPost);
    }

    /**
     * 카테고리별 글 전체 조회
     *
     * @param problemTypeCode
     * @return
     */
    @Transactional(readOnly = true)
    public List<ProblemPostListDTO> findPostList(Long problemTypeCode,int page) {
        List<ProblemPost> problemPosts = problemPostRepository.findAllPostByProblemCode(problemTypeCode,page);
        return toPostListDto(problemPosts);

    }

    /**
     * 검색
     * @param keyword
     * @param page
     * @return
     */
    @Transactional(readOnly = true)
    public List<ProblemPostListDTO> searchPost(String keyword, int page) {
        List<ProblemPost> problemPosts = problemPostRepository.searchPostByKeyword(keyword,page);
        return toPostListDto(problemPosts);
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
                NotFoundUser::new);
        //선택한 반려동물 정보 가져오기
        Pet pet = petRepository.findById(problemPostDTO.getPetId()).orElseThrow(
                NotFoundPet::new);

        ProblemPost problemPost = toEntity(problemPostDTO, user, pet);

        //ProblemType 조회해서 -> PostCategory로 변환 저장
        problemTypeRepository.findAllByCodeIn(problemPostDTO.getProblemTypeList())
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
        problemPostDTO.getFileUrlList().stream().map(imageUrl ->
                        PostFile.builder()
                                .fileUrl(imageUrl)
                                .problemPost(problemPost)
                                .build()
                ).collect(Collectors.toList())
                .forEach(postFile -> problemPost.getPostFiles().add(postFile));


        problemPostRepository.save(problemPost);

    }

    /**
     * 글 수정
     *
     * @param loginId
     * @param problemPostId
     * @param problemPostDTO
     */
    @Transactional
    public void updatePost(Long loginId, Long problemPostId, ProblemPostDTO problemPostDTO) {
        ProblemPost problemPost = problemPostRepository.findPostWithType(problemPostId).orElseThrow(
                NotFoundPost::new);
        // 글을 쓴 유저가 아닌경우
        if (!problemPost.getUser().getId().equals(loginId)) {
            throw new LoginIdError();
        }
        //글 초기화
        problemPostRepository.initProblemPost(problemPostId);

        problemPost.updatePost(problemPostDTO.getTitle()
                ,problemPostDTO.getContent()
                ,problemPostDTO.getFileUrlList()
                ,problemPostDTO.getHasTagList());

        //ProblemType 조회해서 -> PostCategory로 변환 저장
        problemTypeRepository.findAllByCodeIn(problemPostDTO.getProblemTypeList())
                .stream().map(problemType -> PostCategory.builder()
                        .problemPost(problemPost)
                        .problemType(problemType)
                        .build()).collect(Collectors.toList())
                .forEach(postCategory -> problemPost.getPostCategories().add(postCategory));
    }


    /**
     * 글 삭제
     *
     * @param loginId
     * @param problemPostId
     */
    @Transactional
    public void deletePost(Long loginId, Long problemPostId) {
        ProblemPost problemPost = problemPostRepository.findPostWithType(problemPostId).orElseThrow(
                NotFoundPost::new);;
        // 글을 쓴 유저가 아닌경우
        if (!problemPost.getUser().getId().equals(loginId)) {
            throw new LoginIdError();
        }
        problemPostRepository.deletePost(problemPostId);
    }


    /**
     * 추천수 업데이트
     *
     * @param problemPostId
     * @param loginId
     */
    public void updateLikeCount(Long problemPostId, Long loginId) {
        Optional<PostRecommend> recommend = postRecommendRepository.findRecommend(problemPostId, loginId);
        ProblemPost problemPost = problemPostRepository.findPostWithUser(problemPostId).orElseThrow(
                NotFoundPost::new);;
        /**
         * 추천이 이미 있다면 추천을 삭제 추천수 -1
         * 추천이 없다면 추천을 저장 추천수 +1
         */
        if (recommend.isPresent()) {
            problemPost.decreaseLikeCount();
            postRecommendRepository.delete(recommend.get());
        } else {
            PostRecommend postRecommend = PostRecommend.builder()
                    .problemPost(problemPost)
                    .user(problemPost.getUser())
                    .build();
            problemPost.increaseLikeCount();
            postRecommendRepository.save(postRecommend);
        }
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

    /**
     * Dto로 변환
     *
     * @param problemPost
     * @return
     */
    private PostDetailDTO toDetailDto(ProblemPost problemPost) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        //펫 정보를 삭제하지 않았다면
        Optional<Pet> pet = petRepository.findById(problemPost.getPet().getId());
        if (pet.isPresent()) {
            return PostDetailDTO.builder()
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
                    .createdAt(formatter.format(problemPost.getCreatedAt()))
                    .problemTypeList(problemPost.getPostCategories().stream()
                            .map(postCategory -> postCategory.getProblemType().getCode()).collect(Collectors.toList()))
                    .hasTagList(problemPost.getTags().stream()
                            .map(tag -> tag.getHashtag()).collect(Collectors.toList()))
                    .fileUrlList(problemPost.getPostFiles().stream()
                            .map(postFile -> postFile.getFileUrl()).collect(Collectors.toList()))
                    .build();
        } else {
            return PostDetailDTO.builder()
                    .userId(problemPost.getUser().getId())
                    .problemPostId(problemPost.getId())
                    .title(problemPost.getTitle())
                    .content(problemPost.getContent())
                    .duration(problemPost.getDuration())
                    .likeCount(problemPost.getLikeCount())
                    .createdAt(formatter.format(problemPost.getCreatedAt()))
                    .problemTypeList(problemPost.getPostCategories().stream()
                            .map(postCategory -> postCategory.getProblemType().getCode()).collect(Collectors.toList()))
                    .hasTagList(problemPost.getTags().stream()
                            .map(tag -> tag.getHashtag()).collect(Collectors.toList()))
                    .fileUrlList(problemPost.getPostFiles().stream()
                            .map(postFile -> postFile.getFileUrl()).collect(Collectors.toList()))
                    .build();

        }
    }


    /**
     * Dto로 변환
     *
     * @param problemPosts
     * @return
     */
    private List<ProblemPostListDTO> toPostListDto(List<ProblemPost> problemPosts) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return problemPosts.stream().map(problemPost ->
                ProblemPostListDTO.builder()
                        .problemPostId(problemPost.getId())
                        .title(problemPost.getTitle())
                        .content(problemPost.getContent())
                        .likeCount(problemPost.getLikeCount())
                        .createdAt(formatter.format(problemPost.getCreatedAt()))
                        .problemTypeList(problemPost.getPostCategories().stream()
                                .map(postCategory -> postCategory.getProblemType().getCode()).collect(Collectors.toList()))
                        .build()).collect(Collectors.toList());
    }



}
