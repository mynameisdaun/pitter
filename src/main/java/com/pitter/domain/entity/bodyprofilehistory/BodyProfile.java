package com.pitter.domain.entity.bodyprofilehistory;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @ToString
public class BodyProfile {

    private double currentWeight;
    private double targetWeight;
    private double height;
    private double bmi;
    private LocalDateTime checkAt;

    private BodyProfile(double currentWeight, double targetWeight, double height, LocalDateTime checkAt) {
        this.currentWeight = currentWeight;
        this.targetWeight = targetWeight;
        this.height = height;
        this.bmi = (Math.round((currentWeight / ((height / 100) * (height / 100))) * 100)) / 100.0;
        this.checkAt = checkAt;
    }

    public static BodyProfile createBodyProfile(double currentWeight, double targetWeight, double height, LocalDateTime checkAt) {
        return new BodyProfile(currentWeight, targetWeight, height, checkAt);
    }

}