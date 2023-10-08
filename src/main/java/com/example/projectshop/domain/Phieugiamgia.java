package com.example.projectshop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Phieugiamgia {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "ma")
    private String ma;

    @Column(name = "ten")
    private String ten;

    @Column(name = "chietKhau")
    private BigDecimal chietKhau;

    @Column(name = "hinhThucGiamGia")
    private Byte hinhThucGiamGia;

    @Column(name = "thoiGianBatDau")
    private java.sql.Date thoiGianBatDau;

    @Column(name = "thoiGianKetThuc")
    private java.sql.Date thoiGianKetThuc;

    @Column(name = "moTa")
    private String moTa;

    @Column(name = "trangThai")
    private Integer trangThai;

    @Column(name = "idKhachHang")
    private Integer idKhachHang;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMa() {
        return this.ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return this.ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public BigDecimal getChietKhau() {
        return this.chietKhau;
    }

    public void setChietKhau(BigDecimal chietKhau) {
        this.chietKhau = chietKhau;
    }

    public Byte getHinhThucGiamGia() {
        return this.hinhThucGiamGia;
    }

    public void setHinhThucGiamGia(Byte hinhThucGiamGia) {
        this.hinhThucGiamGia = hinhThucGiamGia;
    }

    public java.sql.Date getThoiGianBatDau() {
        return this.thoiGianBatDau;
    }

    public void setThoiGianBatDau(java.sql.Date thoiGianBatDau) {
        this.thoiGianBatDau = thoiGianBatDau;
    }

    public java.sql.Date getThoiGianKetThuc() {
        return this.thoiGianKetThuc;
    }

    public void setThoiGianKetThuc(java.sql.Date thoiGianKetThuc) {
        this.thoiGianKetThuc = thoiGianKetThuc;
    }

    public String getMoTa() {
        return this.moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Integer getTrangThai() {
        return this.trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public Integer getIdKhachHang() {
        return this.idKhachHang;
    }

    public void setIdKhachHang(Integer idKhachHang) {
        this.idKhachHang = idKhachHang;
    }
}
