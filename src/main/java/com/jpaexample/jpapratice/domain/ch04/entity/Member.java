package com.jpaexample.jpapratice.domain.ch04.entity;

import com.jpaexample.jpapratice.domain.ch04.enums.RoleType;
import com.jpaexample.jpapratice.domain.ch05.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Table(name="MEMBER", uniqueConstraints = {@UniqueConstraint(
        name = "NAME_CREATED_TIME_UNIQUE",
        columnNames = {"name","createdTime"}
)})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false, length = 10)
    private String username;
    private Integer age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Lob
    private String description;

    @Transient
    private String temp;


    @Builder
    public Member(String username, Integer age, RoleType roleType, String description, String temp) {
        this.username = username;
        this.age = age;
        this.roleType = roleType;
        this.description = description;
        this.temp = temp;
    }
}
