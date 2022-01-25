package com.pitter.controller.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@JsonSerialize
@NoArgsConstructor
public class MemberJoinResponse {

    @NotBlank(message = "ID는 필수 입력 값입니다.")
    private Long id;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Length(min = 2, max = 10)
    private String nickName;

    private String email;

}
