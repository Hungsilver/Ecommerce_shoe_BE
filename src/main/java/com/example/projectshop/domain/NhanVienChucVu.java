package com.example.projectshop.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @ManyToOne()
    @JoinColumn(name = "id_nhanvien")
    private NhanVien nhanVien;

    @ManyToOne()
    @JoinColumn(name = "id_chucvu")
    private ChucVu chucVu;

}
