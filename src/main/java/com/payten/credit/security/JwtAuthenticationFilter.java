package com.payten.credit.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payten.credit.controller.user.LoginRequest;
import com.payten.credit.exception.AuthenticationException;
import com.payten.credit.exception.ExceptionType;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res){
        try {
            LoginRequest request = new ObjectMapper().readValue(req.getInputStream(),LoginRequest.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getIdentificationNo(),request.getPassword(), new ArrayList<>()));
        }catch (IOException e){
            log.error("Invalid request");
            throw new AuthenticationException(ExceptionType.AUTHENTICATION_EXCEPTION);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth){
        String token = Jwts.builder()
                .setSubject(((User) auth.getPrincipal()).getUsername())
//                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstant.SECRET.getBytes())
                .compact();
        System.out.println(token);
        res.addHeader(SecurityConstant.HEADER_STRING,SecurityConstant.TOKEN_PREFIX+token);
    }
}
