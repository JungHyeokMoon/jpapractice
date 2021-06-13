package com.jpaexample.jpapratice.repository;

import com.jpaexample.jpapratice.domain.ch05.entity.Order;
import com.jpaexample.jpapratice.domain.ch05.entity.QOrder;
import com.jpaexample.jpapratice.domain.ch05.entity.QUser;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.jpaexample.jpapratice.domain.ch05.entity.QOrder.*;
import static com.jpaexample.jpapratice.domain.ch05.entity.QUser.*;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Order> findAll(OrderSearch orderSearch) {
        JPQLQuery query= queryFactory.selectFrom(order);

        if(StringUtils.hasText(orderSearch.getUserName())){
            query.leftJoin(order.user, user)
                    .where(user.name.contains(orderSearch.getUserName()));
        }

        if(orderSearch.getOrderStatus()!=null){
            query.where(order.orderStatus.eq(orderSearch.getOrderStatus()));
        }

        return query.fetch();
    }
}
