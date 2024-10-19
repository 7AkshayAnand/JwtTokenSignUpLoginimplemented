package com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.repositories;

import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    Optional<User> findByEmail(String email);
}
