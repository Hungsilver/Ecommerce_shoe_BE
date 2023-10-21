package com.example.projectshop.domain;


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

import java.sql.Date;

@Entity
@Table(name = "gioithieu")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GioiThieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "tengioithieu")
    private String tenGioiThieu;

    @Column(name = "noidung")
    private String noiDung;

    @Column(name = "logo")
    private String logo;

    @Column(name = "banner")
    private String banner;

    @Column(name = "mata")
    private String moTa;

    @Column(name = "ngaytao")
    private Date ngayTao;

    @Column(name = "ngayxoa")
    private Date ngayXoa;

    @Column(name = "trangthai")
    private Integer trangThai;

    @ManyToOne()
    @JoinColumn(name = "id_nhanvien")
    private NhanVien nhanVien;


}
