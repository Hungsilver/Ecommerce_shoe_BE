package com.example.projectshop.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "diachi")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DiaChi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "diachi")
    private String diaChi;

    @Column(name = "phuongxa")
    private String phuongXa;

    @Column(name = "quanhuyen")
    private String quanHuyen;

    @Column(name = "tinhthanh")
    private String tinhThanh;

    @Column(name = "trangthai")
    private Integer trangThai;

    @ManyToOne
    @JoinColumn(name = "id_khachhang")
    private KhachHang idKhachHang;


}
