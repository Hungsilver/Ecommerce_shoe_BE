package com.example.projectshop.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

@Entity
@Table(name = "hoadon")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
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

    @Column(name = "ngaycapnhat")
    private Date ngayCapNhat;

    @Column(name = "tongtien")
    private BigDecimal tongTien;

    @Column(name = "tiengiam")
    private BigDecimal tienGiam;

    @Column(name = "tongtiensaugiam")
    private BigDecimal tongTienSauGiam;

    @Column(name = "phivanchuyen")
    private BigDecimal phiVanChuyen;

    @Column(name = "phuongthucthanhtoan")
    private String phuongThucThanhToan;

    @Column(name = "trangthai")
    private Integer trangThai;

    @ManyToOne
    @JoinColumn(name = "id_phieugiamgia")
    private PhieuGiamGia phieuGiamGia;

    //    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "id_khachhang", referencedColumnName = "id")
    private KhachHang khachHang;

    @ManyToOne()
    @JoinColumn(name = "id_nhanvien", referencedColumnName = "id")
    private NhanVien nhanVien;

//    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy = "hoaDon")
    private List<HoaDonChiTiet> listHoaDonChiTiet;


}
