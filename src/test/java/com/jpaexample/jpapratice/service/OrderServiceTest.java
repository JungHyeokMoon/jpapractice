package com.jpaexample.jpapratice.service;

import com.jpaexample.jpapratice.domain.ch05.entity.Order;
import com.jpaexample.jpapratice.domain.ch05.entity.User;
import com.jpaexample.jpapratice.domain.ch05.enums.OrderStatus;
import com.jpaexample.jpapratice.domain.ch06.Item;
import com.jpaexample.jpapratice.domain.ch07.Book;
import com.jpaexample.jpapratice.domain.ch09.Address;
import com.jpaexample.jpapratice.exception.BusinessException;
import com.jpaexample.jpapratice.exception.ErrorCode;
import com.jpaexample.jpapratice.repository.ItemRepository;
import com.jpaexample.jpapratice.repository.OrderRepository;
import com.jpaexample.jpapratice.repository.OrderSearch;
import com.jpaexample.jpapratice.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@DisplayName("OrderService - ")
class OrderServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    private User createUser(String name) {
        return userRepository.save(User.builder()
                .name(name)
                .address(Address.builder().city("city1").street("street1").zipcode("123-123").build())
                .build());
    }

    private Book createBook(String name, int price, int stockQuantity) {
        return itemRepository.save(Book.builder()
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .build());
    }

    @Nested
    @DisplayName("order ????????????")
    class OrderServiceTestOrder {

        @Nested
        @DisplayName("?????? ?????????")
        class OrderServiceTestOrderSuccess {

            @Test
            @DisplayName("- ????????? ?????? ???????????????.")
            void test() {
                //given
                User user = createUser("??????1");
                Item item = createBook("book1", 10000, 10);
                int orderCount = 2;

                //when
                Long orderId = orderService.order(user.getId(), item.getId(), orderCount);

                //then
                Order one = orderService.findOne(orderId);

                assertEquals(one.getOrderStatus(), OrderStatus.ORDER);
                assertEquals(1, one.getOrderItems().size());
                assertEquals(10000 * 2, one.getTotalPrice());
                assertEquals(8, item.getStockQuantity());

            }
        }

        @Nested
        @DisplayName("?????? ?????????")
        class OrderServiceTestOrderFail {

            @Test
            @DisplayName("????????? ??????????????? ???????????????.")
            void test() {
                User user = createUser("??????1");
                Item item = createBook("book", 10000, 10);

                int orderCount=11;

                BusinessException businessException = assertThrows(BusinessException.class, () -> orderService.order(user.getId(), item.getId(), orderCount),
                        ErrorCode.QUANTITY_LACK.getMessage());

                assertEquals(ErrorCode.QUANTITY_LACK.getMessage(),businessException.getErrorCode().getMessage());
            }
        }


    }
    @Nested
    @DisplayName("cancel ????????????")
    class OrderServiceTestCancel{

        @Nested
        @DisplayName("???????????????")
        class OrderServiceTestCancelSuccess{

            @Test
            @DisplayName("???????????? ?????? ???????????????.")
            void test(){
                User user = createUser("??????1");
                Item item = createBook("book", 10000, 10);
                int orderCount = 2;

                Long orderId = orderService.order(user.getId(), item.getId(), orderCount);
                orderService.cancelOrder(orderId);

                Order order = orderRepository.getById(orderId);
                assertEquals(OrderStatus.CANCEL,order.getOrderStatus());
                assertEquals(10,item.getStockQuantity());
            }
        }
    }

    @Nested
    @DisplayName("findOrders ????????????")
    class OrderServiceTestFindOrders{

        @Nested
        @DisplayName("???????????????")
        class OrderServiceTestFindOrdersSuccess{

            @Test
            @DisplayName("???????????? ?????? ???????????????.")
            void test(){
                User user = createUser("??????");
                Item item1 = createBook("book1", 10000, 10);
                Item item2 = createBook("book2", 10000, 10);
                Item item3 = createBook("book2", 10000, 10);

                int orderCount = 2;

                orderService.order(user.getId(), item1.getId(), orderCount);
                orderService.order(user.getId(), item2.getId(), orderCount);
                orderService.order(user.getId(), item3.getId(), orderCount);

                List<Order> orderList = orderService.findOrders(OrderSearch.builder().orderStatus(OrderStatus.ORDER).userName("??????").build());
                assertEquals(3,orderList.size());
            }
        }
    }
}