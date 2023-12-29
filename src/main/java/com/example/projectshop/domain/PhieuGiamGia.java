package com.example.projectshop.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "phieugiamgia")
public class PhieuGiamGia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ma")
    private String ma;

    @Column(name = "ten")
    private String ten;

    @Column(name = "chietkhau")
    private BigDecimal chietKhau;

    @Column(name = "hinhthucgiamgia")
    private Boolean hinhThucGiamGia;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "thoigianbatdau")
    private Date thoiGianBatDau;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "thoigianketthuc")
    private Date thoiGianKetThuc;

    @Column(name = "mota")
    private String moTa;

    @Column(name = "trangthai")
    private Integer trangThai;

// ba trang thai : sap dien ra, dang dien ra, het han


    @JsonIgnore
    @OneToMany(mappedBy = "phieuGiamGia")
    private List<HoaDon> listHoaDon;


}
