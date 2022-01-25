package com.pitter.domain.repository;

import com.pitter.domain.entity.member.Member;
import com.pitter.domain.entity.member.Email;
import com.pitter.domain.entity.member.NickName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.config.location=classpath:/application.properties,classpath:/application-oauth.properties")
@Transactional
public class MemberRepositoryTest {
    
    @Autowired private MemberRepository memberRepository;

    @Autowired private EntityManager entityManager;

    private NickName test_nickName = new NickName("tester");
    private Email test_email = new Email("tester@pitter.com");
    private String test_password = "Eptmxm12!";

    @Test
    public void save_success() throws Exception {
        //given
        Member member = Member.createMember(test_nickName, test_email, test_password);

        //when
        Member savedMember = memberRepository.save(member);
        entityManager.flush();

        //then
        assertNotNull(savedMember);
    }

    @Test(expected = IllegalArgumentException.class)
    public void save_fail_nickName_null() throws Exception {
        //given
        Member member = Member.createMember(null, test_email, test_password);

        //when
        memberRepository.save(member);
        entityManager.flush();

        //then
        fail("should throw ConstraintViolationException");
    }

    @Test(expected = IllegalArgumentException.class)
    public void save_fail_nickName_empty_String() throws Exception {
        //given
        Member member = Member.createMember(new NickName(""), test_email, test_password);

        //when
        memberRepository.save(member);
        entityManager.flush();

        //then
        fail("should throw IllegalArgumentException");
    }

    @Test(expected = IllegalArgumentException.class)
    public void save_fail_nickName_length_is_GreaterEqual_than_2() throws Exception {
        //given
        Member member = Member.createMember(new NickName("정"), test_email, test_password);

        //when
        memberRepository.save(member);
        entityManager.flush();

        //then
        fail("should throw IllegalArgumentException");
    }

    @Test(expected = IllegalArgumentException.class)
    public void save_fail_nickName_length_is_LessEqual_than_10() throws Exception {
        //given
        Member member = Member.createMember(new NickName("동해물과백두산이마르고"), test_email, test_password);

        //when
        memberRepository.save(member);
        entityManager.flush();

        //then
        fail("should throw IllegalArgumentException");
    }

    @Test(expected = IllegalArgumentException.class)
    public void save_fail_nickName_blank_white_space() throws Exception {
        //given
        Member member = Member.createMember(new NickName(" "), test_email, test_password);

        //when
        memberRepository.save(member);
        entityManager.flush();

        //then
        fail("should throw IllegalArgumentException");
    }

    @Test(expected = IllegalArgumentException.class)
    public void save_fail_email_null() throws Exception {
        //given
        Member member = Member.createMember(test_nickName, null, test_password);

        //when
        memberRepository.save(member);
        entityManager.flush();

        //then
        fail("should throw IllegalArgumentException");
    }

    @Test(expected = IllegalArgumentException.class)
    public void save_fail_email_empty_String() throws Exception {
        //given
        Member member = Member.createMember(test_nickName, new Email(""), test_password);

        //when
        memberRepository.save(member);
        entityManager.flush();

        //then
        fail("should throw ConstraintViolationException");
    }

    @Test(expected = IllegalArgumentException.class)
    public void save_fail_email_blank_white_space() throws Exception {
        //given
        Member member = Member.createMember(test_nickName, new Email(" "), test_password);

        //when
        memberRepository.save(member);
        entityManager.flush();

        //then
        fail("should throw IllegalArgumentException");
    }

    @Test(expected = IllegalArgumentException.class)
    public void save_fail_email_No_At_Character() throws Exception {
        //given
        Member member = Member.createMember(test_nickName, new Email("daun9870!gmail.com"), test_password);

        //when
        memberRepository.save(member);
        entityManager.flush();

        //then
        fail("should throw IllegalArgumentException");
    }

    @Test(expected = ConstraintViolationException.class)
    public void save_fail_password_null() throws Exception {
        //given
        Member member = Member.createMember(test_nickName, test_email, null);

        //when
        memberRepository.save(member);
        entityManager.flush();

        //then
        fail("should throw ConstraintViolationException");
    }

    @Test(expected = ConstraintViolationException.class)
    public void save_fail_password_empty_String() throws Exception {
        //given
        Member member = Member.createMember(test_nickName, test_email, "");

        //when
        memberRepository.save(member);
        entityManager.flush();

        //then
        fail("should throw ConstraintViolationException");
    }

    @Test(expected = ConstraintViolationException.class)
    public void save_fail_password_blank_white_space() throws Exception {
        //given
        Member member = Member.createMember(test_nickName, test_email, " ");

        //when
        memberRepository.save(member);
        entityManager.flush();

        //then
        fail("should throw ConstraintViolationException");
    }

    @Test(expected = ConstraintViolationException.class)
    public void save_fail_password_contains_no_upper_case() throws Exception {
        //given
        Member member = Member.createMember(test_nickName, test_email, "wjdekdns1!");

        //when
        memberRepository.save(member);
        entityManager.flush();

        //then
        fail("should throw ConstraintViolationException");
    }

