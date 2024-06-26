package com.example.projectshop.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "ma")
    private String ma;

    @Column(name = "soluong")
    private Integer soLuong;

    @Column(name = "giaban")
    private BigDecimal giaBan;

    @Column(name = "ngaytao")
    private Date ngayTao;

    @Column(name = "ngaycapnhat")
    private Date ngayCapNhat;

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

//    @JsonBackReference
//    @JsonIgnoreProperties
    @ManyToOne()
    @JoinColumn(name = "id_sanpham")
    private SanPham sanPham;

//    @JsonIgnore
//    @JsonManagedReference
    @JsonIgnoreProperties("chiTietSanPham")
    @OneToMany(mappedBy = "chiTietSanPham")
    private List<AnhSanPham> anhSanPhams = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "chiTietSanPham")
    private List<GioHangChiTiet> listGioHangChiTiet;

    @JsonIgnore
    @OneToMany(mappedBy = "chiTietSanPham")
    private List<HoaDonChiTiet> listHoaDonChiTiet;
}
