package com.example.projectshop.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

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
//    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "id_hoadon")
    private HoaDon hoaDon;

//    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "id_chitietsanpham")
    private ChiTietSanPham chiTietSanPham;

//    @JsonIgnoreProperties("hoaDonChiTiet")
    @JsonIgnore
    @OneToMany(mappedBy = "hoaDonChiTiet")
    private List<TraHangChiTiet> listTraHangChiTiet;
}
