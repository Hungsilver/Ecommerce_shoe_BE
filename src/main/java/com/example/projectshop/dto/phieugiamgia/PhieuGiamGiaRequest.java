package com.example.projectshop.dto.phieugiamgia;

import com.example.projectshop.domain.KhachHang;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
public class PhieuGiamGiaRequest {

    private Integer id;

    private String ma;

    private String ten;

    private BigDecimal chietKhau;

    private Byte hinhThucGiamGia;

    private Date thoiGianBatDau;

    private Date thoiGianKetThuc;

    private String moTa;

    private Integer trangThai;

    private KhachHang khachHang;
}
