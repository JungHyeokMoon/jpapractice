package com.jpaexample.jpapratice.ch06;

import com.jpaexample.jpapratice.ch05.entity.BaseEntity;
import com.jpaexample.jpapratice.ch05.entity.Order;
import com.jpaexample.jpapratice.ch09.Address;
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

    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    public void setOrder(Order order) {
        this.order = order;
    }

    @Builder
    public Delivery(Address address, DeliveryStatus status) {
        this.address = address;
        this.status = status;
    }
}
