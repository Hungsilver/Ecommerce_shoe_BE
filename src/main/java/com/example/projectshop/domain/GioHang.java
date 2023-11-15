package com.example.projectshop.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "giohang")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GioHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "id_khachhang",referencedColumnName = "id")
    private KhachHang khachHang;

    @JsonIgnore
    @OneToMany(mappedBy = "gioHang")
    private List<GioHangChiTiet> listGioHangChiTiet;


}
