package com.pitter.domain.entity.member;

import com.google.common.base.Strings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @ToString
public class NickName {
    private String nickName;

    public NickName(String nickName) {
        if(Strings.isNullOrEmpty(nickName)) {
            throw new IllegalArgumentException("닉네임은 필수 입력 값 입니다.");
        }
        if( nickName.length() < 2 || nickName.length() > 10 ) {
            throw new IllegalArgumentException("닉네임은 2글자 ~ 10글자 사이어야 합니다.");
        }
        this.nickName = nickName;
    }

    public boolean isEmpty() {
        return this.nickName ==null || this.nickName.equals("") || this.nickName.equals(" ");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NickName nickName = (NickName) o;

        return this.nickName != null ? this.nickName.equals(nickName.nickName) : nickName.nickName == null;
    }

    @Override
    public int hashCode() {
        return nickName != null ? nickName.hashCode() : 0;
    }
}
