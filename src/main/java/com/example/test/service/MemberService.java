package com.example.test.service;

import com.example.test.dto.LoginDto;
import com.example.test.dto.MemberDto;
import com.example.test.entity.Member;
import com.example.test.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberService {
    private static final Logger log = LoggerFactory.getLogger(MemberService.class);
    private final MemberRepository memberRepository;

    public void register(MemberDto memberDto) {
        Member member = new Member();
        member.setEmail(memberDto.getMemberEmail());
        member.setPassword(memberDto.getMemberPassword());
        member.setUsername(memberDto.getMemberUsername());
        member.setNickname(memberDto.getMemberNickname());
        if(!memberRepository.existsByEmail(member.getEmail()))
            memberRepository.save(member);
        else {
            throw new IllegalArgumentException("Email already exists");
        }
    }

    public String login(LoginDto loginDto) {
        /*
        * email, password 체크해서 db에서 username 찾기 -> username 리턴하고 로그 찍기
        * */
        // 1. email check
        // 2. email check를 통과했으면 password check
        // 3. 모든 check를 통과했으면, 디비에서 해당 password와 email을 갖는 username을 찾아서 가져옴
        String ret = "Fail";
        boolean emailCheck = memberRepository.existsByEmail(loginDto.getLoginEmail());
        if(emailCheck == true) {
            Optional<Member> getMember = memberRepository.findByEmail(loginDto.getLoginEmail());
            if (getMember.isEmpty()) return null;
            Member findMember = getMember.get();
            if(findMember.getPassword().equals(loginDto.getLoginPassword()) == true) {
                ret = findMember.getUsername();
            }
        }
        return ret;

    }

    public boolean getEmailCheck(String email) {
        boolean check = memberRepository.existsByEmail(email);

        return check;
    }
}
