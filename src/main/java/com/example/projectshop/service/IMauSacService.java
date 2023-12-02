package com.example.projectshop.service;

import com.example.projectshop.domain.MauSac;
import com.example.projectshop.dto.danhmuc.ExcelDanhMuc;
import com.example.projectshop.dto.mausac.ExcelMauSac;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IMauSacService {
    Page<MauSac> getAll(Pageable pageable);

    MauSac findById(Integer id);

    MauSac findByName(String name);

    Page<MauSac> findAllByName(String name,Pageable pageable);

    List<ExcelMauSac> importExcel(List<ExcelMauSac> excelMauSacs);

    List<ExcelMauSac> exportExcel();

    MauSac create(MauSac mauSac);

    MauSac update(MauSac  mauSac , Integer id);

    MauSac delete(Integer id);


}
