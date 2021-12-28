package com.pitter.domain.entity;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import lombok.Getter;
import org.checkerframework.common.aliasing.qual.LeakedToResult;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Arrays;

@Entity @Getter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Column(unique = true)
    @Length(min = 2, max = 10)
    private String nickName;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Pattern(regexp = "\\w+@\\w+\\.\\w+(\\.\\w+)?",
             message = "올바르지 않은 이메일 형식입니다.")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,20}",
             message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;

    @Embedded
    private BodyProfile bodyProfile;

    private Member(String nickName, String email, String password) {
        this.nickName = nickName;
        this.email = email;
        this.password = password;
    }

    protected Member() { }

    public static Member createMember(String nickName, String email, String password) { return new Member(nickName, email, password); }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", bodyProfile=" + bodyProfile +
                '}';
    }
}
