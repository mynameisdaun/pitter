package com.pitter.domain.entity;

import com.pitter.domain.wrapper.Email;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Member extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Column(unique = true)
    @Length(min = 2, max = 10)
    private String nickName;

    @Column(unique = true)
    @Embedded
    private Email email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,20}",
             message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    @ToString.Exclude
    private transient String password;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = " varchar(20) default 'GUEST' ")
    private Role role;

    private Member(String nickName, Email email, String password, Role role) {
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public static Member createMember(String nickName, Email email, String password) throws IllegalArgumentException {
        validateEmail(email);
        return new Member(nickName, email, password, Role.GUEST);
    }
    public static Member createMember(String nickName, Email email, String password, Role role) {
        validateEmail(email);
        return new Member(nickName, email, password, role);
    }

    private static void validateEmail(Email email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("이메일은 필수 값 입니다.");
        }
    }

}
