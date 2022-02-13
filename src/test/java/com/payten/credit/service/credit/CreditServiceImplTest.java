package com.payten.credit.service.credit;

import com.payten.credit.controller.credit.CreditStatus;
import com.payten.credit.exception.DataNotFoundException;
import com.payten.credit.repository.credit.CreditDao;
import com.payten.credit.repository.credit.CreditEntity;
import com.payten.credit.repository.credit.redis.CreditCache;
import com.payten.credit.repository.user.UserDao;
import com.payten.credit.repository.user.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreditServiceImplTest {

    CreditService service;

    @Mock
    CreditDao creditDao;

    @Mock
    UserDao userDao;

    @Mock
    CreditCache creditCache;


    @BeforeEach
    void setUp() {
        service = new CreditServiceImpl(
                creditDao,
                userDao,
                creditCache);
    }

    @Test
    void should_approve_credit_when_user_exist() {
        //mock
        UserEntity mockUser = new UserEntity();
        mockUser.setId(1L);
        mockUser.setName("TestName");
        mockUser.setSurname("TestSurname");
        mockUser.setIdentificationNo(12345678900L);
        mockUser.setPhoneNo("5554443322");
        mockUser.setCreditScore(500);
        mockUser.setMonthlyIncome(1500D);
        mockUser.setPassword("1234");


        when(userDao.retrieve(1L)).thenReturn(Optional.of(mockUser));

        //when
        Credit createdCredit = service.applyCredit(1L);
        //then
        assertThat(createdCredit).isNotNull();
        assertThat(createdCredit.getCreditStatus()).isEqualTo(CreditStatus.APPROVED);
        assertThat(createdCredit.getCreditLimit()).isEqualTo(10000D);
    }

    @Test
    void should_reject_credit_when_user_exist() {
        //mock
        UserEntity mockUser = new UserEntity();
        mockUser.setId(1L);
        mockUser.setName("TestName");
        mockUser.setSurname("TestSurname");
        mockUser.setIdentificationNo(12345678900L);
        mockUser.setPhoneNo("5554443322");
        mockUser.setCreditScore(400);
        mockUser.setMonthlyIncome(1500D);
        mockUser.setPassword("1234");


        when(userDao.retrieve(1L)).thenReturn(Optional.of(mockUser));

        //when
        Credit createdCredit = service.applyCredit(1L);
        //then
        assertThat(createdCredit).isNotNull();
        assertThat(createdCredit.getCreditStatus()).isEqualTo(CreditStatus.REJECTED);
        assertThat(createdCredit.getCreditLimit()).isEqualTo(0D);
    }

    @Test
    void should_apply_credit_when_user_NOT_exist() {
        when(userDao.retrieve(1L)).thenReturn(Optional.empty());

        //when
        Throwable throwable = catchThrowable(() -> service.applyCredit(1L));
        //then
        assertThat(throwable).isNotNull();
        assertThat(throwable).isInstanceOf(DataNotFoundException.class);

    }

    @Test
    void should_retrieve_credit_result_when_cache_exist() {
        Credit credit = Credit.builder()
                .id(1L)
                .creditLimit(10000D)
                .creditStatus(CreditStatus.APPROVED).build();

        when(creditCache.retrieveCredit(anyLong())).thenReturn(Optional.ofNullable(credit));

        //when
        Credit creditResult = service.creditResult(anyLong());

        assertThat(creditResult).isNotNull();
        assertThat(creditResult.getCreditLimit()).isEqualTo(10000D);
        assertThat(creditResult.getCreditStatus()).isEqualTo(CreditStatus.APPROVED);

        verifyNoInteractions(creditDao);
        verifyNoMoreInteractions(creditCache);

    }


    @Test
    void should_retrieve_credit_result_when_cache_empty() {
        CreditEntity entity = new CreditEntity();
        entity.setId(1L);
        entity.setCreditLimit(10000D);
        entity.setUser(new UserEntity());

        when(creditCache.retrieveCredit(anyLong())).thenReturn(Optional.empty());
        when(creditDao.retrieveByIdentificationNo(anyLong())).thenReturn(Optional.of(entity));
        //when
        Credit creditResult = service.creditResult(anyLong());

        //then
        assertThat(creditResult).isNotNull();
        assertThat(creditResult.getCreditLimit()).isEqualTo(10000D);
        assertThat(creditResult.getCreditStatus()).isEqualTo(CreditStatus.APPROVED);
    }
}