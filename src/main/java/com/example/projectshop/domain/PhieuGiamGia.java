package com.example.projectshop.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

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
    private Date thoiGianBatDau;

    @Column(name = "thoigianketthuc")
    private Date thoiGianKetThuc;

    @Column(name = "mota")
    private String moTa;

    @Column(name = "trangthai")
    private Integer trangThai;

    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "id_khachhang", referencedColumnName = "id")
    private KhachHang khachHang;

    @OneToMany(mappedBy = "phieuGiamGia")
    private List<HoaDon> listHoaDon;


}
