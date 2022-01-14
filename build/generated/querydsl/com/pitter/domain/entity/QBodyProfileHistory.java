package com.pitter.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBodyProfileHistory is a Querydsl query type for BodyProfileHistory
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBodyProfileHistory extends EntityPathBase<BodyProfileHistory> {

    private static final long serialVersionUID = -1796787927L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBodyProfileHistory bodyProfileHistory = new QBodyProfileHistory("bodyProfileHistory");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QBodyProfile bodyProfile;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final QMember member;

    public QBodyProfileHistory(String variable) {
        this(BodyProfileHistory.class, forVariable(variable), INITS);
    }

    public QBodyProfileHistory(Path<? extends BodyProfileHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBodyProfileHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBodyProfileHistory(PathMetadata metadata, PathInits inits) {
        this(BodyProfileHistory.class, metadata, inits);
    }

    public QBodyProfileHistory(Class<? extends BodyProfileHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bodyProfile = inits.isInitialized("bodyProfile") ? new QBodyProfile(forProperty("bodyProfile")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

