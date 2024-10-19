package com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.exeptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
