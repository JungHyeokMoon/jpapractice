package com.jpaexample.jpapratice.ch05.entity;

import com.jpaexample.jpapratice.ch04.entity.Member;
import com.jpaexample.jpapratice.ch05.enums.OrderStatus;
import com.jpaexample.jpapratice.ch06.Delivery;
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

//    public void setUser(User user){
//        //기존관계 제거 메서드
//        if(this.user!=null){
//            this.user.getOrders().remove(this);
//        }
//        this.user = user;
//        user.getOrders().add(this);
//    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
