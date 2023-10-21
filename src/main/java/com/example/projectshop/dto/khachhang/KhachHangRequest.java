package com.example.projectshop.dto.khachhang;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KhachHangRequest {

    private Integer id;

    private String hoTen;

    private String email;

    private String matKhau;

    private String soDienThoai;

    private String ngaySinh;

    private Integer trangThai;
}
