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
import javax.persistence.TypedQuery;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Import(DataJpaTestConfig.class)
public class GroupByAndHavingTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp(){
        User user = User.builder().name("name1").address(Address.builder().city("city1").street("street1").zipcode("zipcode1").build()).build();
        Order order1 = Order.builder().orderStatus(OrderStatus.ORDER).build();
        Order order2 = Order.builder().orderStatus(OrderStatus.ORDER).build();
        Order order3 = Order.builder().orderStatus(OrderStatus.CANCEL).build();
        
        user.addOrder(order1);
        user.addOrder(order2);
        user.addOrder(order3);

        userRepository.save(user);
        
        entityManager.clear(); //영속성 컨텍스트 초기화
    }
    
    @Test
    @DisplayName("groupby")
    void test(){
        TypedQuery<OrderStatus> query = entityManager.createQuery("SELECT o.orderStatus FROM Order o GROUP BY o.orderStatus", OrderStatus.class);
        List<OrderStatus> resultList = query.getResultList();
        assertEquals(2,resultList.size());
        assertTrue(resultList.contains(OrderStatus.ORDER));
        assertTrue(resultList.contains(OrderStatus.CANCEL));
    }

    @Test
    @DisplayName("Join 테스트")
    void test2(){
        String query = "SELECT o FROM Order o JOIN o.user u "
                + "WHERE u.name = :name";
        List<Order> resultList = entityManager.createQuery(query, Order.class)
                .setParameter("name", "name1")
                .getResultList();

        assertEquals(3,resultList.size());
    }

}
