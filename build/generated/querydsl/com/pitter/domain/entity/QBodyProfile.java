package com.pitter.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBodyProfile is a Querydsl query type for BodyProfile
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QBodyProfile extends BeanPath<BodyProfile> {

    private static final long serialVersionUID = -1135477525L;

    public static final QBodyProfile bodyProfile = new QBodyProfile("bodyProfile");

    public final NumberPath<Double> bmi = createNumber("bmi", Double.class);

    public final DateTimePath<java.time.LocalDateTime> checkAt = createDateTime("checkAt", java.time.LocalDateTime.class);

    public final NumberPath<Double> currentWeight = createNumber("currentWeight", Double.class);

    public final NumberPath<Double> height = createNumber("height", Double.class);

    public final NumberPath<Double> targetWeight = createNumber("targetWeight", Double.class);

    public QBodyProfile(String variable) {
        super(BodyProfile.class, forVariable(variable));
    }

    public QBodyProfile(Path<? extends BodyProfile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBodyProfile(PathMetadata metadata) {
        super(BodyProfile.class, metadata);
    }

}

