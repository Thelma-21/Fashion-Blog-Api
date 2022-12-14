package com.thelm.fashionblog.dto;

import com.thelm.fashionblog.model.Like;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private String title ;
    private String description;
    private String featuredImage;
    private int user_id;


}
