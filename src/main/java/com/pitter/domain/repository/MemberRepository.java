package com.pitter.domain.repository;

import com.pitter.domain.entity.Member;
import com.pitter.domain.wrapper.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByNickName(String nickName);

    Optional<Member> findByEmail(Email email);
}
