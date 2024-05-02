package com.example.test.service;

import com.example.test.entity.Board;
import com.example.test.entity.Member;
import com.example.test.repository.BoardRepository;
import com.example.test.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional // -> 객체를 수정 가능한 상태로 변경하여 save하지 않아도 set을 통해 수정 가능
public class BoardService {
    public final BoardRepository boardRepository;
    public final MemberRepository memberRepository;

    public Board createBoard(Long uid, Board board) {
        Member member = memberRepository.findById(uid).orElse(null);
        if (member != null) {
            board.setWriter(member.getNickname());
            Board createdBoard = boardRepository.save(board);

            return createdBoard;
        }
        else return null;

    }

    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    public List<Board> getMyBoards(Long uid) {
        Member member = memberRepository.findById(uid).orElse(null);
        if (member != null) {
            List<Board> myBoards = boardRepository.findByWriter(member.getNickname());
            if (myBoards.isEmpty()) return null;
            return myBoards;
        }
        else return null;
    }

    public Board updateBoard(Long boardId, Board boardDetail) {
        Board updateBoard = boardRepository.findById(boardId).orElse(null);

        if (updateBoard != null) {
            if (updateBoard.getWriter().equals(boardDetail.getWriter())) {
                if (boardDetail.getTitle() != null)
                    updateBoard.setTitle(boardDetail.getTitle());
                if (boardDetail.getContent() != null)
                    updateBoard.setContent(boardDetail.getContent());
//                Board saveBoard = boardRepository.save(updateBoard);
                return updateBoard;
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

    public Page<Board> pagingBoards(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate").descending());
        return boardRepository.findAll(pageRequest);
    }
}
