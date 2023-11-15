package com.example.projectshop.dto.khachhang;

import com.example.projectshop.domain.DiaChi;
import com.example.projectshop.domain.GioHang;
import com.example.projectshop.domain.HoaDon;
import com.example.projectshop.domain.PhieuGiamGia;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KhachHangRespone {

    private Integer id;

    private String hoTen;

    private String email;

    private String matKhau;

    private String soDienThoai;

    private String ngaySinh;

    private Integer trangThai;

    private List<DiaChi> listDiaChi;

    private List<PhieuGiamGia> listPhieuGiamGia;

    private List<GioHang> listGioHang;

    private List<HoaDon> listHoaDon;
}
