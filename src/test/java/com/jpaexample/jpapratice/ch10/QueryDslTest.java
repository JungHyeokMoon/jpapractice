package com.jpaexample.jpapratice.ch10;

import com.jpaexample.jpapratice.domain.ch05.entity.User;
import com.jpaexample.jpapratice.repository.UserRepository;
import com.jpaexample.jpapratice.domain.ch09.Address;
import com.querydsl.jpa.impl.JPAQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static com.jpaexample.jpapratice.domain.ch05.entity.QUser.user;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class QueryDslTest {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        User user = User.builder().name("name1").address(Address.builder().street("street1").zipcode("zipcode1").city("city1").build()).build();
        User user1 = User.builder().name("name2").address(Address.builder().street("street1").zipcode("zipcode1").city("city1").build()).build();

        userRepository.save(user);
        userRepository.save(user1);
        entityManager.clear();
    }

    @Test
    @DisplayName("queryDsl 테스트")
    void test(){
        JPAQuery<User> query = new JPAQuery<>(entityManager);
        query.from(user)
                .where(user.name.eq("name1"));

        List<User> fetch = query.fetch();
        assertEquals(1,fetch.size());

    }

    @Test
    @DisplayName("group by test")
    void test2(){
        JPAQuery<User> query = new JPAQuery<>(entityManager);
        query.from(user)
                .groupBy(user.name);

        query.fetch();
    }
}
