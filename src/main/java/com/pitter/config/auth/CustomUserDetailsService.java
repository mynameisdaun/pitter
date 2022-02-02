package com.pitter.config.auth;

import com.pitter.common.exception.MemberNotFoundException;
import com.pitter.domain.entity.member.Email;
import com.pitter.domain.repository.member.MemberRepository;
import com.pitter.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new CustomUserDetails(new Email(email));
    }
}
