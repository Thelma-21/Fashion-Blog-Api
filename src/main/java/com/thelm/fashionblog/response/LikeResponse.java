package com.thelm.fashionblog.response;

import com.thelm.fashionblog.model.Comment;
import com.thelm.fashionblog.model.Like;
import com.thelm.fashionblog.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class LikeResponse {
    private String message;
    private LocalDateTime timeStamp;
    private Like like;
    private int totalLikes;
}