    @Test(expected = ConstraintViolationException.class)
    public void save_fail_password_contains_no_special_character() throws Exception {
        //given
        Member member = Member.createMember(test_nickName, test_email, "Wjdekdns1");

        //when
        memberRepository.save(member);
        entityManager.flush();

        //then
        fail("should throw ConstraintViolationException");
    }

    @Test(expected = ConstraintViolationException.class)
    public void save_fail_password_contains_no_number() throws Exception {
        //given
        Member member = Member.createMember(test_nickName, test_email, "Wjdekdns!");

        //when
        memberRepository.save(member);
        entityManager.flush();

        //then
        fail("should throw ConstraintViolationException");
    }

    @Test(expected = ConstraintViolationException.class)
    public void save_fail_password_length_is_GreaterEqual_than_8() throws Exception {
        //given
        Member member = Member.createMember(test_nickName, test_email, "Wjdek1!");

        //when
        memberRepository.save(member);
        entityManager.flush();

        //then
        fail("should throw ConstraintViolationException");
    }

    @Test(expected = ConstraintViolationException.class)
    public void save_fail_password_length_is_LessEqual_than_20() throws Exception {
        //given
        Member member = Member.createMember(test_nickName, test_email, "Wjdekdns!123asd123sasx");

        //when
        memberRepository.save(member);
        entityManager.flush();

        //then
        fail("should throw ConstraintViolationException");
    }

    @Test
    public void findById_success() throws Exception {
        //given
        Member member = Member.createMember(test_nickName,test_email,test_password);
        Member savedMember = memberRepository.save(member);
        entityManager.flush();

        //when
        Member findMember = memberRepository.findById(savedMember.getId()).orElseThrow(() -> new IllegalArgumentException("no such data"));

        //then
        System.out.println(findMember.toString());
        assertNotNull(findMember);
        assertNotNull(findMember.getId());
        assertEquals(test_nickName, findMember.getNickName());
        assertEquals(test_email, findMember.getEmail());
        assertEquals(test_password, findMember.getPassword());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findById_fail_none_exist_id () {
        //given
        Member member = Member.createMember(test_nickName,test_email,test_password);
        Member savedMember = memberRepository.save(member);
        entityManager.flush();

        //when
        Long nonExistId = savedMember.getId()+100000;
        Member findMember = memberRepository.findById(nonExistId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        //then
        assertNull(findMember);
    }

    @Test
    public void findAll_success_exist_data() throws Exception {
        //given
        final int numberOfData = 3;
        for(int i = 1 ; i <= numberOfData ; i ++) {
            memberRepository.save(Member.createMember(new NickName("tester"+i), new Email("tester"+i+"@gmail.com"),"Tester!"+i));
        }
        entityManager.flush();

        //when
        List<Member> memberList = memberRepository.findAll();

        //then
        assertNotNull(memberList);
        assertEquals(numberOfData, memberList.size());
    }

    @Test
    public void findAll_success_no_data_return_Empty_List() throws Exception {
        //given
        final int numberOfData = 0;
        //when
        List<Member> memberList = memberRepository.findAll();

        //then
        assertNotNull(memberList);
        assertEquals(numberOfData, memberList.size());
    }

    @Test
    public void findByNickName_success() throws Exception {
        //given
        Member member = Member.createMember(test_nickName,test_email,test_password);
        Member savedMember = memberRepository.save(member);
        entityManager.flush();

        //when
        Member findMember = memberRepository.findByNickName(savedMember.getNickName()).orElseThrow(() -> new IllegalArgumentException("no such data"));

        //then
        assertNotNull(findMember);
        assertNotNull(findMember.getId());
        assertEquals(test_nickName, findMember.getNickName());
        assertEquals(test_email, findMember.getEmail());
        assertEquals(test_password, findMember.getPassword());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findByNickName_success_noneExistNickName() throws Exception {
        //given
        NickName noneExistNickName = new NickName("NONE");

        //when
        Member findMember = memberRepository.findByNickName(noneExistNickName).orElseThrow(() -> new IllegalArgumentException("no such data"));

        //then
        assertNull(findMember);
    }

    @Test
    public void findByEmail_success() throws Exception {
        //given
        Member member = Member.createMember(test_nickName,test_email,test_password);
        memberRepository.save(member);
        entityManager.flush();
        //when
        Member findMember = memberRepository.findByEmail(test_email).orElseThrow(() -> new IllegalArgumentException("no such data"));;


        //then
        assertNotNull(findMember);
        assertNotNull(findMember.getId());
        assertEquals(test_nickName, findMember.getNickName());
        assertEquals(test_email, findMember.getEmail());
        assertEquals(test_password, findMember.getPassword());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findByEmail_fail_NoneExistEmail() throws Exception {
        //given
        Email nonExistEmail = new Email("noexistTest@gmail.com");

        //when
        Member findMember = memberRepository.findByEmail(nonExistEmail).orElseThrow(() -> new IllegalArgumentException("no such data"));

        //then
        assertNull(findMember);
    }

}