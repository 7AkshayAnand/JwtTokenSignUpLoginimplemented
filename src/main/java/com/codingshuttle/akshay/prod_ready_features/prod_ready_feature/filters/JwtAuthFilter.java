package com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.filters;

import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.entities.User;
import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.services.JwtService;
import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private  final UserService userService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader=request.getHeader("Authorization");
        if(requestTokenHeader==null || !requestTokenHeader.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }

        String token=requestTokenHeader.split("Bearer")[1];



       Long userId= jwtService.getUserIdFromToken(token);

       if(userId!=null && SecurityContextHolder.getContext().getAuthentication()==null){
           User user=userService.getUserbyId(userId);
           UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(user,null,null);
           authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
           SecurityContextHolder.getContext().setAuthentication(authentication);
       }

       filterChain.doFilter(request,response);


    }
}

//Working Steps
//step-1 gets the token form the incoming request
//step 2 validates the token gets the id of the user
//step 3 load the user from the database
//set spring security context
//step 4 Now go to the WebSecurityConfig and sets the jwtAuthFilter before UsernamepasswordFilterchain