package com.example.projectshop.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "danhmuc")
public class DanhMuc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "ten")
    private String ten;

    @Column(name = "trangthai")
    private Integer trangThai;

    @JsonIgnore
    @OneToMany(mappedBy = "danhmuc")
    private List<SanPham> sanPhams;

}
