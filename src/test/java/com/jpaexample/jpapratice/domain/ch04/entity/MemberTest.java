package com.jpaexample.jpapratice.domain.ch04.entity;

import com.jpaexample.jpapratice.configuration.DataJpaTestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Import(DataJpaTestConfig.class)
class MemberTest {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Identity 전략 테스트 - 커밋이 아닌 persist에 sql 쿼리가 날라감")
    void test(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Member member = Member.builder().username("name1").age(27).build();
        entityManager.persist(member);
        transaction.commit();
    }

    @Test
    @DisplayName("테스트엔티티매니저 사용")
    void test2(){
        Member member = Member.builder().username("name2").age(26).build();
        Member persist = testEntityManager.persist(member); // 일반 entityManager와는 달리 persist의 반환형이 Entity클래스이다.

        Member member1 = testEntityManager.find(Member.class, persist.getId()); //1차 케쉬에 저장되어 있어서 select 문 날라가지않음
        assertTrue(persist==member1); //영속성 컨텍스트에 persist로 저장되있고, 그 id로 찾는것이기 때문에 주소의 값이 같을 것이다.
    }

    @Test
    @DisplayName("entityManager remove 후 영속성 컨텍스트에서 entity 지워지는지 확인")
    void test3(){
        Member member = Member.builder().username("name3").age(25).build();
        Member persist = testEntityManager.persist(member);

        testEntityManager.remove(persist);
        testEntityManager.flush();

        Member member1 = testEntityManager.find(Member.class, persist.getId()); //remove쿼리를 flush로 날린 후에 찾으면, db에도 없고 영속성컨텍스트에도 존재하지않으므로
        assertNull(member1); //null이나와야함
    }
}