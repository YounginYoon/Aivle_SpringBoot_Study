package com.example.test.repository;

import com.example.test.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);
    boolean existsByPassword(String password);
    Member findByEmail(String email);
    Member findByUsername(String username);
}

