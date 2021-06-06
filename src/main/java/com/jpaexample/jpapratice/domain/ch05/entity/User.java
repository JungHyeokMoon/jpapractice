package com.jpaexample.jpapratice.domain.ch05.entity;

import com.jpaexample.jpapratice.domain.ch09.Address;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "USER", uniqueConstraints = {@UniqueConstraint(name="NAME_UNIQUE",columnNames = {"name"})})
@AttributeOverride(name="id", column = @Column(name="userId"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class User extends BaseEntity{
    private String name;
    private String password;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Set<String> passwordListRecentFive = new LinkedHashSet<>();

    @Embedded
    private Address address;

    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    Set<Order> orders = new LinkedHashSet<>();

    @Builder
    public User(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    @Builder
    public User(String name, String currentPassword, Address address) {
        this.name = name;
        this.password = currentPassword;
        this.address = address;
    }

    public void addOrder(Order order){
        this.orders.add(order);
        order.setUser(this);
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
