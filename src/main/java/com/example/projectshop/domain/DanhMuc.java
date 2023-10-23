package com.example.projectshop.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.springframework.data.annotation.Id;

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
    @OneToMany(mappedBy = "danhMuc")
    private List<SanPham> sanPhams;

}
