package com.pitter.domain.repository;

import com.pitter.domain.entity.BodyProfile;
import com.pitter.domain.entity.BodyProfileHistory;
import com.pitter.domain.entity.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BodyProfileHistoryRepositoryTest {

    @Autowired BodyProfileHistoryRepository bodyProfileHistoryRepository;

    @Autowired MemberRepository memberRepository;

    @Autowired EntityManager em;

    @Test
    public void findByMember_success() throws Exception {
        //given
        Member member = Member.createMember("tester","test@pitter.com","xpTmxm12!");
        memberRepository.save(member);
        BodyProfile bodyProfile1 = BodyProfile.createBodyProfile(90.0,80.0, 174.0, LocalDateTime.now());
        BodyProfile bodyProfile2 = BodyProfile.createBodyProfile(88.0,80.0, 174.0, LocalDateTime.now().minusDays(1));
        BodyProfile bodyProfile3 = BodyProfile.createBodyProfile(83.0,80.0, 174.0, LocalDateTime.now().minusDays(2));
        bodyProfileHistoryRepository.save(BodyProfileHistory.createBodyProfileHistory(bodyProfile1, member));
        bodyProfileHistoryRepository.save(BodyProfileHistory.createBodyProfileHistory(bodyProfile2, member));
        bodyProfileHistoryRepository.save(BodyProfileHistory.createBodyProfileHistory(bodyProfile3, member));
        em.flush();
        em.clear();

        //when
        List<BodyProfileHistory> bodyProfileHistoryList = bodyProfileHistoryRepository.findAll();

        bodyProfileHistoryList.forEach( bodyProfileHistory -> {
           System.out.println(bodyProfileHistory.toString());
        });

    }

    @Test
    public void doubled() throws Exception {
        //given
        double a = 88.0;
        double b = 174.0;

        double c = a / (b*b) * 10000;

        System.out.println(c);
        //when
        //then
    }

}