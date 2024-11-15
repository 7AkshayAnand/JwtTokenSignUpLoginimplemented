package com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.controllers;

import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.dto.LoginDTO;
import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.dto.LoginResponseDTO;
import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.dto.SignUpDTO;
import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.dto.UserDTO;
import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.services.AuthService;
import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)

@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private  final AuthService authService;
    @PostMapping("/signup")
    public UserDTO signUp(@RequestBody SignUpDTO signUpDTO){
        UserDTO  userDTO=userService.signUp(signUpDTO);
        System.out.println("request reached here");
        return userDTO;

    }
    @GetMapping(path="/{id}")
    public UserDTO getUserById(@PathVariable Long id){
        return userService.findById(id);
    }
// below works for normal usecase that is without refresh token
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
//        String token=authService.login(loginDTO,httpServletRequest,httpServletResponse);
//        return ResponseEntity.ok(token);
//    }


        @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
            LoginResponseDTO loginResponseDTO=authService.login(loginDTO);

            Cookie cookie=new Cookie("refreshToken",loginResponseDTO.getRefreshToken());
            cookie.setHttpOnly(true);
            cookie.setSecure(true
            );
            httpServletResponse.addCookie(cookie);
            return ResponseEntity.ok(loginResponseDTO);

    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refresh(HttpServletRequest request){
      String refreshToken=  Arrays.stream(request.getCookies()).filter(cookie->"refreshToken".equals(cookie.getName())).findFirst().map(cookie -> cookie.getValue()).orElseThrow(()->new AuthenticationServiceException("Refresh token now found inside the cookies"));


     LoginResponseDTO loginResponseDTO= authService.refresh(refreshToken);

     return ResponseEntity.ok(loginResponseDTO);
    }
}
