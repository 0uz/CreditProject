package com.payten.credit.controller.user;

import com.payten.credit.BaseIntegrationTest;
import com.payten.credit.controller.common.ExceptionResponse;
import com.payten.credit.repository.user.UserEntity;
import com.payten.credit.repository.user.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserControllerTest extends BaseIntegrationTest {

    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Test
    @Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_create_user() {
        //given
        UserCreateRequest user = new UserCreateRequest();
        user.setName("TestName");
        user.setSurname("TestSurname");
        user.setIdentificationNo("13192796682");
        user.setPhoneNo("5393369458");
        user.setMonthlyIncome(1500D);
        user.setPassword("123123");
        //when
        ResponseEntity<UserCreateResponse> response = testRestTemplate.postForEntity("/users", user, UserCreateResponse.class);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();

        //validate
        Optional<UserEntity> retrieved = userJpaRepository.findById(response.getBody().getId());
        assertThat(retrieved).isPresent();
        assertThat(retrieved.get().getId()).isEqualTo(response.getBody().getId());
    }

    @Test
    @Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_get_user() {
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
        String url = "/users/" + savedUser.getId();
        ResponseEntity<UserResponse> response = testRestTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(httpHeaders) ,UserResponse.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).extracting("name","surname","identificationNo","phoneNo","monthlyIncome")
                .containsExactly("TestName","TestSurname",13192796682L,"5393369458",1500D);

    }

    @Test
    @Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_NOT_get_user() {
        //given
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION,BEARER_TOKEN);

        //when
        ResponseEntity<ExceptionResponse> response = testRestTemplate.exchange("/users/9898", HttpMethod.GET, new HttpEntity<>(httpHeaders), ExceptionResponse.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).extracting("code","message")
                .containsExactly(1001,"User didn't find");

    }


    @Test
    @Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_update_user() {
        //given
        UserEntity userEntity = new UserEntity();
        userEntity.setName("TestName");
        userEntity.setSurname("TestSurname");
        userEntity.setIdentificationNo(13192796682L);
        userEntity.setPhoneNo("5393369458");
        userEntity.setMonthlyIncome(1500D);
        userEntity.setCreditScore(500);
        String encodedPsw = bCryptPasswordEncoder.encode("123123");
        userEntity.setPassword(encodedPsw);

        UserEntity savedUser = userJpaRepository.save(userEntity);

        UserCreateRequest userRequest = new UserCreateRequest();
        userRequest.setName("TestChangedName");
        userRequest.setSurname("TestChangedSurname");
        userRequest.setIdentificationNo("13192796683");
        userRequest.setPhoneNo("5393369457");
        userRequest.setMonthlyIncome(1600D);
        userRequest.setPassword("1231234");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION,BEARER_TOKEN);

        //when
        String url = "/users/" + savedUser.getId();
        ResponseEntity<UserCreateResponse> response = testRestTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(userRequest, httpHeaders), UserCreateResponse.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(savedUser.getId());

        //validate
        Optional<UserEntity> retrieved = userJpaRepository.findById(response.getBody().getId());
        assertThat(retrieved).isPresent();
        assertThat(retrieved.get()).extracting("name","surname","identificationNo","phoneNo","monthlyIncome")
                .containsExactly("TestChangedName","TestChangedSurname",13192796683L,"5393369457",1600D);
    }

    @Test
    @Sql(scripts = "/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void should_delete_user(){
        //given
        UserEntity userEntity = new UserEntity();
        userEntity.setName("TestName");
        userEntity.setSurname("TestSurname");
        userEntity.setIdentificationNo(13192796682L);
        userEntity.setPhoneNo("5393369458");
        userEntity.setMonthlyIncome(1500D);
        userEntity.setCreditScore(500);
        String encodedPsw = bCryptPasswordEncoder.encode("123123");
        userEntity.setPassword(encodedPsw);
        UserEntity savedUser = userJpaRepository.save(userEntity);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION,BEARER_TOKEN);

        //when
        String url = "/users/" + savedUser.getId();
        ResponseEntity<Void> response = testRestTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(httpHeaders), Void.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        //validate
        assertThat(userJpaRepository.findById(savedUser.getId())).isEmpty();
    }
}