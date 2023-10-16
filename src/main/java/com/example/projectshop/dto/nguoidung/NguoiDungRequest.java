package com.example.projectshop.dto.nguoidung;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NguoiDungRequest {
    private Integer id;

    private String hoTen;

    private String anh;


    private String email;


    private String matKhau;


    private String soDienThoai;


    private Byte gioiTinh;


    private String ngaySinh;


    private String diaChi;


    private Integer trangThai;
}
