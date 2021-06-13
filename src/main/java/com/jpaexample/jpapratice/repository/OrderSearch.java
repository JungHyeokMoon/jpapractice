package com.jpaexample.jpapratice.repository;

import com.jpaexample.jpapratice.domain.ch05.entity.Order;
import com.jpaexample.jpapratice.domain.ch05.enums.OrderStatus;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;

import static com.jpaexample.jpapratice.repository.OrderSpec.orderStatusEq;
import static com.jpaexample.jpapratice.repository.OrderSpec.userNameLike;
import static org.springframework.data.jpa.domain.Specification.where;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderSearch {
    private String userName;
    private OrderStatus orderStatus;

    public Specification<Order> toSpecification(){
        return where(userNameLike(userName))
                .and(orderStatusEq(orderStatus));
    }
}
