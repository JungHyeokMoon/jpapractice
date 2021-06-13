package com.jpaexample.jpapratice.controller;

import com.jpaexample.jpapratice.exception.BusinessException;
import com.jpaexample.jpapratice.exception.ErrorCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AopTestController {

    @GetMapping("/hi")
    public Map<String,Integer> hi(){
        Map<String, Integer> map = new HashMap<>();
        map.put("1", 1);
        return map;
    }

    @GetMapping("/hi2")
    public Map<String,Integer> hi2(){
        throw new BusinessException(ErrorCode.SERVER_ERROR);
    }
}
