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

    @Column(name = "id_hoadon")
    private Integer idHoaDon;

    @Column(name = "id_chitietsanpham")
    private Integer idChiTietSanPham;

    @Column(name = "dongia")
    private BigDecimal donGia;

    @Column(name = "soluong")
    private Integer soLuong;


}
