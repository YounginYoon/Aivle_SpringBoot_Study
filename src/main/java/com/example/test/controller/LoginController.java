package com.example.test.controller;

import com.example.test.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/login/oauth2", produces="application/json")
@RequiredArgsConstructor
public class LoginController {
    LoginService loginService;

    @GetMapping("/code/{registrationId}")
    public void googleLogin(@RequestParam String code, @PathVariable String registrationId) {
        loginService.socialLogin(code, registrationId);
    }

}
