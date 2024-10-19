package com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.repositories;

import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface PostRepository extends JpaRepository<PostEntity,Long> {
}
