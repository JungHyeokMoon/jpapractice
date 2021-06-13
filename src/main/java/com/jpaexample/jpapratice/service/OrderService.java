package com.jpaexample.jpapratice.service;

import com.jpaexample.jpapratice.domain.ch05.entity.Order;
import com.jpaexample.jpapratice.domain.ch05.entity.OrderItem;
import com.jpaexample.jpapratice.domain.ch05.entity.User;
import com.jpaexample.jpapratice.domain.ch06.Delivery;
import com.jpaexample.jpapratice.domain.ch06.Item;
import com.jpaexample.jpapratice.exception.BusinessException;
import com.jpaexample.jpapratice.exception.ErrorCode;
import com.jpaexample.jpapratice.repository.OrderRepository;
import com.jpaexample.jpapratice.repository.OrderSearch;
import com.jpaexample.jpapratice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final ItemService itemService;

    /** 주문 */
    public Long order(Long userId, Long itemId, int count){
        //엔티티 조회
        User user = userService.findOne(userId);
        Item item = itemService.findOne(itemId);

        //배송정보 생성
        Delivery delivery = Delivery.builder().address(user.getAddress()).build();

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문생성
        Order order = Order.createOrder(user, delivery, orderItem);

        orderRepository.save(order);
        return order.getId();
    }

    /** 주문 취소 */
    public void cancelOrder(Long orderId){
        Order order = findOne(orderId);
        order.cancel();
    }

    /** 주문 검색 */
    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAll(orderSearch);
    }

    public Order findOne(Long orderId){
        return orderRepository.findById(orderId).orElseThrow(() -> new BusinessException(ErrorCode.SELECT_EMPTY));
    }
}
