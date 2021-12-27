package com.example.demo.domain.entity;


import lombok.Getter;
import javax.persistence.*;
import java.util.Date;

@Embeddable
@Getter
public class BodyProfile {

    private double currentWeight;

    private double targetWeight;

    private double height;

    private double bmi;

    private Date checkAt;

}
