package com.payten.credit.controller.credit;

import com.payten.credit.BaseIntegrationTest;
import com.payten.credit.controller.user.UserResponse;
import com.payten.credit.repository.credit.CreditEntity;
import com.payten.credit.repository.credit.CreditJpaRepository;
import com.payten.credit.repository.user.UserEntity;
import com.payten.credit.repository.user.UserJpaRepository;
import com.payten.credit.service.creditscore.FakeCreditService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CreditControllerTest extends BaseIntegrationTest {

    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    CreditJpaRepository creditJpaRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Test
    @Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_apply_credit_and_approve() {
        //given
        UserEntity user = new UserEntity();
        user.setName("TestName");
        user.setSurname("TestSurname");
        user.setIdentificationNo(13192796682L);
        user.setPhoneNo("5393369458");
        user.setMonthlyIncome(1500D);
        user.setCreditScore(500);
        user.setPassword(bCryptPasswordEncoder.encode("123123"));

        UserEntity savedUser = userJpaRepository.save(user);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION,BEARER_TOKEN);

        //when
        String url = "/credit-apply/" + savedUser.getId();
        ResponseEntity<CreditApplyResponse> response = testRestTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(httpHeaders), CreditApplyResponse.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).extracting("creditStatus","creditLimit")
                .containsExactly(CreditStatus.APPROVED,10000D);
    }

    @Test
    @Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_apply_credit_and_NOT_approve() {
        //given
        UserEntity user = new UserEntity();
        user.setName("TestName");
        user.setSurname("TestSurname");
        user.setIdentificationNo(13192796682L);
        user.setPhoneNo("5393369458");
        user.setMonthlyIncome(1500D);
        user.setCreditScore(400);
        user.setPassword(bCryptPasswordEncoder.encode("123123"));

        UserEntity savedUser = userJpaRepository.save(user);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION,BEARER_TOKEN);

        //when
        String url = "/credit-apply/" + savedUser.getId();
        ResponseEntity<CreditApplyResponse> response = testRestTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(httpHeaders), CreditApplyResponse.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).extracting("creditStatus","creditLimit")
                .containsExactly(CreditStatus.REJECTED,0D);
    }

    @Test
    @Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void creditResult() {
        //given
        UserEntity user = new UserEntity();
        user.setName("TestName");
        user.setSurname("TestSurname");
        user.setIdentificationNo(13192796682L);
        user.setPhoneNo("5393369458");
        user.setMonthlyIncome(1500D);
        user.setCreditScore(500);
        user.setPassword(bCryptPasswordEncoder.encode("123123"));

        UserEntity savedUser = userJpaRepository.save(user);

        CreditEntity credit = new CreditEntity();
        credit.setUser(savedUser);
        credit.setCreditLimit(10000D);

        CreditEntity savedCredit = creditJpaRepository.save(credit);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION,BEARER_TOKEN);

        //when
        String url = "/credit?identificationNo=" + savedUser.getIdentificationNo();
        ResponseEntity<CreditResultResponse> response = testRestTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(httpHeaders), CreditResultResponse.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting("status","creditLimit")
                .containsExactly(CreditStatus.APPROVED,savedCredit.getCreditLimit());


    }
}