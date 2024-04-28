package com.example.test.service;

import com.example.test.entity.Board;
import com.example.test.entity.Comment;
import com.example.test.repository.BoardRepository;
import com.example.test.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class CommentService {
    public final CommentRepository commentRepository;
    public final BoardRepository boardRepository;

    public Comment createComment(Long boardId, Comment comment) {
        Board board = boardRepository.findById(boardId).orElse(null);

        if (board != null) {
            comment.setCommentWriter("Aivle");
            comment.setBoard(board);
            Comment saveComment = commentRepository.save(comment);
            return saveComment;
        }
        else {
            return null;
        }
    }
}
