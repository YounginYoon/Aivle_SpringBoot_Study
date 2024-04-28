package com.example.test.repository;

import com.example.test.entity.Board;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    boolean existsById(Long id);
    List<Board> findCustomByTitleContaining(String keyword);
    List<Board> findByMemberId(Long uid);
}
