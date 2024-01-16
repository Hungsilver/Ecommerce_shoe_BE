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

import java.util.Date;

@Entity
@Table(name = "ghichu")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GhiChu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ghichu")
    private String ghiChu;

    @Column(name = "ngaytao")
    private Date ngayTao;

    @Column(name = "trangthai")
    private Integer trangThai;

    @ManyToOne()
    @JoinColumn(name = "id_hoadon")
    private HoaDon hoaDon;

    @ManyToOne()
    @JoinColumn(name = "id_nhanvien", referencedColumnName = "id")
    private NhanVien nhanVien;

}
