package com.jpaexample.jpapratice.ch04.entity;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {

    private LocalDateTime createdTime;

    private LocalDateTime lastModifiedTime;
}
