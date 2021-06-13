package com.jpaexample.jpapratice.ch10;

import com.jpaexample.jpapratice.configuration.DataJpaTestConfig;
import com.jpaexample.jpapratice.domain.ch05.entity.User;
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
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles("test")
@Import(DataJpaTestConfig.class)
public class JpqlTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp(){
        User user = User.builder().name("NAME1").address(Address.builder().city("CITY1").street("STREET1").zipcode("ZIPCODE").build()).build();
        User user1 = User.builder().name("NAME2").address(Address.builder().city("CITY1").street("STREET1").zipcode("ZIPCODE").build()).build();
        User user2 = User.builder().name("NAME3").address(Address.builder().city("CITY1").street("STREET1").zipcode("ZIPCODE").build()).build();
        User user3 = User.builder().name("NAME4").address(Address.builder().city("CITY1").street("STREET1").zipcode("ZIPCODE").build()).build();
        List<User> userList = Arrays.asList(user, user1, user2, user3);
        userRepository.saveAll(userList);
        entityManager.clear();
    }

    @DisplayName("Jpql 테스트")
    @Test
    void test(){
        String jpql = "select u from User as u where u.name='NAME1'";
        List<User> resultList = entityManager.createQuery(jpql, User.class).getResultList();
        assertEquals(1,resultList.size());
    }

    @Test
    @DisplayName("Jpql 테스트-1")
    void test2(){
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
        List<User> resultList = query.getResultList();
        assertEquals(4,resultList.size());
    }

    @Test
    @DisplayName("Jpql 익셉션 테스트")
    void test3(){
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
        assertThrows(NonUniqueResultException.class, query::getSingleResult);
    }

    @Test
    @DisplayName("파라미터 바인딩")
    void test4(){
        User singleResult = entityManager.createQuery("SELECT u FROM User u WHERE u.name = :name", User.class)
                .setParameter("name", "NAME1")
                .getSingleResult();
        assertEquals(singleResult.getName(),"NAME1");
    }

    @Test
    @DisplayName("페이징 API")
    void test5(){
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u ORDER BY u.id ASC", User.class);
        query.setFirstResult(0);
        query.setMaxResults(2);
        List<User> resultList = query.getResultList();
        assertEquals("NAME1",resultList.get(0).getName());
        assertEquals("NAME2",resultList.get(1).getName());
        assertEquals(2,resultList.size());

        query.setFirstResult(2);
        query.setMaxResults(2);
        List<User> resultList1 = query.getResultList();
        assertEquals("NAME3",resultList1.get(0).getName());
        assertEquals("NAME4",resultList1.get(1).getName());
    }
}
