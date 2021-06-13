package com.jpaexample.jpapratice.controller;

import com.jpaexample.jpapratice.exception.ErrorCode;
import com.jpaexample.jpapratice.exception.ErrorResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AopTestControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("ErrorResponse 테스트")
    void test1(){
        webTestClient.get()
                .uri("/hi2")
                .exchange()
                .expectBody(ErrorResponse.class)
                .consumeWith(errorResponseEntityExchangeResult -> {
                    assertEquals(errorResponseEntityExchangeResult.getResponseBody().getMessage(), ErrorCode.SERVER_ERROR.getMessage());
                });
    }
}