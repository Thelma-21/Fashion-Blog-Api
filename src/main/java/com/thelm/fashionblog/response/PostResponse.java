package com.thelm.fashionblog.response;

import com.thelm.fashionblog.model.Comment;
import com.thelm.fashionblog.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class PostResponse {
    private String message;
    private LocalDateTime timeStamp;
    private Post post;
}
