package com.example.projectshop.service;

import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.GioHang;
import com.example.projectshop.domain.GioHangChiTiet;
import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IGioHangCTService {

    Page<GioHangChiTiet> getall(Pageable pageable);

    GioHangChiTiet addSP(ChiTietSanPhamRequest chiTietSanPhamRequest,int soluong,Integer idgiohang);


    List<GioHangChiTiet> GetGioHangCTByIdGioHang(Integer id);

    GioHangChiTiet update(GioHangChiTiet gioHangChiTiet);

    void delete(Integer id);
}
