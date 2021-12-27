package com.pitter.domain.entity;


import lombok.Getter;
import javax.persistence.*;
import java.util.Date;

@Embeddable
@Getter
public class BodyProfile {

    @Column(columnDefinition = "Integer default 90")
    private double currentWeight;

    @Column(columnDefinition = "Integer default 80")
    private double targetWeight;

    @Column(columnDefinition = "Integer default 175")
    private double height;

    @Column(columnDefinition = "Integer default 35")
    private double bmi;

    @Temporal(TemporalType.TIMESTAMP)
    private Date checkAt;

}
