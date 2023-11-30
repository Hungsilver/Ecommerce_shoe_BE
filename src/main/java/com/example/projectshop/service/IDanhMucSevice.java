package com.example.projectshop.service;

import com.example.projectshop.domain.DanhMuc;
import com.example.projectshop.dto.chatlieudegiay.ExcelCLDG;
import com.example.projectshop.dto.danhmuc.DanhMucRequest;
import com.example.projectshop.dto.danhmuc.ExcelDanhMuc;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface IDanhMucSevice {
    Page<DanhMuc> findAll(
            Integer page,
            Integer pageSize,
            String sortField,
            Boolean isSortDesc,
            String keyword
    );

    DanhMuc findById(Integer id);

    DanhMuc findByName(String name);

    List<ExcelDanhMuc> importExcel(List<ExcelDanhMuc> excelDanhMucs);

    List<ExcelDanhMuc> exportExcel();

    DanhMuc create(DanhMucRequest danhMucRequest);

    DanhMuc update(Integer id, DanhMucRequest danhMucRequest);

    DanhMuc delete(Integer id);
}
