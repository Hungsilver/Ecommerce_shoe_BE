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

    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "id_khachhang",referencedColumnName = "id")
    private KhachHang khachHang;



}
