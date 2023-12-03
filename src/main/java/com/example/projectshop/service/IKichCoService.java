package com.example.projectshop.service;

import com.example.projectshop.domain.KichCo;
import com.example.projectshop.dto.danhmuc.ExcelDanhMuc;
import com.example.projectshop.dto.kichco.ExcelKichCo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IKichCoService {
    Page<KichCo> getAll(Pageable pageable);

    KichCo findById(Integer id);

    KichCo findByName(String name);

    Page<KichCo> findAllByName(String size,Pageable pageable);

    List<ExcelKichCo> importExcel(List<ExcelKichCo> excelKichCos);

    List<ExcelKichCo> exportExcel();

    KichCo create(KichCo kc);

    KichCo update(KichCo  kc , Integer id);

    KichCo delete(Integer id);

}
