package com.example.projectshop.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "nhanvien")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class NhanVien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "hoten")
    private String hoTen;

    @Column(name = "anhdaidien")
    private String anhDaiDien;

    @Column(name = "email")
    private String email;

    @Column(name = "matkhau")
    private String matKhau;

    @Column(name = "sodienthoai")
    private String soDienThoai;

    @Column(name = "gioitinh")
    private Boolean gioiTinh;

    @Column(name = "ngaysinh")
    private String ngaySinh;

    @Column(name = "diachi")
    private String diaChi;

    @Column(name = "trangthai")
    private Integer trangThai;

    @JsonIgnore
    @OneToMany(mappedBy = "nhanVien")
    private List<GioiThieu> listGioiThieu;

    @JsonIgnore
    @OneToMany(mappedBy = "nhanVien")
    private List<HoaDon> listHoaDon;

//    @JsonIgnore
//    @JsonIgnoreProperties("nhanViens")
//    @ManyToMany(fetch = FetchType.LAZY)
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "nhanvienchucvu",
            joinColumns = @JoinColumn(name = "id_nhanvien"),
            inverseJoinColumns =@JoinColumn(name = "id_chucvu")
    )
    private Set<ChucVu> chucVus;

    @Override
    public String toString() {
        return "NhanVien{" +
                "id=" + id +
                ", hoTen='" + hoTen + '\'' +
                ", anhDaiDien='" + anhDaiDien + '\'' +
                ", email='" + email + '\'' +
                ", matKhau='" + matKhau + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", gioiTinh=" + gioiTinh +
                ", ngaySinh='" + ngaySinh + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", trangThai=" + trangThai +
                ", chucVus=" + chucVus +
//                ", listGioiThieu=" + listGioiThieu +
//                ", listHoaDon=" + listHoaDon +
                '}';
    }
}
