package com.xogito.projectmanagement.dtos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private String code;
    private String Message;
}
