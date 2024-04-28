package com.example.test.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

@Getter
@Setter
@Entity
@Table(name="comment_table")

public class Comment extends Time{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(length = 20, nullable = false)
    private String commentWriter;


    @Column(nullable = false)
    private String commentContents;

    /* Board:Comment = 1:N */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_id")
    @JsonBackReference
    private Board board;
}
