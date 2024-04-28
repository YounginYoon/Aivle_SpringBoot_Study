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
    @PostMapping("/post/{boardId}")
    public ResponseEntity<Comment> createComment(@PathVariable Long boardId, @RequestBody Comment comment) {
        Comment createComment = commentService.createComment(boardId, comment);
        if (createComment != null) {
            return ResponseEntity.ok(createComment);
        }
        else {
            return ResponseEntity.badRequest().body(createComment);
        }
    }

    @PutMapping("/update/{boardId}/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long boardId, @PathVariable Long commentId, @RequestBody Comment comment) {
        Comment updatedComment = commentService.updateComment(boardId, commentId, comment);
        if (updatedComment != null) {
            return ResponseEntity.ok(updatedComment);
        }
        else return ResponseEntity.badRequest().body(updatedComment);
    }

    @DeleteMapping("/delete/{boardId}/{commentId}")
    public ResponseEntity<Comment> deleteComment(@PathVariable Long boardId, @PathVariable Long commentId) {
        Comment deletedComment = commentService.deleteComment(boardId, commentId);
        if (deletedComment != null) return ResponseEntity.ok(deletedComment);
        else return ResponseEntity.badRequest().body(deletedComment);
    }
}
