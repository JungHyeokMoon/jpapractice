package com.jpaexample.jpapratice.service;

import com.jpaexample.jpapratice.domain.ch05.entity.User;
import com.jpaexample.jpapratice.domain.ch09.Address;
import com.jpaexample.jpapratice.exception.BusinessException;
import com.jpaexample.jpapratice.exception.ErrorCode;
import com.jpaexample.jpapratice.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원가입 테스트")
    void test(){
        //given
        User user = User.builder().name("name1").currentPassword("password12@M").address(Address.builder().city("city1").street("street1").zipcode("zipcode1").build()).build();

        //when
        Long saveId = userService.join(user);

        assertEquals(user,userRepository.findById(saveId).get());
    }

    @Test
    @DisplayName("중복 회원가입 테스트")
    void test2(){
        User user = User.builder().name("name1").currentPassword("password12@M").address(Address.builder().city("city1").street("street1").zipcode("zipcode1").build()).build();
        userService.join(user);
        User user1 = User.builder().name("name1").currentPassword("password12!M").address(Address.builder().city("city1").street("street1").zipcode("zipcode1").build()).build();
        BusinessException businessException = assertThrows(BusinessException.class, () -> userService.join(user1), ErrorCode.DUPLICATE_USER.getMessage());
        assertEquals(businessException.getErrorCode().getMessage(),ErrorCode.DUPLICATE_USER.getMessage());
    }
}