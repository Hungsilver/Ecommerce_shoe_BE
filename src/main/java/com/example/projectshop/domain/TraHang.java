package com.example.projectshop.domain;

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
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "trahang")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TraHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "lydo")
    private String lyDo;

    @Column(name = "ngaytao")
    private Date ngayTao;

    @Column(name = "ngaycapnhat")
    private Date ngayCapNhat;

    @Column(name = "tientrakhach")
    private BigDecimal tienTraKhach;

    @Column(name = "trangthai")
    private Integer trangThai;

    @ManyToOne()
    @JoinColumn(name = "id_hoadon")
    private HoaDon hoaDon;

    @JsonIgnoreProperties("traHang")
//    @JsonIgnore
    @OneToMany(mappedBy = "traHang")
    private List<TraHangChiTiet> listTraHangChiTiet;
}
