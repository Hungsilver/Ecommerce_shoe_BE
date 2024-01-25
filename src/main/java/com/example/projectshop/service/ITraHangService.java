package com.example.projectshop.service;

import com.example.projectshop.domain.TraHang;
import com.example.projectshop.domain.TraHangChiTiet;
import com.example.projectshop.dto.trahang.TraHangChiTietRequest;
import com.example.projectshop.dto.trahang.TraHangRequest;
import com.example.projectshop.dto.trahang.UpdateCTSP;

import java.math.BigDecimal;
import java.util.List;

public interface ITraHangService {
    List<TraHang> findAll(Integer status,
                          String keyword,
                          String sortField,
                          Boolean isSortDesc,
                          Integer page,
                          Integer pageSize);

    List<TraHang> findAllByTrangThai(Integer trangThai);

    List<TraHang> findByIdHoaDon(Integer idHoaDon);

    TraHang findById(Integer id);

    TraHangChiTiet findByIdTHCT(Integer id);

    List<TraHang> findByIdKhachHang( );

    List<TraHang> findByMaHoaDon(String maHoaDon );

    TraHang add(TraHangRequest traHangRequest);

    TraHang shopAdd(TraHangRequest traHangRequest);

    void updateQuantity(List<UpdateCTSP> updateCTSPS);

    TraHangChiTiet deleteTHCT(Integer id);

    TraHangChiTiet updateQuantityTHCT(Integer id, Integer soLuong);

    TraHang updatePayment(Integer id, BigDecimal tongTien);

    TraHangChiTiet addTHCT(TraHangChiTietRequest traHangChiTietRequest);

    TraHang updateGhiChu(Integer id, String ghiChu);

    TraHang updateStatus(Integer id, Integer trangThai);
}
