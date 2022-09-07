package com.thelm.fashionblog.repository;

import com.thelm.fashionblog.model.Comment;
import com.thelm.fashionblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByCommentContainingIgnoreCase(String keyword);
}
