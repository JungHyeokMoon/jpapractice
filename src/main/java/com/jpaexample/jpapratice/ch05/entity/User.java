package com.jpaexample.jpapratice.ch05.entity;

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

    private String city;
    private String street;
    private String zipcode;

    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    Set<Order> orders = new LinkedHashSet<>();

    @Builder
    public User(String name, String city, String street, String zipcode) {
        this.name = name;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public void addOrder(Order order){
        this.orders.add(order);
        order.setUser(this);
    }
}
