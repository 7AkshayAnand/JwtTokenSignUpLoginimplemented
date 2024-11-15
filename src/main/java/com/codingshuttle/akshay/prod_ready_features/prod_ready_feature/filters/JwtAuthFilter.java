package com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.filters;

import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.entities.User;
import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.services.JwtService;
import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private  final UserService userService;
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{
            final String requestTokenHeader=request.getHeader("Authorization");
            if(requestTokenHeader==null || !requestTokenHeader.startsWith("Bearer")){
                filterChain.doFilter(request,response);
                return;
            }

            String token=requestTokenHeader.split("Bearer")[1];


//here below validation of the incoming token is done and if token is valid then only userId is returned
            Long userId= jwtService.getUserIdFromToken(token);

            if(userId!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                User user=userService.getUserbyId(userId);
                UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(user,null,null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request,response);

        }catch(Exception e){
            handlerExceptionResolver.resolveException(request,response,null,e);
//            here exception occours at filter level but our global exception handler exception at controller level
//             so here we are delegating the exception from the filter to global exception handler
//            The resolveException() method makes sure that the exception is handled by the Spring context,
//            and since the global exception handler (@ControllerAdvice) is part of the Spring context, it will
//            take care of the exception.
        }


    }
}

//Working Steps
//step-1 gets the token form the incoming request
//step 2 validates the token gets the id of the user
//step 3 load the user from the database
//set spring security context
//step 4 Now go to the WebSecurityConfig and sets the jwtAuthFilter before UsernamepasswordFilterchain