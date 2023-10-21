package com.example.projectshop.domain;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "hoadon")
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "mahoadon")
    private String maHoaDon;

    @Column(name = "tenkhachhang")
    private String tenKhachHang;

    @Column(name = "sodienthoai")
    private String soDienThoai;

    @Column(name = "diachi")
    private String diaChi;

    @Column(name = "phuongxa")
    private String phuongXa;

    @Column(name = "quanhuyen")
    private String quanHuyen;

    @Column(name = "tinhthanh")
    private String tinhThanh;

    @Column(name = "ngaytao")
    private Date ngayTao;

    @Column(name = "tongtien")
    private BigDecimal tongTien;

    @Column(name = "phivanchuyen")
    private BigDecimal phiVanChuyen;

    @Column(name = "phuongthucthanhtoan")
    private String phuongThucThanhToan;

    @Column(name = "trangthai")
    private Integer trangThai;

    @ManyToOne
    @JoinColumn(name = "id_nhanvien")
    private NhanVien nhanvien;
//    @Column(name = "id_phieugiamgia")
//    private Integer idPhieuGiamGia;
//
//    @Column(name = "id_nguoidung")
//    private Integer idNguoiDung;


}
