package com.thelm.fashionblog.response;

import com.thelm.fashionblog.model.Comment;
import com.thelm.fashionblog.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentResponse {
    private String message;
    private LocalDateTime timeStamp;
    private Comment comment;
    private Post post;
}
