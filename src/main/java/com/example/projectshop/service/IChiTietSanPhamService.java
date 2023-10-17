package com.example.projectshop.service;

import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamRequest;
import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IChiTietSanPhamService {

    List<ChiTietSanPhamResponse> findAll();

    Page<ChiTietSanPhamResponse> getAll(String pageParam,String limitParam);

    ChiTietSanPhamResponse getOne(Integer id);

    ChiTietSanPhamResponse create(ChiTietSanPhamRequest chiTietSanPhamRequest);

    ChiTietSanPhamResponse update(Integer id,ChiTietSanPhamRequest chiTietSanPhamRequest);

    void delete(Integer id);

    Page<ChiTietSanPhamResponse> timKiem (String timKiem,String pageParam,String limitParam);
}
