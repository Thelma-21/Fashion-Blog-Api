package com.thelm.fashionblog.controller;

import com.thelm.fashionblog.dto.*;
import com.thelm.fashionblog.model.Post;
import com.thelm.fashionblog.response.*;
import com.thelm.fashionblog.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@RestController
@Slf4j
@RequestMapping(value = "/api")
public class UserController {

    private final UserService userService;


    @PostMapping(value = "/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody UserDto userDto){
        log.info("successfully registered  {} ", userDto.getEmail());
        return new ResponseEntity<>(userService.register(userDto), CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginDto loginDto){
        log.info("Login successfully {} ", loginDto.getEmail());
        return new ResponseEntity<>(userService.login(loginDto), ACCEPTED);
    }


    @PostMapping(value = "/create")
    public ResponseEntity<PostResponse> create(@RequestBody PostDto postDto){
        log.info("successfully created a post with title: {} ", postDto.getTitle() );
        return new ResponseEntity<>(userService.createPost(postDto), CREATED);

    }


    @PostMapping(value = "/comment/{user_id}/{post_id}")
    public ResponseEntity<CommentResponse> comment(@PathVariable(value = "user_id") Integer user_id, @PathVariable(value = "post_id") Integer post_id, @RequestBody CommentDto commentDto){
        log.info("successfully commented : {} ", commentDto.getComment());
        return new ResponseEntity<>(userService.comment(user_id, post_id, commentDto), OK);
    }


    @PostMapping(value = "/like/{user_id}/{post_id}")
    public ResponseEntity<LikeResponse> like(@PathVariable(value = "user_id") Integer user_id, @PathVariable(value = "post_id") Integer post_id, @RequestBody LikeDto likeDto){
        log.info("Successfully liked :  {} " , likeDto.isLiked());
        return new ResponseEntity<>(userService.like(user_id, post_id, likeDto), OK);
    }

    @GetMapping("/searchPost/{keyword}")
    public ResponseEntity<SearchPostResponse> searchPost(@PathVariable(value = "keyword") String keyword){
        return new ResponseEntity<>(userService.searchPost(keyword), OK);
    }

    @GetMapping(value = "/searchComment/{keyword}")
    public ResponseEntity<SearchCommentResponse> searchComment(@PathVariable(value = "keyword") String keyword){
        return new ResponseEntity<>(userService.searchComment(keyword), OK);
    }

    @GetMapping(value = "/post/{id}")
    public ResponseEntity<Post> searchComment(@PathVariable(value = "id") Integer id){
        return new ResponseEntity<>(userService.findPostById(id), OK);
    }



}
