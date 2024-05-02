package com.example.test.controller;

import com.example.test.entity.Board;
import com.example.test.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
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
    private static final Logger log = LoggerFactory.getLogger(BoardController.class);
    private final BoardService boardService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/post/uid={uid}") // localhost:8080/board/post
    public ResponseEntity<Board> createBoard(@PathVariable Long uid, @RequestBody Board board) {
        Board created = boardService.createBoard(uid, board);
        if (created != null) {
            log.info("save success! id: " + created.getId() + " , title: " + created.getTitle() + " , content: " + created.getContent());
            return ResponseEntity.ok(created);
        }
        else {
            log.info("create fail");
            return ResponseEntity.badRequest().body(created);
        }

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Board> getAllBoards() {
        List<Board> boardList = boardService.getAllBoards();
        return boardList;
    }

    @GetMapping("/uid={uid}")
    public ResponseEntity<List<Board>> getMyBoards(@PathVariable Long uid) {
        List<Board> myBoards = boardService.getMyBoards(uid);

        if (myBoards != null) {
            return ResponseEntity.ok(myBoards);
        }
        else return ResponseEntity.badRequest().body(myBoards);
    }

    @PutMapping("/update/boarId={boardId}")
    public ResponseEntity<Board> updateBoard(@PathVariable Long boardId, @RequestBody Board board) {
        Board updateBoard = boardService.updateBoard(boardId, board);
        if(updateBoard != null) {
            return ResponseEntity.ok(updateBoard);
        }
        else {
            return ResponseEntity.badRequest().body(updateBoard);
        }
    }

    @DeleteMapping("/delete/boarId={boardId}")
    public ResponseEntity<Board> deleteBoard(@PathVariable Long boardId) {
        Board deleteBoard = boardService.deleteBoard(boardId);
        if (deleteBoard != null) {
            return ResponseEntity.ok(deleteBoard);
        }
        else {
            return ResponseEntity.badRequest().body(deleteBoard);
        }
    }

    @GetMapping("/search/keyword={keyword}")
    public List<Board> searchBoard(@PathVariable String keyword) {
        List<Board> searchPosts = boardService.searchBoard(keyword);
        return searchPosts;
    }

    @GetMapping("/paging")
    public ResponseEntity<List<Board>> pagingBoards(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "3") int size) {
        Page<Board> pageResult = boardService.pagingBoards(page, size);
        List<Board> pagingBoards = pageResult.getContent();
        if (pagingBoards.isEmpty() == true) {
            return ResponseEntity.badRequest().body(pagingBoards);
        }
        else return ResponseEntity.ok(pagingBoards);
    }
}
