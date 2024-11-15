package com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.advice;

import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.exeptions.ResourceNotFoundException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
@ExceptionHandler(ResourceNotFoundException.class)
public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException exception){

    ApiError apiError=new ApiError(exception.getMessage(), HttpStatus.NOT_FOUND);
    return new ResponseEntity<>(apiError,HttpStatus.NOT_FOUND);
}


//    @ExceptionHandler(BadCredentialsException.class)
//    public  ResponseEntity<ApiError> handleCredentialException(AuthenticationException e){
//        ApiError apiError=new ApiError(e.getLocalizedMessage(),HttpStatus.UNAUTHORIZED);
//        return new ResponseEntity<>(apiError,HttpStatus.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler(UsernameNotFoundException.class)
//    public ResponseEntity<ApiError> handleUsernameNotFoundException(UsernameNotFoundException e) {
//        ApiError apiError = new ApiError(e.getMessage(), HttpStatus.NOT_FOUND);
//        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
//    }

    @ExceptionHandler(AuthenticationException.class)
public  ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException e){
    ApiError apiError=new ApiError(e.getLocalizedMessage(),HttpStatus.UNAUTHORIZED);
    return new ResponseEntity<>(apiError,HttpStatus.UNAUTHORIZED);
}

    @ExceptionHandler(JwtException.class)
    public  ResponseEntity<ApiError> handleJwtException(JwtException e){
        ApiError apiError=new ApiError(e.getLocalizedMessage(),HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(apiError,HttpStatus.UNAUTHORIZED);
    }
}
