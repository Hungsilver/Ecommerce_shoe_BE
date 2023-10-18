package com.example.projectshop.dto.khachhang;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KhachHangRespone {

    private Integer id;

    private String hoTen;

    private String email;

    private String matKhau;

    private String soDienThoai;

    private String ngaySinh;

    private Integer trangThai;
}
