package com.jpaexample.jpapratice.repository;

import com.jpaexample.jpapratice.domain.ch05.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("select u from User u join fetch u.orders")
    List<User> findAllJoinFetch();

    Optional<User> findByName(String name);
}
