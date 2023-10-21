package com.example.projectshop.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "hoadonchitiet")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class HoaDonChiTiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;


    @ManyToOne
    @JoinColumn (name = "id_hoadon")
    private HoaDon idHoaDon;

    @ManyToOne
    @JoinColumn (name = "id_chitietsanpham")
    private ChiTietSanPham idChiTietSanPham;

    @Column(name = "dongia")
    private BigDecimal donGia;

    @Column(name = "soluong")
    private Integer soLuong;


}
