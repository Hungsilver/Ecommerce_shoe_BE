package com.example.projectshop.dto.nhanvien;


import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class NhanVienRequest {

    private Integer id;


    private String hoTen;


    private String anhDaiDien;


    private String email;


    private String matKhau;


    private String soDienThoai;


    private Byte gioiTinh;


    private String ngaySinh;


    private String diaChi;


    private Integer trangThai;

}
