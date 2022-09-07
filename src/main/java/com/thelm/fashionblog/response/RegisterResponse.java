package com.thelm.fashionblog.response;

import com.thelm.fashionblog.model.Comment;
import com.thelm.fashionblog.model.Post;
import com.thelm.fashionblog.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RegisterResponse {
    private String message;
    private LocalDateTime timeStamp;
    private User user;

}
