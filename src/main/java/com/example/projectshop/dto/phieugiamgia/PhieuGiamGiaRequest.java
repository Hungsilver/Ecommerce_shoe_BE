package com.example.projectshop.dto.phieugiamgia;

import com.example.projectshop.domain.HoaDon;
import com.example.projectshop.domain.KhachHang;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PhieuGiamGiaRequest {

    private Integer id;

    private String ten;

    private BigDecimal chietKhau;

    private Boolean hinhThucGiamGia;

    @Temporal(TemporalType.TIMESTAMP)
    private Date thoiGianBatDau;

    @Temporal(TemporalType.TIMESTAMP)
    private Date thoiGianKetThuc;

    private String moTa;

    private Integer trangThai;


}
