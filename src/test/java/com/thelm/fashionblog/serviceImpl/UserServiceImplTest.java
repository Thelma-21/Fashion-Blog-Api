package com.thelm.fashionblog.serviceImpl;

import com.thelm.fashionblog.dto.*;
import com.thelm.fashionblog.exception.EntityAlreadyExistException;
import com.thelm.fashionblog.model.Comment;
import com.thelm.fashionblog.model.Like;
import com.thelm.fashionblog.model.Post;
import com.thelm.fashionblog.model.User;
import com.thelm.fashionblog.repository.CommentRepository;
import com.thelm.fashionblog.repository.LikeRepository;
import com.thelm.fashionblog.repository.PostRepository;
import com.thelm.fashionblog.repository.UserRepository;
import com.thelm.fashionblog.response.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.time.Month.AUGUST;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private LikeRepository likeRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private LocalDateTime localDateTime;
    private User user;
    private Comment comment;
    private Like like;
    private Post post;
    List<Like> likeList = new ArrayList<>();
    List<Post> postList = new ArrayList<>();
    List<Comment> commentList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        localDateTime = LocalDateTime.of(2022, AUGUST, 9, 5, 50, 30, 70000);
        user =
                new User(
                        1,
                        "Thelma",
                        "thelmaozougwu@gmail.com",
                        "Admin",
                        "SB2022",
                        localDateTime,
                        localDateTime,
                        postList,
                        commentList,
                        likeList);
        post =
                new Post(
                        1,
                        "Love pro-max",
                        "Loving wholeheardedly",
                        "Love-pro-max",
                        "fine image.jpg",
                        localDateTime,
                        localDateTime,
                        user,
                        commentList,
                        likeList);
        like = new Like(1, true, localDateTime, localDateTime, user, post);
        comment = new Comment(1, " God when", localDateTime, localDateTime, post, user);
    }

    @Test
    @DisplayName("Should save the user when the email is not taken")
    void registerWhenEmailIsNotTaken() {
        UserDto userDto = new UserDto("Thelma", "thelmaozougwu@gmail.com", "Admin", "SB2022");
        when(userRepository.findUserByEmail(any())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(user);
        RegisterResponse registerResponse = userService.register(userDto);
        assertEquals("success", registerResponse.getMessage());
        assertEquals(localDateTime, registerResponse.getTimeStamp());
        assertEquals(user, registerResponse.getUser());
    }

    @Test
    @DisplayName("Should throw an exception when the email is already taken")
    void registerWhenEmailIsAlreadyTakenThenThrowException() {
        UserDto userDto = new UserDto("Thelma", "thelmaozougwu@gmail.com", "Admin", "SB2022");
        when(userRepository.findUserByEmail(any())).thenReturn(Optional.of(user));
        assertThrows(EntityAlreadyExistException.class, () -> userService.register(userDto));
    }


    @Test
    void loginSuccessfully() {
        LoginDto loginDto = new LoginDto("thelmaozougwu@gmail.com" , "SB2022");
        when(userRepository.findUserByEmail("thelmaozougwu@gmail.com")).thenReturn(Optional.ofNullable(user));
        LoginResponse loginResponse = new LoginResponse("success" , localDateTime);
        var actual =  userService.login(loginDto);
        actual.setTimeStamp(localDateTime);
        assertEquals(loginResponse , actual);
    }

    @Test
    void incorrectPasswordForLogin() {
        LoginDto loginDto = new LoginDto("thelmaozougwu@gmail.com" , "sb2022");
        when(userRepository.findUserByEmail("thelmaozougwu@gmail.com")).thenReturn(Optional.ofNullable(user));
        LoginResponse loginResponse = new LoginResponse("password mismatch" , localDateTime);
        var actual =  userService.login(loginDto);
        actual.setTimeStamp(localDateTime);
        assertEquals(loginResponse , actual);
    }

    @Test
    void createPost() {
        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(user));
        PostDto postDto = new PostDto( "Love pro-max", "Loving wholeheardedly", "fine image.jpg",  1);
        PostResponse postResponse = new PostResponse("success" , localDateTime , post);
        var actual = userService.createPost(postDto);
        actual.setTimeStamp(localDateTime);
        actual.getPost().setCreatedAt(localDateTime);
        actual.getPost().setUpdatedAt(localDateTime);
        actual.getPost().setId(1);
        actual.getPost().setSlug("Love-pro-max");
        assertEquals(postResponse , actual);
    }

    @Test
    void comment() {
        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(user));
        when(postRepository.findById(1)).thenReturn(Optional.ofNullable(post));
        CommentDto commentDto = new CommentDto("God when");
        CommentResponse commentResponse = new CommentResponse("success" , localDateTime , comment , post);
        var actual = userService.comment(1 , 1  , commentDto);
        actual.setTimeStamp(localDateTime);
        actual.setComment(comment);
        commentResponse.setComment(comment);
        commentResponse.setPost(post);
        assertEquals(commentResponse , actual);
    }

    @Test
    void like() {
        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(user));
        when(postRepository.findById(1)).thenReturn(Optional.ofNullable(post));
        List<Like> likes = new ArrayList<>(Arrays.asList(like));
        when(likeRepository.likeList(1)).thenReturn(likes);
        LikeDto likeDto = new LikeDto(true);
        LikeResponse likeResponse = new LikeResponse("success" , localDateTime , like , 1);
        var actual = userService.like(1 , 1  , likeDto);
        actual.setTimeStamp(localDateTime);
        actual.setLike(like);
        likeResponse.getLike().setUser(user);
        likeResponse.getLike().setPost(post);
        assertEquals(likeResponse , actual);
    }

    @Test
    void findUserById() {
        when(userRepository.findById(1)).thenReturn(Optional.ofNullable(user));
        assertEquals(user , userService.findUserById(1));
    }

    @Test
    void findPostById() {
        when(postRepository.findById(1)).thenReturn(Optional.ofNullable(post));
        assertEquals(post , userService.findPostById(1));
    }

    @Test
    void findUserByEmail() {
        when(userRepository.findUserByEmail("enwerevincent@gmail.com")).thenReturn(Optional.ofNullable(user));
        assertEquals(user , userService.findUserByEmail("enwerevincent@gmail.com"));
    }
}