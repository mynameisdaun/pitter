package com.pitter.service;

import com.pitter.api.dto.MemberJoinRequestDto;
import com.pitter.domain.entity.Member;
import com.pitter.domain.repository.MemberRepository;
import com.pitter.exception.DuplicateMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public Long join (@Valid MemberJoinRequestDto memberJoinRequestDto) {
        if(isDuplicateEmail(memberJoinRequestDto.getEmail()) || isDuplicateNickName(memberJoinRequestDto.getNickName())) {
            throw new DuplicateMemberException("이미 존재하는 회원입니다.");
        }
        Member savedMember = memberRepository.save(memberJoinRequestDto.toMemberEntity());
        return savedMember.getId();
    }

    public boolean isDuplicateNickName(String nickName) {
        return memberRepository.findByNickName(nickName)
                .isPresent();
    }

    public boolean isDuplicateEmail(String email) {
        return memberRepository.findByEmail(email)
                .isPresent();
    }

}
