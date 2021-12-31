package com.pitter.api.controller;

import com.pitter.api.dto.MemberJoinRequestDto;
import com.pitter.api.dto.MemberJoinResponseDto;
import com.pitter.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public MemberJoinResponseDto join(@Valid @RequestBody MemberJoinRequestDto memberJoinRequestDto) {
        Long savedId = memberService.join(memberJoinRequestDto.toMemberEntity());
        return new MemberJoinResponseDto(savedId, memberJoinRequestDto.getNickName(), memberJoinRequestDto.getEmail());
    }





}
