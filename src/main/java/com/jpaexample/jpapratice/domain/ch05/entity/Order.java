package com.jpaexample.jpapratice.domain.ch05.entity;

import com.jpaexample.jpapratice.domain.ch05.enums.OrderStatus;
import com.jpaexample.jpapratice.domain.ch06.Delivery;
import com.jpaexample.jpapratice.domain.ch06.DeliveryStatus;
import com.jpaexample.jpapratice.exception.BusinessException;
import com.jpaexample.jpapratice.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "ORDERS")
@Entity
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "ORDER_ID")),
        @AttributeOverride(name = "createdTime", column = @Column(name = "ORDER_TIME"))
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Order extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID") //자신의 테이블에 있는 외래키를 관리할 수 있으므로 좋다.
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;

    @Builder
    public Order(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setUser(User user) {
        this.user = user;
        user.getOrders().add(this);
    }

    public void setStatus(OrderStatus orderStatus){
        this.orderStatus = orderStatus;
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    public static Order createOrder(User user, Delivery delivery, OrderItem... orderItems){
        Order order = Order.builder().orderStatus(OrderStatus.ORDER).build();
        order.setUser(user);
        order.setDelivery(delivery);
        for(var item : orderItems)
            order.addOrderItem(item);
        return order;
    }

    public int getTotalPrice(){
        int totalPrice=0;
        for(var item : orderItems)
            totalPrice += item.getTotalPrice();
        return totalPrice;
    }

    public void cancel(){
        if(delivery.getStatus()== DeliveryStatus.COMP){
            throw new BusinessException(ErrorCode.ALREADY_COMP);
        }

        this.setStatus(OrderStatus.CANCEL);
        for(var item : orderItems)
            item.cancel();
    }
}
