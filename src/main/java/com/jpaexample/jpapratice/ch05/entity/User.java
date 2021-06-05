package com.jpaexample.jpapratice.ch05.entity;

import com.jpaexample.jpapratice.ch09.Address;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "USER")
@AttributeOverride(name="id", column = @Column(name="userId"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class User extends BaseEntity{

    private String name;

    @Embedded
    private Address address;

    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    Set<Order> orders = new LinkedHashSet<>();

    @Builder
    public User(String name,Address address) {
        this.name = name;
        this.address = address;
    }

    public void addOrder(Order order){
        this.orders.add(order);
        order.setUser(this);
    }
}
