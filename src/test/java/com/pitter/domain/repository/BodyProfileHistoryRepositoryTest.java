package com.pitter.domain.repository;

import com.pitter.domain.entity.BodyProfile;
import com.pitter.domain.entity.BodyProfileHistory;
import com.pitter.domain.entity.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BodyProfileHistoryRepositoryTest {

    Logger logger = LoggerFactory.getLogger(BodyProfileHistoryRepositoryTest.class);

    @Autowired BodyProfileHistoryRepository bodyProfileHistoryRepository;

    @Autowired MemberRepository memberRepository;

    @Autowired EntityManager em;

    @Test
    public void findAll_success() throws Exception {
        //given
        Member member = Member.createMember("tester","test@pitter.com","xpTmxm12!");
        memberRepository.save(member);
        BodyProfile bodyProfile1 = BodyProfile.createBodyProfile(90.0,80.0, 174.0, LocalDateTime.now());
        bodyProfileHistoryRepository.save(BodyProfileHistory.createBodyProfileHistory(bodyProfile1, member));
        em.flush();
        em.clear();

        //when
        List<BodyProfileHistory> bodyProfileHistoryList = bodyProfileHistoryRepository.findAll();

        //then
        assertThat(bodyProfileHistoryList.size()).isEqualTo(1);

        /* bodyProfileHistory를 출력해 본다 */
        bodyProfileHistoryList.forEach( bodyProfileHistory -> {
            logger.info("\n[=======================DEBUG BEGIN=======================] \n " +
                            "{} \n" +
                          "[=======================DEBUG E N D=======================]", bodyProfileHistory.toString());
        });
    }
}