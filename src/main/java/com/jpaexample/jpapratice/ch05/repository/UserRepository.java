package com.jpaexample.jpapratice.ch05.repository;

import com.jpaexample.jpapratice.ch05.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query("select u from User u join fetch u.orders")
    List<User> findAllJoinFetch();

}
