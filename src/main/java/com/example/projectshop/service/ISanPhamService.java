package com.example.projectshop.service;

import com.example.projectshop.domain.SanPham;
import com.example.projectshop.dto.sanpham.SanPhamResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ISanPhamService {

    List<SanPham> findAll();

    Page<SanPham> getAllByParam(String priceMin,
                                String priceMax,
                                String thuongHieu,
                                String xuatXu,
                                String mauSac,
                                String chatLieuGiay,
                                String chatLieuDeGiay,
                                String pageParam,
                                String limitParam
    );

    SanPham getOne(Integer id);
}
