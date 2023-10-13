package com.example.projectshop.service;

import com.example.projectshop.dto.anhsanpham.AnhSanPhamRequest;
import com.example.projectshop.dto.anhsanpham.AnhSanPhamResponse;
import java.util.List;

public interface IAnhSanPhamService {
    List<AnhSanPhamResponse> getAll();

    AnhSanPhamResponse findById(Integer id);

    AnhSanPhamResponse create(AnhSanPhamRequest anhSanPhamRequest);

    AnhSanPhamResponse update(AnhSanPhamRequest anhSanPhamRequest, Integer id);

    void delete(Integer id);
}
