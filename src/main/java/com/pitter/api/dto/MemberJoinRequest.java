package com.pitter.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;
import com.pitter.domain.entity.Member;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@JsonSerialize
@NoArgsConstructor
public class MemberJoinRequest {

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Length(min = 2, max = 10)
    private String nickName;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Pattern(regexp = "\\w+@\\w+\\.\\w+(\\.\\w+)?",
            message = "올바르지 않은 이메일 형식입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;

    public Member toMemberEntity() {
        return Member.createMember(this.nickName, this.email, this.password);
    }

}
