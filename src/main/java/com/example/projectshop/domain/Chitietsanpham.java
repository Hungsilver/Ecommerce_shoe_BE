package com.example.projectshop.domain;


import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

}
