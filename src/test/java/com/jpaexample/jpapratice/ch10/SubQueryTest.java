package com.jpaexample.jpapratice.ch10;

import com.jpaexample.jpapratice.configuration.DataJpaTestConfig;
import com.jpaexample.jpapratice.domain.ch05.entity.Order;
import com.jpaexample.jpapratice.domain.ch05.entity.User;
import com.jpaexample.jpapratice.domain.ch05.enums.OrderStatus;
import com.jpaexample.jpapratice.repository.UserRepository;
import com.jpaexample.jpapratice.domain.ch09.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
@Import(DataJpaTestConfig.class)
public class SubQueryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp(){
        User user = User.builder().address(Address.builder().city("city1").street("street1").zipcode("zipcode1").build())
                .name("name1").build();
        Order order1 = Order.builder().orderStatus(OrderStatus.ORDER).build();
        Order order2 = Order.builder().orderStatus(OrderStatus.ORDER).build();
        Order order3 = Order.builder().orderStatus(OrderStatus.CANCEL).build();
        Order order8 = Order.builder().orderStatus(OrderStatus.CANCEL).build();

        user.addOrder(order1);
        user.addOrder(order2);
        user.addOrder(order3);
        user.addOrder(order8);

        User user1 = User.builder().name("name2").address(Address.builder().city("city2").street("street2").zipcode("zipcode2").build()).build();

        Order order4 = Order.builder().orderStatus(OrderStatus.ORDER).build();
        Order order5 = Order.builder().orderStatus(OrderStatus.CANCEL).build();
        Order order6 = Order.builder().orderStatus(OrderStatus.CANCEL).build();

        user1.addOrder(order4);
        user1.addOrder(order5);
        user1.addOrder(order6);

        userRepository.save(user);
        userRepository.save(user1);
        entityManager.clear();
    }

    @Test
    @DisplayName("EXISTS 함수")
    void test(){
        String jpql = "SELECT o FROM Order o WHERE EXISTS (SELECT u FROM o.user u where u.name = :name)";
        List<Order> resultList = entityManager.createQuery(jpql, Order.class)
                .setParameter("name", "name1")
                .getResultList();

        assertEquals(4,resultList.size());
    }

    @Test
    @DisplayName("ANY 함수")
    void test2(){
        String jpql = "SELECT o FROM Order o WHERE o.user = ANY (SELECT u FROM User u)";
        List<Order> resultList = entityManager.createQuery(jpql, Order.class).getResultList();
        assertEquals(7,resultList.size());
    }

    @Test
    @DisplayName("Collection 식")
    void test3(){
        String jpql = "SELECT u FROM User u WHERE u.orders is not empty";
        List<User> resultList = entityManager.createQuery(jpql, User.class).getResultList();
        assertEquals(2,resultList.size());
    }
}
