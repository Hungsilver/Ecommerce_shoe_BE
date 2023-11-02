package com.example.projectshop.dto.hoadon;

import com.example.projectshop.domain.KhachHang;
import com.example.projectshop.domain.NhanVien;
import com.example.projectshop.domain.PhieuGiamGia;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class HoaDonRequest {
    private Integer id;
    private String tenKhachHang;
    private String soDienThoai;
    private String diaChi;
    private String phuongXa;
    private String quanHuyen;
    private String tinhThanh;
    private BigDecimal tongTien;
    private BigDecimal phiVanChuyen;
    private String phuongThucThanhToan;
    private PhieuGiamGia phieuGiamGiaRequest;
    private KhachHang khachHangRequest;
    private NhanVien nhanVien;
    private List<HoaDonChiTietRequest> hoaDonChiTietRequests;
}
