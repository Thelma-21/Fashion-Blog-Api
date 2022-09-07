package com.thelm.fashionblog.response;

import com.thelm.fashionblog.model.Comment;
import com.thelm.fashionblog.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class SearchCommentResponse {
    private String message;
    private LocalDateTime timeStamp;
    private List<Comment> comments;
}
