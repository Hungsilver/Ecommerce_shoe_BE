package com.example.projectshop.dto.nhanvien;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Field;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
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

        private String role;

}


