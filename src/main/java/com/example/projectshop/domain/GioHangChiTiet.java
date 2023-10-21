package com.example.projectshop.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "giohangchitiet")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GioHangChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_giohang")
    private GioHang idGioHang;

    @ManyToOne
    @JoinColumn(name = "id_chitietsanpham")
    private ChiTietSanPham idChiTietSanPham;

    @Column(name = "soluong")
    private Integer soLuong;

    @Column(name = "giaban")
    private BigDecimal giaBan;


}
