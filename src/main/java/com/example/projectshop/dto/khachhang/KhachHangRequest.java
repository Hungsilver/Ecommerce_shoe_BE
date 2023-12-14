package com.example.projectshop.dto.khachhang;

import com.example.projectshop.domain.DiaChi;
import com.example.projectshop.domain.GioHang;
import com.example.projectshop.domain.HoaDon;
import com.example.projectshop.domain.PhieuGiamGia;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class KhachHangRequest {

    private Integer id;

    private String hoTen;

    private String email;

    private String matKhau;

    private String soDienThoai;

    private String ngaySinh;

    private Integer trangThai;

}
