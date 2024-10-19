package com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.advice;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponse<T> {
//    @JsonFormat(pattern="hh-mm-ss dd-MM-yyyy")
    private LocalDateTime timeStamp;



    private T data;
    private ApiError error;

    public ApiResponse() {
        this.timeStamp = LocalDateTime.now();
    }

    public ApiResponse(T data) {
        this();
        this.data = data;
    }

    public ApiResponse(ApiError error) {
        this();
        this.error = error;
    }
}
//if data(Object ) is present then we should not set the error and it will passed as null and vice versa
//we are sendign meaningful api response that is either toh we will send proper data when data is present
//or we will send apierror object that contains error information
