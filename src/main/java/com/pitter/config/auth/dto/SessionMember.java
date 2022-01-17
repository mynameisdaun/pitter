package com.pitter.config.auth.dto;

import com.pitter.domain.entity.Member;
import lombok.Getter;

import java.io.Serializable;
@Getter
public class SessionMember implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionMember(Member member) {
        this.name = member.getNickName();
        this.email = member.getEmail();
        //TODO: 프로필 사진(Avatar Update)
        this.picture = "default";
    }

}
