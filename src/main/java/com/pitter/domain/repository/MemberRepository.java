package com.pitter.domain.repository;

import com.pitter.domain.entity.member.Member;
import com.pitter.domain.entity.member.Email;
import com.pitter.domain.entity.member.NickName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByNickName(NickName nickName);

    Optional<Member> findByEmail(Email email);
}
