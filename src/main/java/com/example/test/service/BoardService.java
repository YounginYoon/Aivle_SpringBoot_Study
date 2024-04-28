package com.example.test.service;

import com.example.test.entity.Board;
import com.example.test.repository.BoardRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
// @Transactional -> 객체를 수정 가능한 상태로 변경하여 save하지 않아도 set을 통해 수정 가능
public class BoardService {
    public final BoardRepository boardRepository;

    public void create(Board board) {
        board.setWriter("Aivle");
        boardRepository.save(board);
    }

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public Board updateBoard(Long boardId, Board boardDetail) {
        Board updateBoard = boardRepository.findById(boardId).orElse(null);

        if (updateBoard != null) {
            if (updateBoard.getWriter().equals("Aivle")) {
                updateBoard.setTitle(boardDetail.getTitle());
                updateBoard.setContent(boardDetail.getContent());
                Board saveBoard = boardRepository.save(updateBoard);
                return saveBoard;
            }
            else return null;
        }
        else {
            return null;
        }
    }

    public Board deleteBoard(Long boardId) {
        Board deleteBoard = boardRepository.findById(boardId).orElse(null);

        if (deleteBoard != null) {
            boardRepository.deleteById(boardId);
            return deleteBoard;
        }
        else return null;
    }

    public List<Board> searchBoard(String keyword) {
        List<Board> posts = boardRepository.findCustomByTitleContaining(keyword);
        return posts;
    }
}
