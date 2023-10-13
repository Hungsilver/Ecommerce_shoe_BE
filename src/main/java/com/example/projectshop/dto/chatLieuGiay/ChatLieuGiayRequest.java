package com.example.projectshop.dto.chatLieuGiay;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatLieuGiayRequest {
    private Integer id;

//    @NotBlank(message = "*Vui lòng nhập thông tin!")
//    @Size(max = 5, message = "*Vui lòng không nhập quá 50 ký tự!")
    private String ten;
}
