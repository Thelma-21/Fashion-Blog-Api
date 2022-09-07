package com.thelm.fashionblog.repository;

import com.thelm.fashionblog.model.Like;
import com.thelm.fashionblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {
    @Query(value = "SELECT  * FROM likes WHERE  user_id = ?1 AND post_id = ?2" , nativeQuery = true)
    Like findLikeByUserIdAndPostId(int user_id , int post_id);

    @Query(value = "SELECT  * FROM likes WHERE   post_id = ?1" , nativeQuery = true)
    List<Like> likeList(int post_id);
}
