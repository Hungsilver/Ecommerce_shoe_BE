package com.example.projectshop.dto.chatlieugiay;

import com.example.projectshop.domain.ChiTietSanPham;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatLieuGiayRequest {
    private Integer id;

//    @NotBlank(message = "*Vui lòng nhập thông tin!")
//    @Size(max = 5, message = "*Vui lòng không nhập quá 50 ký tự!")
    private String ten;

    private Integer trangThai;

}
