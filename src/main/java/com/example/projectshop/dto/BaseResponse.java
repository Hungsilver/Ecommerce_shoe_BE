package com.example.projectshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
// class dùng để ném các ngoại lệ tùy chỉnh
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseResponse <T>{
    private String code;

    private T data;

    private String message;
}
