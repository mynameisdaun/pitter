package com.pitter.controller.api.member;

import com.pitter.controller.dto.MemberJoinRequest;
import com.pitter.controller.dto.MemberJoinResponse;
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
        Long savedId = memberService.join(memberJoinRequest.toMemberEntity()).getId();
        return new MemberJoinResponse(savedId, memberJoinRequest.getNickName(), memberJoinRequest.getEmail());
    }
}
