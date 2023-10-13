package com.example.projectshop.domain;


import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "giohangchitiet")
public class Giohangchitiet {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "idGioHang")
    private Integer idGioHang;

    @Column(name = "idChiTietSanPham")
    private Integer idChiTietSanPham;

    @Column(name = "soLuong")
    private Integer soLuong;

    @Column(name = "giaBan")
    private BigDecimal giaBan;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdGioHang() {
        return this.idGioHang;
    }

    public void setIdGioHang(Integer idGioHang) {
        this.idGioHang = idGioHang;
    }

    public Integer getIdChiTietSanPham() {
        return this.idChiTietSanPham;
    }

    public void setIdChiTietSanPham(Integer idChiTietSanPham) {
        this.idChiTietSanPham = idChiTietSanPham;
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
}
