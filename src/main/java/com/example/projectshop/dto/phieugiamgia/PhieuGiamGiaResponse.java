package com.example.projectshop.dto.phieugiamgia;

import com.example.projectshop.domain.HoaDon;
import com.example.projectshop.domain.KhachHang;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class PhieuGiamGiaResponse {
    private Integer id;

    private String ma;

    private String ten;

    private BigDecimal chietKhau;

    private Byte hinhThucGiamGia;

    private java.sql.Date thoiGianBatDau;

    private java.sql.Date thoiGianKetThuc;

    private String moTa;

    private Integer trangThai;

    private KhachHang khachHang;

    private List<HoaDon> listHoaDon;
}
