package com.example.test.controller;

import com.example.test.entity.Board;
import com.example.test.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/board", produces = MediaType.APPLICATION_JSON_VALUE)
public class BoardController {
    private final BoardService boardService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/post") // localhost:8080/board/post
    public Board create(@RequestBody Board board) {
        boardService.create(board);
        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Board> getAllBoards() {
        List<Board> boardList = boardService.getAllBoards();
        return boardList;
    }

    @PutMapping("/update/{boardId}")
    public ResponseEntity<Board> updateBoard(@PathVariable Long boardId, @RequestBody Board board) {
        Board updateBoard = boardService.updateBoard(boardId, board);
        if(updateBoard != null) {
            return ResponseEntity.ok(updateBoard);
        }
        else {
            return ResponseEntity.badRequest().body(updateBoard);
        }
    }

    @DeleteMapping("/delete/{boardId}")
    public ResponseEntity<Board> deleteBoard(@PathVariable Long boardId) {
        Board deleteBoard = boardService.deleteBoard(boardId);
        if (deleteBoard != null) {
            return ResponseEntity.ok(deleteBoard);
        }
        else {
            return ResponseEntity.badRequest().body(deleteBoard);
        }
    }

    @GetMapping("/search/{keyword}")
    public List<Board> searchBoard(@PathVariable String keyword) {
        List<Board> searchPosts = boardService.searchBoard(keyword);
        return searchPosts;
    }
}
