package com.shop.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


@EntityListeners(value = {AuditingEntityListener.class}) //@CreatedDated와 @LastModifiedDate를 사용하기 위해.. 그런데 지워도 이상하게 에러는 안 난다.
@MappedSuperclass // 어노테이션이 적용된 클래스는 데이터베이스 테이블과 직접적으로 매핑되지 않습니다. 대신, 이 클래스의 필드들이 하위 엔터티 클래스들에게 상속되어 공통된 매핑 정보를 제공합니다
@Getter@Setter
public abstract class BaseTimeEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regTime;

    @LastModifiedDate
    private LocalDateTime updateTime;

}
