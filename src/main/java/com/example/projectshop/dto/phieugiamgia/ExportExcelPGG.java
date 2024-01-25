package com.example.projectshop.dto.phieugiamgia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExportExcelPGG {
    private Integer stt;

    private String maPhieuGiamGia;

    private String tieuDe;

    private BigDecimal chietKhau;

    private String hinhThucGiamGia;

    private Date thoiGianBatDau;

    private Date thoiGianKetThuc;

    private String moTa;

    private String trangThai;
}
