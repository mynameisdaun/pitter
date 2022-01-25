package com.pitter.domain.entity.member;

import com.google.common.base.Strings;
import lombok.*;

import javax.persistence.Embeddable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @ToString
public class Email {
    private String email;

    public Email(String email) {
        if(Strings.isNullOrEmpty(email)) {
            throw new IllegalArgumentException("이메일은 필수 입력 값입니다.");
        }
        Pattern pattern = Pattern.compile("\\w(\\.?\\w)+@\\w+\\.\\w+(\\.\\w+)?");
        Matcher matcher = pattern.matcher(email);
        if(!matcher.find()) {
            throw new IllegalArgumentException("올바르지 않은 이메일 형식입니다.");
        }
        this.email = email;
    }

    public boolean isEmpty() {
        return this.email ==null || this.email.equals("") || this.email.equals(" ");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;

        return email != null ? email.equals(email1.email) : email1.email == null;
    }

    @Override
    public int hashCode() {
        return email != null ? email.hashCode() : 0;
    }
}
