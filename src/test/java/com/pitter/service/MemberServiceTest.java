package com.pitter.service;

import com.pitter.domain.entity.member.Member;
import com.pitter.domain.repository.MemberRepository;
import com.pitter.domain.entity.member.Email;
import com.pitter.domain.entity.member.NickName;
import com.pitter.exception.DuplicateMemberException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(properties = "spring.config.location=classpath:/application.properties,classpath:/application-oauth.properties")
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;

    @Autowired MemberRepository memberRepository;

    private NickName test_nickName = new NickName("toytoy");
    private Email test_email = new Email("tyotyo@pitter.com");
    private String test_password = "Eptmxm1!";
    private Member member;

    @Test
    public void join_success() throws Exception {
        //given
        Member member = Member.createMember(test_nickName, test_email, test_password);

        //when
        Long savedId = memberService.join(member).getId();
        
        //then
        assertThat(savedId).isNotNull();
    }
    
    @Test
    public void join_fail_Duplicate_Email() throws Exception {
        //given
        Member member = Member.createMember(test_nickName, test_email, test_password);
        memberRepository.save(member);
        Member duplicateMember = Member.createMember(new NickName("중복아닌닉네임"), test_email, test_password);
                
        //when
        assertThatThrownBy(()->memberService.join(duplicateMember))
                .isInstanceOf(DuplicateMemberException.class);
        //then

    }

    @Test
    public void join_fail_Duplicate_NickName() throws Exception {
        //given
        Member member = Member.createMember(test_nickName, test_email, test_password);
        memberRepository.save(member);
        Member duplicateMember = Member.createMember(test_nickName, new Email("notduplicated@pitter.com"), test_password);

        //when;
        assertThatThrownBy(()->memberService.join(duplicateMember))
                .isInstanceOf(DuplicateMemberException.class);

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