package com.example.projectshop.service;

import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamRequest;
import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IChiTietSanPhamService {

    Page<ChiTietSanPham> findAll(
            String priceMin,
            String priceMax,
            String color,
            String shoe_material,
            String shoe_sole_material,
            Integer page,
            Integer pageSize
    );

    Optional<ChiTietSanPham> findById(Integer id);

    ChiTietSanPham create(ChiTietSanPhamRequest chiTietSanPhamRequest);

    ChiTietSanPham update(Integer id,ChiTietSanPhamRequest chiTietSanPhamRequest);

    ChiTietSanPham delete(Integer id);

    Page<ChiTietSanPham> search(String keyword,Integer page,Integer pageSize);
}
