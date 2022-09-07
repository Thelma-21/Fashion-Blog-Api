package com.thelm.fashionblog.serviceImpl;

import com.thelm.fashionblog.dto.*;
import com.thelm.fashionblog.exception.PostAlreadyLikedException;
import com.thelm.fashionblog.exception.EntityAlreadyExistException;
import com.thelm.fashionblog.exception.CustomNotFoundException;
import com.thelm.fashionblog.model.Comment;
import com.thelm.fashionblog.model.Like;
import com.thelm.fashionblog.model.Post;
import com.thelm.fashionblog.model.User;
import com.thelm.fashionblog.repository.CommentRepository;
import com.thelm.fashionblog.repository.LikeRepository;
import com.thelm.fashionblog.repository.PostRepository;
import com.thelm.fashionblog.repository.UserRepository;
import com.thelm.fashionblog.response.*;
import com.thelm.fashionblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;


    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");



    @Override
    public RegisterResponse register(UserDto userDto) {
        Optional<User> newUser = userRepository.findUserByEmail(userDto.getEmail());
        if(newUser.isPresent()){
            throw new EntityAlreadyExistException("User already Exist");
        }
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setRole(userDto.getRole());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
        return new RegisterResponse("success", LocalDateTime.now(), user);
    }

    @Override
    public LoginResponse login(LoginDto loginDto) {
        User user = findUserByEmail(loginDto.getEmail());
        LoginResponse loginResponse = null;
        if(user != null){
            if(user.getPassword().equals(loginDto.getPassword())){
                loginResponse = new LoginResponse("success", LocalDateTime.now());
            }else {
                loginResponse = new LoginResponse("password mismatch", LocalDateTime.now());
            }
        }
        return loginResponse;
    }

    @Override
    public PostResponse createPost(PostDto postDto) {
        User user = findUserById(postDto.getUser_id());
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setFeaturedImage(postDto.getFeaturedImage());
        post.setSlug(makeSlug(postDto.getTitle()));
        post.setUser(user);
        postRepository.save(post);
        return new PostResponse("success", LocalDateTime.now(), post);
    }


    @Override
    public CommentResponse comment(int user_id, int post_id, CommentDto commentDto) {
        User user = findUserById(user_id);
        Post post = findPostById(post_id);
        Comment comment = new Comment();
        comment.setComment(commentDto.getComment());
        comment.setUser(user);
        comment.setPost(post);
        commentRepository.save(comment);
        return new CommentResponse("success", LocalDateTime.now(), comment, post);
    }


    @Override
    public LikeResponse like(int user_id, int post_id, LikeDto likeDto) {
        Like like = new Like();
        User user = findUserById(user_id);
        Post post = findPostById(post_id);
        LikeResponse likeResponse;
        Like duplicateLike = likeRepository.findLikeByUserIdAndPostId(user_id, post_id);
        if(duplicateLike == null){
            like.setLiked(likeDto.isLiked());
            like.setUser(user);
            like.setPost(post);
            likeRepository.save(like);
            List<Like> likeList = likeRepository.likeList(post_id);
            likeResponse = new LikeResponse("success", LocalDateTime.now(), like, likeList.size());
        }else {
            likeRepository.delete(duplicateLike);
            throw new PostAlreadyLikedException("Post already liked, now unliked ");
        }

        return likeResponse;
    }


    @Override
    public SearchCommentResponse searchComment(String keyword) {
        List<Comment> commentList = commentRepository.findByCommentContainingIgnoreCase(keyword);
        return new SearchCommentResponse("success", LocalDateTime.now(), commentList);
    }


    @Override
    public SearchPostResponse searchPost(String keyword) {
        List<Post> postList = postRepository.findByTitleContainingIgnoreCase(keyword);
        return new SearchPostResponse("success", LocalDateTime.now(), postList);
    }


    @Override
    public User findUserById(int id){
        return userRepository.findById(id).orElseThrow(()-> new CustomNotFoundException("user with id: "+ id + " not found"));
    }

    @Override
    public Post findPostById(int id){
        return postRepository.findById(id).orElseThrow(()-> new CustomNotFoundException("post with id: " + id + " not found"));
    }

    @Override
    public User findUserByEmail(String email){
        return userRepository.findUserByEmail(email).orElseThrow(()-> new CustomNotFoundException("user with email: "+ email + " not found"));
    }


    public String makeSlug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }


}
