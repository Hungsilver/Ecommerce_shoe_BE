package com.example.projectshop.service;

import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamRequest;
import com.example.projectshop.dto.chitietsanpham.ExportExcelCTSP;
import com.example.projectshop.dto.chitietsanpham.ImportExcelCTSP;
import com.google.zxing.WriterException;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

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
            String size,
            String shoe_material,
            String shoe_sole_material,
            Integer product,
            String keyword,
            Boolean isSortAsc,
            String sortField,
            Integer page,
            Integer pageSize
    );

    ChiTietSanPham findById(Integer id);

    ChiTietSanPham getTop1ByPrice();

    ChiTietSanPham findByMa(String ma);

    List<ChiTietSanPham> findByIdSanPham(Integer idSanPham);

    List<ExportExcelCTSP> exportExcel();

    List<ImportExcelCTSP> importExcel(List<ImportExcelCTSP> importExcelCTSPS) throws IOException, WriterException;

    ChiTietSanPham create(ChiTietSanPhamRequest chiTietSanPhamRequest) throws IOException, WriterException;

    ChiTietSanPham update(Integer id, ChiTietSanPhamRequest chiTietSanPhamRequest);

    ChiTietSanPham delete(Integer id);

    ChiTietSanPham  fetchctspWithgiohangchitiet(Integer id);

}
