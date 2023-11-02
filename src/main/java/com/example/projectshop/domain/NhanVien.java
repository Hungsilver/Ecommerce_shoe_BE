package com.example.projectshop.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
    private Byte gioiTinh;

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

    @JsonIgnore
//    @JsonIgnoreProperties("nhanViens")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "nhanvienchucvu",
            joinColumns = @JoinColumn(name = "id_nhanvien"),
            inverseJoinColumns =@JoinColumn(name = "id_chucvu")
    )
    private List<ChucVu> chucVus;

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
