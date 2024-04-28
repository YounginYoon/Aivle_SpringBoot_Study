package com.example.test.controller;

import com.example.test.entity.Comment;
import com.example.test.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    
}
