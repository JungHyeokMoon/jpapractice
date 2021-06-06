package com.jpaexample.jpapratice.domain.ch05.entity;

import com.jpaexample.jpapratice.domain.ch05.enums.OrderStatus;
import com.jpaexample.jpapratice.repository.OrderRepository;
import com.jpaexample.jpapratice.repository.UserRepository;
import com.jpaexample.jpapratice.domain.ch09.Address;
import com.querydsl.jpa.impl.JPAQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.jpaexample.jpapratice.domain.ch05.entity.QOrder.*;
import static com.jpaexample.jpapratice.domain.ch05.entity.QUser.*;

@DataJpaTest
@ActiveProfiles("test")
public class NplusOneProblemTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp(){
        List<User> userList = new ArrayList<>();
        User user = User.builder().address(Address.builder().city("city1").street("street1").zipcode("zipcode1").build())
                .name("name1").build();
        Order order1 = Order.builder().orderStatus(OrderStatus.ORDER).build();
        Order order2 = Order.builder().orderStatus(OrderStatus.ORDER).build();
        Order order3 = Order.builder().orderStatus(OrderStatus.CANCEL).build();

        user.addOrder(order1);
        user.addOrder(order2);
        user.addOrder(order3);

        userList.add(user);

        User user1 = User.builder().name("name2").address(Address.builder().city("city2").street("street2").zipcode("zipcode2").build()).build();

        Order order4 = Order.builder().orderStatus(OrderStatus.ORDER).build();
        Order order5 = Order.builder().orderStatus(OrderStatus.CANCEL).build();
        Order order6 = Order.builder().orderStatus(OrderStatus.CANCEL).build();

        user1.addOrder(order4);
        user1.addOrder(order5);
        user1.addOrder(order6);

        userList.add(user1);

        userRepository.saveAll(userList);
        entityManager.clear();
    }

    @Test
    @DisplayName("N+1 Problem")
    void test(){

        List<User> allJoinFetch = userRepository.findAllJoinFetch();
        for(User user : allJoinFetch){
            user.getOrders().size();
        }
    }

    @Test
    @DisplayName("fetch join with queryDsl")
    void test2(){
        JPAQuery query = new JPAQuery(entityManager);
        List<User> fetch = query.from(user)
                .innerJoin(user.orders,order).fetchJoin().fetch();
        for(User user : fetch){
            user.getOrders().size();
        }
    }
}
