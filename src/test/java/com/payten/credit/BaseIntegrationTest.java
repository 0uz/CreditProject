package com.payten.credit;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseIntegrationTest {

    protected String BEARER_TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMzE5Mjc5NjY4MiIsImV4cCI6MTY0NTA5MzEyNn0.9xLGDLsivf4ALkt8woGah1vShJLPPZ1qLo_1s8Io-9EPj1l2tI9_AVJ3zOj0GkIrfmlZsYNd-5W_4eRD7thb0g";

    @LocalServerPort
    protected int serverPort;

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @Test
    void contextLoads(){

    }

}
