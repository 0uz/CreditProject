package com.payten.credit.security;

import com.payten.credit.repository.user.UserDao;
import com.payten.credit.service.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditUserDetailService implements UserDetailsService {
    private final UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String identificationNo) throws UsernameNotFoundException {
        User user = userDao.retrieveByIdentificationNo(Long.parseLong(identificationNo));
        return new org.springframework.security.core.userdetails.User(user.getIdentificationNo()+"",user.getPassword(), List.of());
    }
}
