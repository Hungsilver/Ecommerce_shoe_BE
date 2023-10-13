package com.example.projectshop.dto.phieugiamgia;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PhieuGiamGiaRequest {

    private Integer id;

    private String ma;

    private String ten;

    private BigDecimal chietKhau;

    private Byte hinhThucGiamGia;

    private java.sql.Date thoiGianBatDau;

    private java.sql.Date thoiGianKetThuc;

    private String moTa;

    private Integer trangThai;

//    private Integer idKhachHang;
}
