package com.example.projectshop.service;

import com.example.projectshop.domain.HoaDon;
import com.example.projectshop.domain.PhieuGiamGia;
import com.example.projectshop.dto.phieugiamgia.ExportExcelPGG;
import com.example.projectshop.dto.phieugiamgia.ImportExcelPGG;
import com.example.projectshop.dto.phieugiamgia.PhieuGiamGiaRequest;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.List;

public interface IPhieuGiamGiaService {

    Page<PhieuGiamGia> findAll(
            Integer page,
            Integer pageSize,
            String sortField,
            Boolean isSortDesc,
            String keyword
    );

    PhieuGiamGia findById(Integer id);

    PhieuGiamGia findByCode(String  ma);

    PhieuGiamGia findByName(String name);

    List<ImportExcelPGG> importExcel(List<ImportExcelPGG> importExcelPGGS);

    List<ExportExcelPGG> exportExcel();

    PhieuGiamGia create(PhieuGiamGiaRequest PhieuGiamGiaRequest);

    PhieuGiamGia update(Integer id, PhieuGiamGiaRequest PhieuGiamGiaRequest);

    PhieuGiamGia delete(Integer id);

}
