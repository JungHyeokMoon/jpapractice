package com.jpaexample.jpapratice.ch06;

import com.jpaexample.jpapratice.ch05.entity.BaseEntity;
import com.jpaexample.jpapratice.ch05.entity.Order;
import lombok.Builder;
import lombok.Getter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;

@Entity
@Table(name="DELIVERY")
@AttributeOverride(name="id", column = @Column(name = "DELIVERY_ID"))
@Getter
public class Delivery extends BaseEntity {

    @OneToOne(mappedBy ="delivery")
    private Order order;

    private String city;
    private String street;
    private String zipcode;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    public void setOrder(Order order) {
        this.order = order;
    }

    @Builder
    public Delivery(String city, String street, String zipcode, DeliveryStatus status) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
        this.status = status;
    }
}
