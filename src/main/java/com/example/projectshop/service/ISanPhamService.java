package com.example.projectshop.service;

import com.example.projectshop.domain.SanPham;
import com.example.projectshop.domain.ThuongHieu;
import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamRequest;
import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamResponse;
import com.example.projectshop.dto.sanpham.SanPhamRequest;
import com.example.projectshop.dto.sanpham.SanPhamResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ISanPhamService {


    Page<SanPham> findAll(
            Integer page,
            Integer pageSize,
            String sortField,
            Boolean isSortDesc
    );

    Page<SanPham> filter(
            String priceMin,
            String priceMax,
            String trademark,
            String origin,
            String color,
            String size,
            String shoe_material,
            String shoe_sole_materal,
            String keyword,
            Boolean isSortAsc,
            Integer page,
            Integer pageSize
    );

    Optional<SanPham> findById(Integer id);

    SanPham create(SanPhamRequest sanPhamRequest);

    SanPham update(Integer id,SanPhamRequest sanPhamRequest);

    SanPham delete(Integer id);

}
