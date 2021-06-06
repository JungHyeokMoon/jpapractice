package com.jpaexample.jpapratice.ch10;

import com.jpaexample.jpapratice.domain.ch06.Item;
import com.jpaexample.jpapratice.domain.ch07.Album;
import com.jpaexample.jpapratice.domain.ch07.Book;
import com.jpaexample.jpapratice.domain.ch07.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
public class PolymorphismTest {

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp(){
        Movie movie = Movie.builder().actor("actor1").director("director1").name("name1").price(1000).stockQuantity(2).build();
        Album album = Album.builder().artist("artist1").etc("etc1").name("name2").price(2000).stockQuantity(3).build();
        Book book = Book.builder().author("author1").isbn("isbn1").name("name3").price(3000).stockQuantity(4).build();

        entityManager.persist(movie);
        entityManager.persist(album);
        entityManager.persist(book);

        entityManager.clear();
    }

    @Test
    @DisplayName("다형성 테스트 - 자식클래스에 ToString이 재정의 되어있기 때문에 자식클래스의 ToString 출력")
    void test(){
        String jpql = "SELECT i FROM Item i";
        List<Item> resultList = entityManager.createQuery(jpql, Item.class).getResultList();
        assertEquals(3,resultList.size());
        resultList.forEach(System.out::println);
    }

    @Test
    @DisplayName("Type 쿼리 - 다운캐스팅")
    void test2(){
        String jpql = "SELECT i FROM Item i WHERE TYPE(i) IN (Book)";
        List<Item> resultList = entityManager.createQuery(jpql, Item.class).getResultList();
        assertEquals(1,resultList.size());
        Book book = (Book) resultList.get(0);
        assertNotNull(book);
    }
}
