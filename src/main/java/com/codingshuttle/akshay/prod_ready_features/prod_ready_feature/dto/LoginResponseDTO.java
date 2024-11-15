package com.codingshuttle.akshay.prod_ready_features.prod_ready_feature.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private Long id;
    private String accesssToken;
    private String refreshToken;
}
