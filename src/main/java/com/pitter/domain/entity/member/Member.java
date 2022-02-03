package com.pitter.domain.entity.member;

import com.pitter.domain.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity @Getter
@NoArgsConstructor
@ToString
public class Member extends BaseEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    @Embedded
    private NickName nickName;

    @Column(unique = true)
    @Embedded
    private Email email;

    //TODO: 비밀번호를 어떻게 처리하면 좋을까..
    //    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,20}",
    //    message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    @ToString.Exclude
    private transient String password;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = " varchar(20) default 'GUEST' ")
    private Role role;

    private String profileImageUrl;

    //TODO: 프로파일 이미지 어떻게 처리하면 좋을지 생각해보자.
    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl=profileImageUrl;
    }

    private Member(NickName nickName, Email email, String password, Role role) {
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public static Member createMember(NickName nickName, Email email, String password) throws IllegalArgumentException {
        return new Member(nickName, email, password, Role.USER);
    }
    public static Member createMember(NickName nickName, Email email, String password, Role role) {
        return new Member(nickName, email, password, role);
    }
}
