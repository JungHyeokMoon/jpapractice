package com.jpaexample.jpapratice.ch08;

import com.jpaexample.jpapratice.domain.ch05.entity.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class ProxyTest {

    @Autowired
    private EntityManager entityManager;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder().name("name1").build();
        entityManager.persist(user);
        entityManager.clear(); //영속성 컨텍스트 초기화
    }

    @Test
    @DisplayName("프록시테스트")
    void test() {
        User reference = entityManager.getReference(User.class, user.getId()); //여기서는 조회가 되지 않는다. 디버깅 찍어봐야함
        System.out.println("getReference 호출이기 때문에, 여기서는 쿼리문이 로그에 찍히지 않을것으로 기대");
        boolean loaded = entityManager.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(reference);
        assertFalse(loaded);
        assertEquals(user.getName(), reference.getName());
        boolean loaded1 = entityManager.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(reference);
        System.out.println("user.getName()을 호출하기 때문에 쿼리실행");
        assertTrue(loaded1);
    }

    @Test
    @DisplayName("프록시테스트 - 프록시객체는 식별자 값을 보관하기 때문에 쿼리가 실행되지 않는다. (초기화 X)")
    void test2(){
        User reference = entityManager.getReference(User.class, user.getId());
        System.out.println(reference.getId());
        boolean loaded = entityManager.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(reference);
        assertFalse(loaded);
    }
}
