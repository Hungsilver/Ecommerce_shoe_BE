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

    @Column(name = "dongia")
    private BigDecimal donGia;

    @Column(name = "soluong")
    private Integer soLuong;

//    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "id_hoadon")
    private HoaDon hoaDon;

//    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "id_chitietsanpham")
    private ChiTietSanPham chiTietSanPham;


}
