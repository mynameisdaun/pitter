package com.pitter.api.controller;

import com.pitter.api.dto.MemberJoinRequest;
import com.pitter.api.dto.MemberJoinResponse;
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
    public MemberJoinResponse join(@Valid @RequestBody MemberJoinRequest memberJoinRequest) {
        Long savedId = memberService.join(memberJoinRequest.toMemberEntity());
        return new MemberJoinResponse(savedId, memberJoinRequest.getNickName(), memberJoinRequest.getEmail());
    }





}
