package com.example.test.service;

import com.example.test.entity.Board;
import com.example.test.entity.Comment;
import com.example.test.entity.Member;
import com.example.test.repository.BoardRepository;
import com.example.test.repository.CommentRepository;
import com.example.test.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    public final CommentRepository commentRepository;
    public final BoardRepository boardRepository;
    public final MemberRepository memberRepository;

    public Comment createComment(Long boardId, Long uid, Comment comment) {
        Board board = boardRepository.findById(boardId).orElse(null);
        Member member = memberRepository.findById(uid).orElse(null);

        if (board != null && member != null) {
            comment.setCommentWriter(member.getNickname());
            comment.setBoard(board);
            Comment saveComment = commentRepository.save(comment);
            return saveComment;
        }
        else {
            return null;
        }
    }

    public Comment updateComment(Long boardId, Long commentId, Long uid, Comment comment) {
        Board boardForComment = boardRepository.findById(boardId).orElse(null);

        if (boardForComment != null) {
            Comment commentForUpdate = commentRepository.findById(commentId).orElse(null);
            if (commentForUpdate != null && commentForUpdate.getBoard().getId() == boardId) {
                Member memberForComment = memberRepository.findById(uid).orElse(null);
                if (memberForComment != null && commentForUpdate.getCommentWriter().equals(memberForComment.getNickname())) {
                    commentForUpdate.setCommentContents(comment.getCommentContents());
                    return commentForUpdate;
                }
                else return null;
            }
            else return null;
        }
        else return null;
    }

    public Comment deleteComment(Long boardId, Long uid, Long commentId) {
        Board boardForComment = boardRepository.findById(boardId).orElse(null);

        if (boardForComment != null) {
            Comment commentForDelete = commentRepository.findById(commentId).orElse(null);
            if (commentForDelete != null && commentForDelete.getBoard().getId() == boardId) {
                Member memberForComment = memberRepository.findById(uid).orElse(null);
                if (memberForComment != null && commentForDelete.getCommentWriter().equals(memberForComment.getNickname())) {
                    commentRepository.deleteById(commentId);
                    return commentForDelete;
                }
                else return null;
            }
            else return null;
        }
        else return null;
    }

    public List<Comment> getMyComments(Long uid) {
        Member member = memberRepository.findById(uid).orElse(null);

        if (member != null) {
            List<Comment> myComments = commentRepository.findByCommentWriter(member.getNickname());
            if (myComments.isEmpty()) return null;
            return myComments;
        }
        return null;
    }

    public List<Comment> getBoardComments(Long boardId) {
        List<Comment> boardComments = commentRepository.findByBoardId(boardId);
        if (boardComments.isEmpty()) return null;
        return boardComments;
    }
}
