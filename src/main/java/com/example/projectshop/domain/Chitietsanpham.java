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
@Table(name = "chitietsanpham")
public class Chitietsanpham {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "ma")
    private String ma;

    @Column(name = "soLuong")
    private Integer soLuong;

    @Column(name = "giaBan")
    private BigDecimal giaBan;

    @Column(name = "ngayTao")
    private java.sql.Date ngayTao;

    @Column(name = "trangThai")
    private Integer trangThai;

    @Column(name = "idMauSac")
    private Integer idMauSac;

    @Column(name = "idKichCo")
    private Integer idKichCo;

    @Column(name = "idChatLieuGiay")
    private Integer idChatLieuGiay;

    @Column(name = "idChatLieuDeGiay")
    private Integer idChatLieuDeGiay;

    @Column(name = "idSanPham")
    private Integer idSanPham;

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

    public Integer getSoLuong() {
        return this.soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getGiaBan() {
        return this.giaBan;
    }

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    public java.sql.Date getNgayTao() {
        return this.ngayTao;
    }

    public void setNgayTao(java.sql.Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public Integer getTrangThai() {
        return this.trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public Integer getIdMauSac() {
        return this.idMauSac;
    }

    public void setIdMauSac(Integer idMauSac) {
        this.idMauSac = idMauSac;
    }

    public Integer getIdKichCo() {
        return this.idKichCo;
    }

    public void setIdKichCo(Integer idKichCo) {
        this.idKichCo = idKichCo;
    }

    public Integer getIdChatLieuGiay() {
        return this.idChatLieuGiay;
    }

    public void setIdChatLieuGiay(Integer idChatLieuGiay) {
        this.idChatLieuGiay = idChatLieuGiay;
    }

    public Integer getIdChatLieuDeGiay() {
        return this.idChatLieuDeGiay;
    }

    public void setIdChatLieuDeGiay(Integer idChatLieuDeGiay) {
        this.idChatLieuDeGiay = idChatLieuDeGiay;
    }

    public Integer getIdSanPham() {
        return this.idSanPham;
    }

    public void setIdSanPham(Integer idSanPham) {
        this.idSanPham = idSanPham;
    }
}
