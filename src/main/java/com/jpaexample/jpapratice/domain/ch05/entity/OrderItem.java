package com.jpaexample.jpapratice.domain.ch05.entity;

import com.jpaexample.jpapratice.domain.ch06.Item;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ORDER_ITEM")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "ORDER_ITEM_ID"))
@Getter
public class OrderItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    private int orderPrice;
    private int count;

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Builder
    public OrderItem(int orderPrice, int count) {
        this.orderPrice = orderPrice;
        this.count = count;
    }

    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = OrderItem.builder().orderPrice(orderPrice).count(count).build();
        orderItem.setItem(item);
        item.removeStock(count);
        return orderItem;
    }

    public void cancel() {
        getItem().addStock(count);
    }

    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
