package com.example.projectshop.dto.phieugiamgia;

import com.example.projectshop.domain.HoaDon;
import com.example.projectshop.domain.KhachHang;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class PhieuGiamGiaRequest {

    private Integer id;

    private String ten;

    private BigDecimal chietKhau;

    private Boolean hinhThucGiamGia;

    private Date thoiGianBatDau;

    private Date thoiGianKetThuc;

    private String moTa;

    private Integer trangThai;


}
