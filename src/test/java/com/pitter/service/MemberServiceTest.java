package com.pitter.service;

import com.pitter.api.dto.MemberJoinRequestDto;
import com.pitter.domain.entity.Member;
import com.pitter.domain.repository.MemberRepository;
import com.pitter.exception.DuplicateMemberException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;

    @Autowired MemberRepository memberRepository;

    private String test_nickName = "tester";
    private String test_email = "tester@pitter.com";
    private String test_password = "Eptmxm1!";

    @Test
    public void join_success() throws Exception {
        //given
        Member member = Member.createMember(test_nickName, test_email, test_password);

        //when
        Long savedId = memberService.join(member);
        
        //then
        assertThat(savedId).isNotNull();
    }
    
    @Test(expected = DuplicateMemberException.class)
    public void join_fail_Duplicate_Email() throws Exception {
        //given
        Member member = Member.createMember(test_nickName, test_email, test_password);
        memberRepository.save(member);
        Member duplicateMember = Member.createMember("notDuplicated", test_email, test_password);
                
        //when
        memberService.join(duplicateMember);
                
        //then
        fail("should throw DuplicateMemberException");
    }

    @Test(expected = DuplicateMemberException.class)
    public void join_fail_Duplicate_NickName() throws Exception {
        //given
        Member member = Member.createMember(test_nickName, test_email, test_password);
        memberRepository.save(member);
        Member duplicateMember = Member.createMember(test_nickName, "notduplicated@pitter.com", test_password);

        //when;
        memberService.join(duplicateMember);

        //then
        fail("should throw DuplicateMemberException");
    }

    @Test
    public void isDuplicateEmail_success_notDuplicated() throws Exception {
        //given


        //when
        boolean isDuplicateEmail = memberService.isDuplicateEmail(test_email);

        //then
        assertThat(isDuplicateEmail).isEqualTo(false);
    }

    @Test
    public void isDuplicateEmail_success_Duplicated() throws Exception {
        //given
        Member member = Member.createMember(test_nickName,test_email,test_password);
        memberRepository.save(member);

        //when
        boolean isDuplicateEmail = memberService.isDuplicateEmail(test_email);

        //then
        assertThat(isDuplicateEmail).isEqualTo(true);
    }

    @Test
    public void isDuplicateNickName_success_notDuplicated() throws Exception {
        //given


        //when
        boolean isDuplicateNickName = memberService.isDuplicateNickName(test_nickName);

        //then
        assertThat(isDuplicateNickName).isEqualTo(false);
    }

    @Test
    public void isDuplicateNickName_success_Duplicated() throws Exception {
        //given
        Member member = Member.createMember(test_nickName,test_email,test_password);
        memberRepository.save(member);

        //when
        boolean isDuplicateNickName = memberService.isDuplicateNickName(test_nickName);

        //then
        assertThat(isDuplicateNickName).isEqualTo(true);
    }

}