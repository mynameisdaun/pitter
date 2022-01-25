package com.pitter.domain.entity.bodyprofilehistory;

import com.pitter.domain.entity.BaseEntity;
import com.pitter.domain.entity.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BodyProfileHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "body_profile_history_id")
    private Long id;

    @Embedded
    private BodyProfile bodyProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static BodyProfileHistory createBodyProfileHistory(BodyProfile bodyProfile, Member member) {
        return new BodyProfileHistory(bodyProfile, member);
    }

    private BodyProfileHistory(BodyProfile bodyProfile, Member member) {
        this.bodyProfile=bodyProfile;
        this.member=member;
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).toString();
    }

}
