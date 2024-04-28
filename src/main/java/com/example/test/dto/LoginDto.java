package com.example.test.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    @Column(nullable = false)
    private String loginEmail;

    @Column(nullable = false)
    private String loginPassword;
}
