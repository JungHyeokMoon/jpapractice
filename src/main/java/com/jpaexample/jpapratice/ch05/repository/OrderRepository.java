package com.jpaexample.jpapratice.ch05.repository;

import com.jpaexample.jpapratice.ch05.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
