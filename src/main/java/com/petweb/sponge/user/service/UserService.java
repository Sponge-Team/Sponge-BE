package com.petweb.sponge.user.service;

import com.petweb.sponge.exception.error.NotFoundPost;
import com.petweb.sponge.exception.error.NotFoundUser;
import com.petweb.sponge.pet.dto.PetDTO;
import com.petweb.sponge.post.domain.post.Bookmark;
import com.petweb.sponge.post.domain.post.ProblemPost;
import com.petweb.sponge.post.dto.post.PostIdDTO;
import com.petweb.sponge.post.dto.post.ProblemPostListDTO;
import com.petweb.sponge.post.repository.post.BookmarkRepository;
import com.petweb.sponge.post.repository.post.ProblemPostRepository;
import com.petweb.sponge.trainer.dto.AddressDTO;
import com.petweb.sponge.user.domain.User;
import com.petweb.sponge.user.dto.UserDTO;
import com.petweb.sponge.user.dto.UserDetailDTO;
import com.petweb.sponge.user.dto.UserUpdateDTO;
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
public class UserService {

    private final UserRepository userRepository;

    private final ProblemPostRepository problemPostRepository;
    private final BookmarkRepository bookmarkRepository;
    /**
     * 유저 단건 조회
     *
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    public UserDetailDTO findUser(Long userId) {
        // user,address 한번에 조회
        User user = userRepository.findUserWithAddress(userId).orElseThrow(
                NotFoundUser::new);
        return toDetailDto(user);
    }

    /**
     * 유저 내정보 조회
     *
     * @param loginId
     * @return
     */
    @Transactional(readOnly = true)
    public UserDetailDTO findMyInfo(Long loginId) {
        // user,address 한번에 조회
        User user = userRepository.findUserWithAddress(loginId).orElseThrow(
                NotFoundUser::new);


        return toDetailDto(user);
    }

    /**
     * 유저 정보 저장
     *
     * @param loginId
     * @param userDTO
     * @return
     */
    @Transactional
    public void saveUser(Long loginId, UserDTO userDTO) {
        //현재 로그인 유저 정보 가져오기
        User user = userRepository.findById(loginId).orElseThrow(
                NotFoundUser::new);
        user.settingUser(userDTO);
        userRepository.save(user);
    }

    /**
     * 유저 정보 수정
     * @param userId
     * @param userUpdateDTO
     */
    @Transactional
    public void updateUser(Long userId, UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(userId).orElseThrow(
                NotFoundUser::new);
        //초기화
        userRepository.initUser(userId);
        user.updateUser(userUpdateDTO);
    }

    /**
     * 유저 정보 삭제
     *
     * @param userId
     */
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                NotFoundUser::new);
        userRepository.deleteUser(user.getId());
    }

    /**
     * 북마크 조회
     *
     * @param loginId
     * @return
     */
    @Transactional(readOnly = true)
    public List<ProblemPostListDTO> findBookmark(Long loginId) {
        List<ProblemPost> problemPostList = problemPostRepository.findAllPostByBookmark(loginId);
        return toPostListDto(problemPostList);

    }
    /**
     * 북마크 업데이트
     *
     * @param postIdDTO
     * @param loginId
     */
    public void updateBookmark(PostIdDTO postIdDTO, Long loginId) {
        Optional<Bookmark> bookmark = bookmarkRepository.findBookmark(postIdDTO.getProblemPostId(), loginId);
        ProblemPost problemPost = problemPostRepository.findPostWithUser(postIdDTO.getProblemPostId()).orElseThrow(
                NotFoundPost::new);;

        // 이미 북마크 되어있다면 삭제 아니라면 저장
        if (bookmark.isPresent()) {
            bookmarkRepository.delete(bookmark.get());
        } else {
            Bookmark buildBookmark = Bookmark.builder()
                    .problemPost(problemPost)
                    .user(problemPost.getUser())
                    .build();
            bookmarkRepository.save(buildBookmark);
        }

    }


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
    /**
     * DetailDto 변환
     * @param user
     * @return
     */
    private UserDetailDTO toDetailDto(User user) {
        List<AddressDTO> addressDTOList = user.getUserAddresses().stream().map(userAddress -> AddressDTO.builder()
                .city(userAddress.getCity())
                .town(userAddress.getTown())
                .build()).collect(Collectors.toList());
        List<PetDTO> petDTOList = user.getPets().stream().map(pet ->
                        PetDTO.builder()
                                .petId(pet.getId())
                                .petName(pet.getName())
                                .breed(pet.getBreed())
                                .gender(pet.getGender())
                                .age(pet.getAge())
                                .weight(pet.getWeight())
                                .petImgUrl(pet.getPetImgUrl()).build())
                .collect(Collectors.toList());
        return UserDetailDTO.builder()
                .userId(user.getId())
                .userName(user.getName())
                .petList(petDTOList)
                .addressList(addressDTOList)
                .build();
    }



}
