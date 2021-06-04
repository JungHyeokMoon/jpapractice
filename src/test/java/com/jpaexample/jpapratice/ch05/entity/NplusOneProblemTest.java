package com.jpaexample.jpapratice.ch05.entity;

import com.jpaexample.jpapratice.ch05.enums.OrderStatus;
import com.jpaexample.jpapratice.ch05.repository.OrderRepository;
import com.jpaexample.jpapratice.ch05.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

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
        User user = User.builder().city("city1").street("street1").name("name1").zipcode("1234").build();
        Order order1 = Order.builder().orderStatus(OrderStatus.ORDER).build();
        Order order2 = Order.builder().orderStatus(OrderStatus.ORDER).build();
        Order order3 = Order.builder().orderStatus(OrderStatus.CANCEL).build();

        user.addOrder(order1);
        user.addOrder(order2);
        user.addOrder(order3);

        userList.add(user);

        User user1 = User.builder().city("city2").street("strret2").name("name2").zipcode("1234").build();

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
}
