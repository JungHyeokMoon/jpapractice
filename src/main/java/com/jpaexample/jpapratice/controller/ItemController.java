package com.jpaexample.jpapratice.controller;

import com.jpaexample.jpapratice.domain.ch06.Item;
import com.jpaexample.jpapratice.domain.ch07.Book;
import com.jpaexample.jpapratice.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/items/new")
    public void create(Book item){
        itemService.saveItem(item);
    }

    @GetMapping("/items")
    public ResponseEntity<List<Item>> list(){
        return new ResponseEntity<>(itemService.findItems(), HttpStatus.OK);
    }

    @PostMapping("/items/{itemId}/edit")
    public void updateItem(Book item){
        itemService.saveItem(item);
    }
}
