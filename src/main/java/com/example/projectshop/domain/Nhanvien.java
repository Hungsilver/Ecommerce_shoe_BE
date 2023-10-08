package com.example.projectshop.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "nhanvien")
public class Nhanvien implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "hoten")
    private String hoTen;

    @Column(name = "anh")
    private String anh;

    @Column(name = "email")
    private String email;

    @Column(name = "matkhau")
    private String matKhau;

    @Column(name = "sodienthoai")
    private String soDienThoai;

    @Column(nullable = false,name = "gioitinh")
    private Boolean gioiTinh;

    @JsonFormat(pattern="dd-MM-yyyy")
    @Column(name = "ngaysinh")
    private LocalDate ngaySinh;

    @Column(name = "trangthai")
    private Integer trangThai;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "nhanvien_chucvu",
            joinColumns = @JoinColumn(name = "idNhanVien",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "idChucVu",referencedColumnName = "id")
    )
    private Set<Chucvu> chucVus = new HashSet<>();

}
