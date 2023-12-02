package com.example.projectshop.service;

import com.example.projectshop.dto.anhsanpham.ExcelASP;

import java.util.List;

public interface IAnhSanPhamService {
    List<ExcelASP> importExcel(List<ExcelASP> excelASPS);
    List<ExcelASP> exportExcel();
    void delete(String id);
}
