package com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.config;

import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.filters.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity

//•  @EnableWebSecurity is essential for enabling Spring Security in your web application and for allowing you to define custom security configurations (such as custom filters, authentication, authorization rules, etc.).
//        •  Without this annotation, your security configuration, including things like authentication and custom filters, won't be applied.

@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
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
//                      .requestMatchers("/posts/**").hasAnyRole("ADMIN")
                      .anyRequest().authenticated()).csrf(csrfConfig->csrfConfig.disable())
               .sessionManagement(sessionconfig->sessionconfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);


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



//    we need this below bean while performing the login see login() of AuthService
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

}
