package com.thelm.fashionblog.service;

import com.thelm.fashionblog.dto.*;
import com.thelm.fashionblog.model.Post;
import com.thelm.fashionblog.model.User;
import com.thelm.fashionblog.response.*;

public interface UserService {

    RegisterResponse register(UserDto userDto);

    LoginResponse login(LoginDto loginDto);

    PostResponse createPost(PostDto postDto);

    CommentResponse comment(int user_id , int post_id , CommentDto commentDto);

    LikeResponse like(int user_id , int post_id , LikeDto likeDto);

    SearchCommentResponse searchComment(String keyword);

    SearchPostResponse searchPost(String keyword);

    User findUserById(int id);

    Post findPostById(int id);

    User findUserByEmail(String email);
}
