package com.jpaexample.jpapratice.controller;

import com.jpaexample.jpapratice.service.ItemService;
import com.jpaexample.jpapratice.service.OrderService;
import com.jpaexample.jpapratice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final ItemService itemService;

    @PostMapping("/order")
    public ResponseEntity<Long> order(@RequestParam("userId") Long userId, @RequestParam("itemId") Long itemId,
                                @RequestParam("count") int count){
        Long order = orderService.order(userId, itemId, count);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
}
