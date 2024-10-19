package com.codingshuttle.akshay.prod_ready_features.prod_ready_feature;


import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.entities.User;
import com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.services.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProdReadyFeatureApplicationTests {
	@Autowired
	private JwtService jwtService;
	@Test
	void contextLoads() {

//		User user=new User(4L,"anuj@gmail.com","1234");
//
//		String token= jwtService.generateToken(user);
//		System.out.println("the generated token is "+token);
//
//		Long id=jwtService.getUserIdFromToken(token);
//
//		System.out.println("the id is  "+id);


	}




}
