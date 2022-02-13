package com.payten.credit;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseIntegrationTest {

    protected String BEARER_TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMzE5Mjc5NjY4MiJ9.ogit1YZGgT616n6mOhjDPM-bpQwslLgM6JZ_IM4wYvaL0j4qeuhMOlAlMFSMZJLoSVb6x0MAGTvA8UIEd2cfOQ";

    @LocalServerPort
    protected int serverPort;

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @Test
    void contextLoads(){

    }

}
