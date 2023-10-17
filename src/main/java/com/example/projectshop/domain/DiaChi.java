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

    @Column(name = "id_phuongxa")
    private Integer idPhuongXa;

    @Column(name = "id_quanhuyen")
    private Integer idQuanHuyen;

    @Column(name = "id_tinhthanh")
    private Integer idTinhThanh;

    @Column(name = "trangthai")
    private Integer trangThai;

    @Column(name = "id_khachhang")
    private Integer idKhachHang;


}
