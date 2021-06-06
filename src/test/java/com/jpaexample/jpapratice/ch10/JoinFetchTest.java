package com.jpaexample.jpapratice.ch10;

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
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class JoinFetchTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        List<User> userList = new ArrayList<>();
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

        userList.add(user);

        User user1 = User.builder().name("name2").address(Address.builder().city("city2").street("street2").zipcode("zipcode2").build()).build();

        Order order4 = Order.builder().orderStatus(OrderStatus.ORDER).build();
        Order order5 = Order.builder().orderStatus(OrderStatus.CANCEL).build();
        Order order6 = Order.builder().orderStatus(OrderStatus.CANCEL).build();

        user1.addOrder(order4);
        user1.addOrder(order5);
        user1.addOrder(order6);

        userList.add(user1);

        User user2 = User.builder().name("name3").address(Address.builder().city("city3").street("street3").zipcode("zipcode3").build()).build();
        Order order7 = Order.builder().orderStatus(OrderStatus.CANCEL).build();

        user2.addOrder(order7);
        userList.add(user2);

        userRepository.saveAll(userList);
        entityManager.clear();
    }

    @Test
    @DisplayName("Join Fetch - 다대일 조인")
    void test() {
        String jpql = "SELECT o FROM Order o join fetch o.user u where u.name = :name";
        List<Order> resultList = entityManager.createQuery(jpql, Order.class)
                .setParameter("name", "name1")
                .getResultList();

        assertEquals(4, resultList.size());

        Map<OrderStatus, List<Order>> collect = resultList.stream().collect(Collectors.groupingBy(Order::getOrderStatus));
        assertEquals(2,collect.size());
        for(var orderList : collect.values()){
            assertEquals(2,orderList.size());
        }
    }

    @Test
    @DisplayName("Join Fetch - 일대다 조인 - DISTINCT")
    void test2(){
        String jpql = "SELECT DISTINCT u FROM User u join fetch u.orders o where u.name = :name";
        List<User> resultList = entityManager.createQuery(jpql, User.class)
                .setParameter("name", "name1")
                .getResultList();

        assertEquals(1,resultList.size());
        for(var user : resultList){
            System.out.println(user.getName());
            for(var order : user.getOrders()){
                System.out.println(order.getOrderStatus());
            }
            System.out.println("---------------------");
        }
    }
}
