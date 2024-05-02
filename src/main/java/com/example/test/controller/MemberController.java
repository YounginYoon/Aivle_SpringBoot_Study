package com.example.test.controller;

import com.example.test.dto.LoginDto;
import com.example.test.dto.MemberDto;
import com.example.test.entity.Member;
import com.example.test.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class MemberController {
    private static final Logger log = LoggerFactory.getLogger(MemberController.class);
    private final MemberService memberService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public Member register(@RequestBody MemberDto memberDto) {
        memberService.register(memberDto);
        return null;
    }


    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto) {
        log.info(loginDto.getLoginEmail() + ' ' + loginDto.getLoginPassword());
        String login_check = memberService.login(loginDto);

        return login_check;
    }

    @ResponseStatus(HttpStatus.OK) // CREATED: 생성할 떄 OK: 가져올 떄
    @GetMapping("/check/register/{email}")
    public boolean checkEmail(@PathVariable String email) {
        boolean check = memberService.getEmailCheck(email);
        return check;
    }

}
