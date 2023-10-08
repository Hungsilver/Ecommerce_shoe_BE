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
@Table(name = "hoadonchitiet")
public class Hoadonchitiet {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "idHoaDon")
    private Integer idHoaDon;

    @Column(name = "idChiTietGiay")
    private Integer idChiTietGiay;

    @Column(name = "donGia")
    private BigDecimal donGia;

    @Column(name = "soLuong")
    private Integer soLuong;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdHoaDon() {
        return this.idHoaDon;
    }

    public void setIdHoaDon(Integer idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public Integer getIdChiTietGiay() {
        return this.idChiTietGiay;
    }

    public void setIdChiTietGiay(Integer idChiTietGiay) {
        this.idChiTietGiay = idChiTietGiay;
    }

    public BigDecimal getDonGia() {
        return this.donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }

    public Integer getSoLuong() {
        return this.soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }
}
