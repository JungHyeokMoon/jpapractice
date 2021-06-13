package com.jpaexample.jpapratice.repository;

import com.jpaexample.jpapratice.domain.ch05.entity.Order;
import com.jpaexample.jpapratice.domain.ch05.entity.User;
import com.jpaexample.jpapratice.domain.ch05.enums.OrderStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;

public class OrderSpec {
    public static Specification<Order> userNameLike(final String userName){
        return (root, query, builder) -> {
            if(!StringUtils.hasText(userName)) return null;

            Join<Order, User> m = root.join("user", JoinType.INNER);
            return builder.like(m.<String>get("name"), "%" + userName + "%");
        };
    }

    public static Specification<Order> orderStatusEq(final OrderStatus orderStatus){
        return (root,query,builder)->{
            if(orderStatus==null) return null;
            return builder.equal(root.get("status"), orderStatus);
        };
    }
}
