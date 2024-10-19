package com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.*;

@Configuration
@EnableWebSecurity

public class WebSecurityConfig {

//    below we are defining our customized filter chain
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{


//        httpSecurity
//                .authorizeHttpRequests(auth ->
//                        auth.requestMatchers("/posts","/auth/signup").permitAll() // /posts will not be authenticated
//                                .requestMatchers("/post/**").hasAnyRole("ADMIN")// Requires ADMIN role for /post/{id} and sub-paths
//                                .anyRequest().authenticated())
//                                .csrf(csrfConfig-> {
//                                    try {
//                                        csrfConfig.disable()//here we are not using csrf token
//                        .sessionManagement(sessionconfig->sessionconfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));// now sessionId will not be stored
//
//                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
//                                    }
//                                });
       httpSecurity.authorizeHttpRequests(auth->
              auth.requestMatchers("/auth/**","/posts").permitAll()
                      .requestMatchers("/posts/**").hasAnyRole("ADMIN")
                      .anyRequest().authenticated()).csrf(csrfConfig->csrfConfig.disable())
               .sessionManagement(sessionconfig->sessionconfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


//        httpSecurity.authorizeHttpRequests(auth->
//                        auth
//                                .anyRequest().permitAll()).csrf(csrfConfig->csrfConfig.disable())
//                .sessionManagement(sessionconfig->sessionconfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));







        return httpSecurity.build();
    }
//here when the DaoAuthenticatorProvoder asks the UserDetailsService to load the user then we are
//    instead of going to database to check the user we are setting InMemoryUserDetailsManager to provide UserDetailsService
//    @Bean
//    UserDetailsService myInMemoryUserDetailsService(){
//        UserDetails normalUser= User.withUsername("anuj").password(passwordEncoder().encode("Anuj123")).roles("USER").build();
//
//        UserDetails adminUser=User.withUsername("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").build();
//
//      return new InMemoryUserDetailsManager(normalUser,adminUser);
//    }
//
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

}
