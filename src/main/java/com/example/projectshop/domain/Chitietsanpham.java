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
import java.sql.Date;

@Entity
@Table(name = "chitietsanpham")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Chitietsanpham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "soluong")
    private Integer soLuong;

    @Column(name = "giaban")
    private BigDecimal giaBan;

    @Column(name = "ngaytao")
    private Date ngayTao;

    @Column(name = "trangthai")
    private Integer trangThai;

//    @Column(name = "id_mausac")
//    private Integer idMauSac;
//
//    @Column(name = "id_kichco")
//    private Integer idKichCo;
//
//    @Column(name = "id_chatlieugiay")
//    private Integer idChatLieuGiay;
//
//    @Column(name = "id_chatlieudegiay")
//    private Integer idChatLieuDeGiay;
//
//    @Column(name = "id_sanpham")
//    private Integer idSanPham;


}
