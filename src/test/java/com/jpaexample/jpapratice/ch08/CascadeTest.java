package com.jpaexample.jpapratice.ch08;

import com.jpaexample.jpapratice.ch05.entity.Order;
import com.jpaexample.jpapratice.ch05.entity.OrderItem;
import com.jpaexample.jpapratice.ch05.enums.OrderStatus;
import com.jpaexample.jpapratice.ch06.Delivery;
import com.jpaexample.jpapratice.ch06.DeliveryStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;

@DataJpaTest
@ActiveProfiles("test")
public class CascadeTest {
    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Cascade 테스트")
    void test(){
        Delivery delivery = Delivery.builder().city("city1").status(DeliveryStatus.READY).street("street1").zipcode("zipcode1").build();
        OrderItem orderItem1 = OrderItem.builder().orderPrice(1000).count(1).build();
        OrderItem orderItem2 = OrderItem.builder().orderPrice(2000).count(1).build();

        Order order = Order.builder().orderStatus(OrderStatus.ORDER).build();
        order.setDelivery(delivery);
        order.addOrderItem(orderItem1);
        order.addOrderItem(orderItem2);
        entityManager.persist(order);

    }
}
