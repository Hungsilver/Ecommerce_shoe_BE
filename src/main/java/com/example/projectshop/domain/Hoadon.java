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
@Table(name = "hoadon")
public class Hoadon {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "maHoaDon")
    private String maHoaDon;

    @Column(name = "tenKhachHang")
    private String tenKhachHang;

    @Column(name = "soDienThoai")
    private String soDienThoai;

    @Column(name = "diaChi")
    private String diaChi;

    @Column(name = "phuongXa")
    private String phuongXa;

    @Column(name = "quanHuyen")
    private String quanHuyen;

    @Column(name = "tinhThanh")
    private String tinhThanh;

    @Column(name = "ngayTao")
    private java.sql.Date ngayTao;

    @Column(name = "tongTien")
    private BigDecimal tongTien;

    @Column(name = "phiVanChuyen")
    private BigDecimal phiVanChuyen;

    @Column(name = "phuongThucThanhToan")
    private String phuongThucThanhToan;

    @Column(name = "trangThai")
    private Integer trangThai;

    @Column(name = "idPhieuGiamGia")
    private Integer idPhieuGiamGia;

    @Column(name = "idKhachHang")
    private Integer idKhachHang;

    @Column(name = "idNhanVien")
    private Integer idNhanVien;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaHoaDon() {
        return this.maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getTenKhachHang() {
        return this.tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getSoDienThoai() {
        return this.soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return this.diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getPhuongXa() {
        return this.phuongXa;
    }

    public void setPhuongXa(String phuongXa) {
        this.phuongXa = phuongXa;
    }

    public String getQuanHuyen() {
        return this.quanHuyen;
    }

    public void setQuanHuyen(String quanHuyen) {
        this.quanHuyen = quanHuyen;
    }

    public String getTinhThanh() {
        return this.tinhThanh;
    }

    public void setTinhThanh(String tinhThanh) {
        this.tinhThanh = tinhThanh;
    }

    public java.sql.Date getNgayTao() {
        return this.ngayTao;
    }

    public void setNgayTao(java.sql.Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public BigDecimal getTongTien() {
        return this.tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }

    public BigDecimal getPhiVanChuyen() {
        return this.phiVanChuyen;
    }

    public void setPhiVanChuyen(BigDecimal phiVanChuyen) {
        this.phiVanChuyen = phiVanChuyen;
    }

    public String getPhuongThucThanhToan() {
        return this.phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    public Integer getTrangThai() {
        return this.trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public Integer getIdPhieuGiamGia() {
        return this.idPhieuGiamGia;
    }

    public void setIdPhieuGiamGia(Integer idPhieuGiamGia) {
        this.idPhieuGiamGia = idPhieuGiamGia;
    }

    public Integer getIdKhachHang() {
        return this.idKhachHang;
    }

    public void setIdKhachHang(Integer idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public Integer getIdNhanVien() {
        return this.idNhanVien;
    }

    public void setIdNhanVien(Integer idNhanVien) {
        this.idNhanVien = idNhanVien;
    }
}
