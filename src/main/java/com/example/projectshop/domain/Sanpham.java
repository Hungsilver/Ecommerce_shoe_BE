package com.example.projectshop.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "sanpham")
public class Sanpham {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "ma")
    private String ma;

    @Column(name = "ten")
    private String ten;

    @Column(name = "anhChinh")
    private String anhChinh;

    @Column(name = "moTa")
    private String moTa;

    @Column(name = "trangThai")
    private Integer trangThai;

    //    @Column(name = "idThuongHieu")
    @ManyToOne
    @JoinColumn(name = "idThuongHieu")
    private Thuonghieu idThuongHieu;

    @ManyToOne
    @JoinColumn(name = "idAnhSanPham")
    private Anhsanpham idAnhsanpham;
//    @Column(name = "idXuatXu")
//    private Integer idXuatXu;

}
