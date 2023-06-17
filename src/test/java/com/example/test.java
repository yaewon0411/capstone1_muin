package com.example;

import capstone_demo.domain.Resident;
import capstone_demo.dto.TokenInfo;
//import capstone_demo.jwt.JwtTokenProvider;
import capstone_demo.service.ResidentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = SpringBootTest.class)
public class test {

//    @Mock
//    private Authentication authentication;
//
//    private JwtTokenProvider jwtTokenProvider;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//        jwtTokenProvider = new JwtTokenProvider("tlzmfltzjdkfaAej235skdf9dfskdjkDdhxksltygpdl12qhrhltvek");
//    }
//
//    @Test
//    public void testGenerateToken() {
//        // Mock user authentication
//        when(authentication.getName()).thenReturn("username");
//       // when(authentication.getAuthorities()).thenReturn(getAuthorities());
//
//        // Generate token
//        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
//
//        // Verify token properties
//        assertEquals("Bearer", tokenInfo.getGrantType());
//        System.out.println("tokenInfo.getAccessToken() = " + tokenInfo.getAccessToken());
//        System.out.println("tokenInfo.getRefreshToken() = " + tokenInfo.getRefreshToken());
//    }
//
//    private Collection<? extends GrantedAuthority> getAuthorities() {
//        // Return a collection of user authorities
//        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
//    }



}


