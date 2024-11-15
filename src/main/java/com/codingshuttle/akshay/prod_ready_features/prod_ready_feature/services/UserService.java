package com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.services;

import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.dto.LoginDTO;
import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.dto.SignUpDTO;
import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.dto.UserDTO;
import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.entities.User;
import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.exeptions.ResourceNotFoundException;
import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.repositories.UserRepository;
import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.config.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(()->new BadCredentialsException("user with email : "+username+" not found"));
    }

    public User getUserbyId(Long userId){
        return userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with given id "+userId+" not found"));
    }
    public UserDTO signUp(SignUpDTO signUpDTO) {
      Optional<User> user= userRepository.findByEmail(signUpDTO.getEmail());
      if(user.isPresent()){
          throw  new BadCredentialsException("User with email : "+signUpDTO.getEmail()+" already exists!!!");
      }

      User toBeCreatedUser=modelMapper.map(signUpDTO,User.class);

      toBeCreatedUser.setPassword(passwordEncoder.encode(toBeCreatedUser.getPassword()));

      User savedUser=userRepository.save(toBeCreatedUser);

      return modelMapper.map(savedUser,UserDTO.class);
    }

    public UserDTO findById(Long id) {
        return modelMapper.map(userRepository.findById(id).orElse(null),UserDTO.class);
    }


}
