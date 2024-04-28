package com.example.test.entity;

import jakarta.persistence.*;

@Table(name = "table_test")
@Entity
public class test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String demoText;
}
