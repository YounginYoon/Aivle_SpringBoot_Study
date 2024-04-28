package com.example.test.service;

import com.example.test.entity.Board;
import com.example.test.entity.Comment;
import com.example.test.repository.BoardRepository;
import com.example.test.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
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

    public Comment updateComment(Long boardId, Long commentId, Comment comment) {
        Board boardForComment = boardRepository.findById(boardId).orElse(null);

        if (boardForComment != null) {
            Comment commentForUpdate = commentRepository.findById(commentId).orElse(null);
            if (commentForUpdate != null && commentForUpdate.getBoard().getId() == boardId) {
                if (commentForUpdate.getCommentWriter().equals("Aivle")) {
                    commentForUpdate.setCommentContents(comment.getCommentContents());
                    return commentForUpdate;
                }
                else return null;
            }
            else return null;
        }
        else return null;
    }

    public Comment deleteComment(Long boardId, Long commentId) {
        Board boardForComment = boardRepository.findById(boardId).orElse(null);

        if (boardForComment != null) {
            Comment commentForDelete = commentRepository.findById(commentId).orElse(null);
            if (commentForDelete != null && commentForDelete.getBoard().getId() == boardId) {
                if (commentForDelete.getCommentWriter().equals("Aivle")) {
                    commentRepository.deleteById(commentId);
                    return commentForDelete;
                }
                else return null;
            }
            else return null;
        }
        else return null;
    }
}
