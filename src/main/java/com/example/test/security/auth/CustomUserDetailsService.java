package com.example.test.security.auth;

import com.example.test.entity.Member;
import com.example.test.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository){

        this.memberRepository = memberRepository;

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member memberData = memberRepository.findByEmail(username);
        System.out.println("going");

        if(memberData != null){
            System.out.println("memberdata");
            return new CustomUserDetails(memberData);

        }


        return null;
    }

}
