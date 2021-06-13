package com.jpaexample.jpapratice.repository;

import com.jpaexample.jpapratice.domain.ch05.entity.Order;

import java.util.List;

public interface OrderRepositoryCustom {
    public List<Order> findAll(OrderSearch orderSearch);
}
