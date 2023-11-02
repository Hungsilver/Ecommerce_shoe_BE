package com.example.projectshop.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "khachhang")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class KhachHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "hoten")
    private String hoTen;

    @Column(name = "email")
    private String email;

    @Column(name = "matkhau")
    private String matKhau;

    @Column(name = "sodienthoai")
    private String soDienThoai;

    @Column(name = "ngaysinh")
    private String ngaySinh;

    @Column(name = "trangthai")
    private Integer trangThai;

    @JsonIgnore
//    @JsonManagedReference
    @OneToMany(mappedBy = "khachHang")
    private List<DiaChi> listDiaChi = new ArrayList<>();



    //    @JsonManagedReference
//    @OneToMany(mappedBy = "khachHang")
//    private List<GioHang> listGioHang = new ArrayList<>();
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "khachHang")
    private GioHang giohang;


    @JsonIgnore
//    @JsonManagedReference
    @OneToMany(mappedBy = "khachHang")
    private List<HoaDon> listHoaDon = new ArrayList<>();


}
