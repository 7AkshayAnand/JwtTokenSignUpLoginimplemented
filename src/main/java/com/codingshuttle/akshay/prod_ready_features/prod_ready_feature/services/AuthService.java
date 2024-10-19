package com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.services;

import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.dto.LoginDTO;
import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.entities.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String login(LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication=  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword()));


        User user=(User)authentication.getPrincipal();
        String token= jwtService.generateToken(user);

        Cookie cookie=new Cookie("token",token);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);


        return token;
    }
}
