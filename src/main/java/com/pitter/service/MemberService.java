package com.pitter.service;

import com.pitter.domain.entity.member.Member;
import com.pitter.domain.repository.member.MemberRepository;
import com.pitter.domain.entity.member.Email;
import com.pitter.domain.entity.member.NickName;
import com.pitter.common.exception.DuplicateMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    public Optional<Member> findByEmail(Email email) {
        return memberRepository.findByEmail(email);
    }

    public boolean isDuplicateNickName(NickName nickName) {
        return memberRepository.findByNickName(nickName)
                .isPresent();
    }

    public boolean isDuplicateEmail(Email email) {
        return memberRepository.findByEmail(email)
                .isPresent();
    }

}
