package com.example.projectshop.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "chucvu")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChucVu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "tenchucvu")
    private String tenChucVu;

    @Column(name = "trangthai")
    private Integer trangThai;

    @ManyToMany(mappedBy = "chucVus",fetch = FetchType.LAZY)
    private List<NhanVien> nhanViens;

    @Override
    public String toString() {
        return "ChucVu{" +
                "id=" + id +
                ", tenChucVu='" + tenChucVu + '\'' +
                ", trangThai=" + trangThai +
//                ", nhanViens=" + nhanViens +
                '}';
    }
}
