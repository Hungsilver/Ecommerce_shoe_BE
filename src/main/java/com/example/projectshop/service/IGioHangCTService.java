package com.example.projectshop.service;

import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.GioHang;
import com.example.projectshop.domain.GioHangChiTiet;
import com.example.projectshop.domain.KhachHang;
import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IGioHangCTService {

    Page<GioHangChiTiet> getall(Pageable pageable);

    GioHangChiTiet addSP(ChiTietSanPham chiTietSanPham,int soluong,Integer idgiohang);

    GioHangChiTiet onlineCart(KhachHang kh,Integer idctsp, int soluong);
    List<GioHangChiTiet> GetGioHangCTByIdGioHang(Integer id);

    GioHangChiTiet update(GioHangChiTiet gioHangChiTiet);

    void remove(KhachHang kh ,Integer id);
    boolean increase(KhachHang kh,Integer idctsp);
    boolean reduce(KhachHang kh,Integer idctsp);
}
