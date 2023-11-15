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

    public String checkProperties() throws IllegalAccessException {
        for (Field f : getClass().getDeclaredFields()) {
            if (f.get(this) == null) {
                String[] arr = f.toString().split("\\.");
                String t = arr[arr.length - 1];
                if (t.equalsIgnoreCase("hoTen")
                        || t.equalsIgnoreCase("anhDaiDien")
                        || t.equalsIgnoreCase("email")
                        || t.equalsIgnoreCase("matKhau")
                        || t.equalsIgnoreCase("soDienThoai")
                        || t.equalsIgnoreCase("gioiTinh")
                        || t.equalsIgnoreCase("ngaySinh")
                        || t.equalsIgnoreCase("diaChi")
                        || t.equalsIgnoreCase("trangThai")
                        || t.equalsIgnoreCase("role")
                ) {
                    return t;
                }
            }
        }
        return null;
    }
}


