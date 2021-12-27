package com.example.demo.domain.entity;


import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class BodyProfileHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "body_profile_history_id")
    private Long id;

    @Embedded
    private BodyProfile bodyProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


}
