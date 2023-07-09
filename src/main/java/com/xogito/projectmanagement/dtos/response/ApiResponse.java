package com.xogito.projectmanagement.dtos.response;


import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ApiResponse {
    private String message;
    private int code;

}
