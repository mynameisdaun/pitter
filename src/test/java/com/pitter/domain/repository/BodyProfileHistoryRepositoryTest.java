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
import static org.assertj.core.api.Fail.fail;


@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.config.location=classpath:/application-test.yml")
@Transactional
public class BodyProfileHistoryRepositoryTest {

    Logger logger = LoggerFactory.getLogger(BodyProfileHistoryRepositoryTest.class);

    @Autowired BodyProfileHistoryRepository bodyProfileHistoryRepository;

    @Autowired MemberRepository memberRepository;

    @Autowired EntityManager em;

    @Test
    public void findHistoryByCheckAtBetween_success() throws Exception {
        //given
        Member member = Member.createMember("tester","test@pitter.com","xpTmxm12!");
        memberRepository.save(member);
        BodyProfile bodyProfile1 = BodyProfile.createBodyProfile(90.0,80.0, 174.0, LocalDateTime.now());
        BodyProfile bodyProfile2 = BodyProfile.createBodyProfile(88.0,80.0, 174.0, LocalDateTime.now().minusDays(2));
        BodyProfile bodyProfile3 = BodyProfile.createBodyProfile(85.0,80.0, 174.0, LocalDateTime.now().minusDays(4));
        bodyProfileHistoryRepository.save(BodyProfileHistory.createBodyProfileHistory(bodyProfile1, member));
        bodyProfileHistoryRepository.save(BodyProfileHistory.createBodyProfileHistory(bodyProfile2, member));
        bodyProfileHistoryRepository.save(BodyProfileHistory.createBodyProfileHistory(bodyProfile3, member));
        em.flush();
        em.clear();
                
        //when
        List<BodyProfileHistory> results = bodyProfileHistoryRepository.findHistoryByCheckAtBetween("tester", LocalDateTime.now().minusDays(3),LocalDateTime.now());

        //then
        assertThat(results.size()).isEqualTo(2);
    }

}