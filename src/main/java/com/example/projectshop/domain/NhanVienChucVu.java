package com.example.projectshop.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "nhanvienchucvu")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class NhanVienChucVu {
    @Id
    @Column(name = "id_nhanVien")
    private Integer idNhanVien;

    @Column(name = "id_ChucVu")
    private Integer idChucVu;

}
