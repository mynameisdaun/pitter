package com.example.demo.domain.entity;


import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    private String username;

    private String email;

    private String password;

    @Embedded
    private BodyProfile bodyProfile;



}
