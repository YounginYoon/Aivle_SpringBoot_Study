package com.example.test.repository;

import com.example.test.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByCommentWriter(String nickname);
    List<Comment> findByBoardId(Long boardId);
}
