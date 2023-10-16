package com.example.projectshop.service;

import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IChiTietSanPhamService {

    List<ChiTietSanPhamResponse> findAll();

    Page<ChiTietSanPhamResponse> getAll(String pageParam,String limitParam);

    ChiTietSanPhamResponse getOne(Integer id);
}
