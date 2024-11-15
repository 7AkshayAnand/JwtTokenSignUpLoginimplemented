package com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.services;

import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.dto.LoginDTO;
import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.dto.LoginResponseDTO;
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
    private final UserService userService;

//    public String login(LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response) {
////        below returns the authentication object contains user details only if authentication is successsfull
//        Authentication authentication=  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword()));
//
////below we are getting the user and passing it to the generatetoken() for generating the token based o
////        the information present in user
//        User user=(User)authentication.getPrincipal();
//        String token= jwtService.generateAccessToken(user);
//
//        Cookie cookie=new Cookie("token",token);
//        cookie.setHttpOnly(true);
//        response.addCookie(cookie);
//
//
//        return token;
//    }


    public LoginResponseDTO login(LoginDTO loginDTO) {
//        below returns the authentication object contains user details only if authentication is successsfull
        Authentication authentication=  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword()));

//below we are getting the user and passing it to the generatetoken() for generating the token based o
//        the information present in user
        User user=(User)authentication.getPrincipal();
        System.out.println("user is "+user.getName());
        String accessToken= jwtService.generateAccessToken(user);
        String refreshToken=jwtService.generateRefreshToken(user);
        System.out.println("access  is "+accessToken);
        System.out.println("refresh  is "+refreshToken);
        return new LoginResponseDTO(user.getId(),accessToken,refreshToken);





    }

    public LoginResponseDTO refresh(String refreshToken) {
//        getuserIdFromToken() verifies the refresh token and if its valid the returs the userId
        Long userId=jwtService.getUserIdFromToken(refreshToken);
        User user=userService.getUserbyId(userId);
//        generating the new accessToken for the user
        String accessToken=jwtService.generateAccessToken(user);
        return new LoginResponseDTO(user.getId(),accessToken,refreshToken);
    }
}
