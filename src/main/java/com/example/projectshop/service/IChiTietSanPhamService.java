package com.example.projectshop.service;

import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.SanPham;
import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamRequest;
import com.google.zxing.WriterException;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.Optional;

public interface IChiTietSanPhamService {

    Page<ChiTietSanPham> findAll(
            Integer page,
            Integer pageSize,
            String sortField,
            Boolean isSortDesc
    );

    Page<ChiTietSanPham> filter(
            String priceMin,
            String priceMax,
            String color,
            String shoe_material,
            String shoe_sole_material,
            String keyword,
            Boolean isSortAsc,
            String sortField,
            Integer page,
            Integer pageSize
    );

    Optional<ChiTietSanPham> findById(Integer id);

    ChiTietSanPham create(ChiTietSanPhamRequest chiTietSanPhamRequest) throws IOException, WriterException;

    ChiTietSanPham update(Integer id, ChiTietSanPhamRequest chiTietSanPhamRequest);

    ChiTietSanPham delete(Integer id);

    ChiTietSanPham  fetchctspWithgiohangchitiet(Integer id);

    ChiTietSanPham findByMa(String ma);
}
