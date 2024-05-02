package com.example.test.controller;

import com.example.test.entity.Comment;
import com.example.test.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/comment")
@RequiredArgsConstructor

public class CommentController {
    private final CommentService commentService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/post/boardId={boardId}/uid={uid}")
    public ResponseEntity<Comment> createComment(@PathVariable Long boardId, @PathVariable Long uid,@RequestBody Comment comment) {
        Comment createComment = commentService.createComment(boardId, uid, comment);
        if (createComment != null) {
            return ResponseEntity.ok(createComment);
        }
        else {
            return ResponseEntity.badRequest().body(createComment);
        }
    }

    @PutMapping("/update/boardId={boardId}/commentId={commentId}/uid={uid}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long boardId, @PathVariable Long commentId, @PathVariable Long uid, @RequestBody Comment comment) {
        Comment updatedComment = commentService.updateComment(boardId, commentId, uid, comment);
        if (updatedComment != null) {
            return ResponseEntity.ok(updatedComment);
        }
        else return ResponseEntity.badRequest().body(updatedComment);
    }

    @DeleteMapping("/delete/boardId={boardId}/commentId={commentId}/uid={uid}")
    public ResponseEntity<Comment> deleteComment(@PathVariable Long boardId, @PathVariable Long uid, @PathVariable Long commentId) {
        Comment deletedComment = commentService.deleteComment(boardId, uid, commentId);
        if (deletedComment != null) return ResponseEntity.ok(deletedComment);
        else return ResponseEntity.badRequest().body(deletedComment);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/uid={uid}")
    public ResponseEntity<List<Comment>> getMyComments(@PathVariable Long uid) {
        List<Comment> myComments = commentService.getMyComments(uid);

        if (myComments != null) {
            return ResponseEntity.ok(myComments);
        }
        else return ResponseEntity.badRequest().body(myComments);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/boardId={boardId}")
    public ResponseEntity<List<Comment>> getBoardComments(@PathVariable Long boardId) {
        List<Comment> boardComments = commentService.getBoardComments(boardId);

        if (boardComments != null) {
            return ResponseEntity.ok(boardComments);
        }
        else return ResponseEntity.badRequest().body(boardComments);
    }
}
