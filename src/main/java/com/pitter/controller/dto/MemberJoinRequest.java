package com.pitter.controller.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pitter.domain.entity.Member;
import com.pitter.domain.wrapper.Email;
import com.pitter.domain.wrapper.NickName;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberJoinRequest {

    private String nickName;

    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;

    public Member toMemberEntity() {
        return Member.createMember(new NickName(nickName), new Email(this.email), this.password);
    }

}
