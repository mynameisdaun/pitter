package com.pitter.controller.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@JsonSerialize
@NoArgsConstructor
public class BodyProfileRequest {

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickName;

    @NotBlank(message = "측정 시작일을 입력 해주세요.")
    private String firstCheckUpDate;

    @NotBlank(message = "측정 종료일을 입력 해주세요.")
    private String lastCheckUpDate;

}
