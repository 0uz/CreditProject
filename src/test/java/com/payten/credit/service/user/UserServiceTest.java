package com.payten.credit.service.user;

import com.payten.credit.exception.DataNotFoundException;
import com.payten.credit.repository.user.UserDao;
import com.payten.credit.repository.user.UserEntity;
import com.payten.credit.repository.user.redis.UserCache;
import com.payten.credit.service.creditscore.FakeCreditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    UserService userService;

    @Mock
    UserDao userDao;

    @Mock
    FakeCreditService fakeCreditService;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    UserCache userCache;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(
                userDao,
                fakeCreditService,
                bCryptPasswordEncoder,
                userCache);
    }

    @Test
    void should_create_user() {
        //mock
        User mockUser = User.builder()
                .Id(1L)
                .name("TestName")
                .surname("TestSurname")
                .identificationNo(12345678900L)
                .phoneNo("5554443322")
                .creditScore(500)
                .monthlyIncome(1500D)
                .password("1234")
                .build();

        when(userDao.isIdentificationExist(mockUser.getIdentificationNo())).thenReturn(false);
        when(fakeCreditService.getCreditScore(mockUser)).thenReturn(500);
        when(bCryptPasswordEncoder.encode(mockUser.getPassword())).thenReturn("1234");
        when(userDao.create(any())).thenReturn(1L);

        //when
        Long createdUser = userService.create(mockUser);
        //then
        assertThat(createdUser).isNotNull();
        assertThat(createdUser).isEqualTo(mockUser.getId());


    }

    @Test
    void should_retrieve_user_when_cache_is_empty() {
        //mock
        when(userCache.retrieveUser(any())).thenReturn(Optional.empty());

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
        User retrievedUser = userService.retrieve(1L);

        //then
        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser).extracting("Id","name","surname","identificationNo","phoneNo","monthlyIncome","creditScore","password")
                .containsExactly(1L,"TestName","TestSurname",12345678900L,"5554443322",1500D,500,"1234");
    }

    @Test
    void should_retrieve_user_when_cache_is_exist() {
        //mock
        User mockUser = User.builder()
                .Id(1L)
                .name("TestName")
                .surname("TestSurname")
                .identificationNo(12345678900L)
                .phoneNo("5554443322")
                .creditScore(500)
                .monthlyIncome(1500D)
                .password("1234")
                .build();

        when(userCache.retrieveUser(1L)).thenReturn(Optional.ofNullable(mockUser));

        //when
        User retrievedUser = userService.retrieve(1L);

        //then
        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser).extracting("id","name","surname","identificationNo","phoneNo","monthlyIncome","creditScore","password")
                .containsExactly(1L,"TestName","TestSurname",12345678900L,"5554443322",1500D,500,"1234");

        verifyNoInteractions(userDao);
        verifyNoMoreInteractions(userCache);
    }

    @Test
    void should_delete_user() {
        userService.delete(1L);
        verify(userDao, times(1)).delete(1L);
    }

    @Test
    void should_update_user_when_user_exist() {
        //mock
        User mockUser = User.builder()
                .Id(1L)
                .name("TestName")
                .surname("TestSurname")
                .identificationNo(12345678900L)
                .phoneNo("5554443322")
                .creditScore(500)
                .monthlyIncome(1500D)
                .password("1234")
                .build();

        UserEntity entity = mockUser.convertToUserEntity();

        when(userDao.retrieve(1L)).thenReturn(Optional.ofNullable(entity));
        when(bCryptPasswordEncoder.encode("1234")).thenReturn("1234");
        when(userDao.create(any())).thenReturn(1L);
        //when
        Long updatedUser = userService.update(mockUser, 1L);
        //then
        assertThat(updatedUser).isEqualTo(mockUser.getId());
    }

    @Test
    void should_update_user_when_user_NOT_exist() {
        //mock
        User mockUser = User.builder()
                .Id(1L)
                .name("TestName")
                .surname("TestSurname")
                .identificationNo(12345678900L)
                .phoneNo("5554443322")
                .creditScore(500)
                .monthlyIncome(1500D)
                .password("1234")
                .build();

        when(userDao.retrieve(1L)).thenReturn(Optional.empty());
        //when
        Throwable throwable = catchThrowable(() -> userService.update(mockUser, 1L));
        //then
        assertThat(throwable).isNotNull()
                .isInstanceOf(DataNotFoundException.class)
                .hasMessage("User didn't find");

    }
}