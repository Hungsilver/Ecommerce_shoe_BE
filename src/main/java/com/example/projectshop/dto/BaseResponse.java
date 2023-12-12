package com.example.projectshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseResponse<T> {

    private Integer code;

    private T data;

    private Boolean isOK;

    private String message;

    public BaseResponse<T> createBaseResponse(Integer code, T data, Boolean isOK, String message) {
        return BaseResponse.<T>builder()
                .code(code)
                .data(data)
                .isOK(isOK)
                .message(message)
                .build();
    }
}
