package com.example.projectshop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "phieugiamgia")
public class PhieuGiamGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ma")
    private String ma;

    @Column(name = "ten")
    private String ten;

    @Column(name = "chietkhau")
    private BigDecimal chietKhau;

    @Column(name = "hinhthucgiamgia")
    private Byte hinhThucGiamGia;

    @Column(name = "thoigianbatdau")
    private java.sql.Date thoiGianBatDau;

    @Column(name = "thoigianketthuc")
    private java.sql.Date thoiGianKetThuc;

    @Column(name = "mota")
    private String moTa;

    @Column(name = "trangthai")
    private Integer trangThai;

//    @Column(name = "idKhachHang")
//    private Integer idKhachHang;


}
