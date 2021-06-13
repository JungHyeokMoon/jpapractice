package com.jpaexample.jpapratice.domain.ch05.entity;

import com.jpaexample.jpapratice.configuration.DataJpaTestConfig;
import com.jpaexample.jpapratice.domain.ch05.enums.OrderStatus;
import com.jpaexample.jpapratice.domain.ch09.Address;
import com.jpaexample.jpapratice.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@EnableJpaAuditing
@Import(DataJpaTestConfig.class)
class UserTest {

    @Autowired
    private TestEntityManager testEntityManager;

    private User user;

    @Autowired
    private UserRepository userRepository;

    @Test
    void test(){
        User user = User.builder().name("정혁").address(Address.builder().city("city").street("street").zipcode("zipcode").build()).build();
        testEntityManager.persist(user); //ID가 IDENTITY라 어짜피 쓰기지연이 되지않음.
        assertEquals(1,user.getId());
        assertNotNull(user.getOrders());
        assertEquals(0,user.getOrders().size());
        assertThat(user.getCreatedTime()).isBefore(LocalDateTime.now());

        Order order1 = Order.builder().orderStatus(OrderStatus.ORDER).build();
        Order order2 = Order.builder().orderStatus(OrderStatus.ORDER).build();
        Order order3 = Order.builder().orderStatus(OrderStatus.CANCEL).build();

        order1.setUser(user);
        order2.setUser(user);
        order3.setUser(user); //CASCADE가 붙어있지 않으므로 ORDER쪽에서 setUser를 해줘야함.

        testEntityManager.persist(order1);
        testEntityManager.persist(order2);
        Order persist = testEntityManager.persist(order3);

        testEntityManager.flush();
        testEntityManager.clear();

        User user1 = testEntityManager.find(User.class, user.getId());

        assertFalse(user==user1);
        assertEquals(3,user1.getOrders().size());
        assertEquals(OrderStatus.CANCEL,persist.getOrderStatus());
    }

    @Test
    @DisplayName("findByName - Test")
    void test2(){
        User user2 = User.builder().name("name1").address(Address.builder().city("city1").zipcode("zipcode1").street("street1").build()).build();
        userRepository.save(user2);

        User name1 = userRepository.findByName("name1").get();
        assertEquals("name1",name1.getName());
    }
}