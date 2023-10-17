package com.example.projectshop.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class ChiTietSanPham {
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

    @ManyToOne
    @JoinColumn(name = "id_mausac")
    private MauSac mauSac;

    @ManyToOne
    @JoinColumn(name = "id_kichco")
    private KichCo kichCo;

    @ManyToOne
    @JoinColumn(name = "id_chatlieugiay")
    private ChatLieuGiay chatLieuGiay;

    @ManyToOne
    @JoinColumn(name = "id_chatlieudegiay")
    private ChatLieuDeGiay chatLieuDeGiay;

//    @JsonIgnore
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_sanpham")
    private SanPham sanPham;

//    @OneToMany(mappedBy = "chiTietSanPham")
//    private List<AnhSanPham> list;


}
