package com.pitter.service;

import com.pitter.domain.entity.Member;
import com.pitter.domain.repository.MemberRepository;
import com.pitter.domain.wrapper.Email;
import com.pitter.exception.DuplicateMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public Member join (Member member) {
        if(isDuplicateEmail(member.getEmail()) || isDuplicateNickName(member.getNickName())) {
            throw new DuplicateMemberException("이미 존재하는 회원입니다.");
        }
        return memberRepository.save(member);
    }

    public Member findByEmail(Email email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(NoSuchElementException::new);
    }

    public boolean isDuplicateNickName(String nickName) {
        return memberRepository.findByNickName(nickName)
                .isPresent();
    }

    public boolean isDuplicateEmail(Email email) {
        return memberRepository.findByEmail(email)
                .isPresent();
    }

}
