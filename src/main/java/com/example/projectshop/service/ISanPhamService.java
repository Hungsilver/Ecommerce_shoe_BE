package com.example.projectshop.service;

import com.example.projectshop.domain.SanPham;
import com.example.projectshop.dto.chitietsanpham.ExportExcelCTSP;
import com.example.projectshop.dto.sanpham.ExportExcelSanPham;
import com.example.projectshop.dto.sanpham.ImportExcelSanPham;
import com.example.projectshop.dto.sanpham.SanPhamRequest;
import org.springframework.data.domain.Page;

import java.util.List;

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
            Integer status,
            String keyword,
            Boolean isSortAsc,
            Integer page,
            Integer pageSize
    );

    SanPham findById(Integer id);

    SanPham findByName(String name);

    List<ImportExcelSanPham> importExcel(List<ImportExcelSanPham> importExcelSanPhams);

    List<ExportExcelSanPham> exportExcel();

    SanPham create(SanPhamRequest sanPhamRequest);

    SanPham update(Integer id,SanPhamRequest sanPhamRequest);

    SanPham delete(Integer id);

}
