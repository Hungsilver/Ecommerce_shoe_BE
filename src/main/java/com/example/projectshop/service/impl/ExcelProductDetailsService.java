package com.example.projectshop.service.impl;


import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.helper.ExcelExportProductsDetailHelper;
import com.example.projectshop.helper.ExcelImportProductDetailHelper;
import com.example.projectshop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelProductDetailsService {
    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Autowired
    private MauSacRepository mauSacRepository;

    @Autowired
    private KichCoRepository kichCoRepository;
    @Autowired
    private ChatLieuGiayRepository chatLieuGiayRepository;

    @Autowired
    private ChatLieuDeGiayRepository chatLieuDeGiayRepository;

    @Autowired
    private  SanPhamRepository sanPhamRepository;

    // xuat tu db ra  file excel
//    public ByteArrayInputStream load() {
//
//        List<ChiTietSanPham> chiTietSanPhams = chiTietSanPhamRepository.findAll();
//
//        ByteArrayInputStream in = ExcelExportProductsDetailHelper.ChiTietSanPhamToEcexl(chiTietSanPhams);
//        return in;
//    }


    // nhap excel vao db
//    public void saveChiTietSanPhamsToDatabase(MultipartFile file) {
//        if (ExcelImportProductDetailHelper.isValidExcelFile(file)) {
//            try {
//
//                List<ChiTietSanPham> chiTietSanPhams = ExcelImportProductDetailHelper.getProductDetailDataFromExcel(file.getInputStream(), mauSacRepository,
//                        kichCoRepository, chatLieuGiayRepository, chatLieuDeGiayRepository,sanPhamRepository);
//                this.chiTietSanPhamRepository.saveAll(chiTietSanPhams);
//            } catch (IOException e) {
//                throw new IllegalArgumentException("The file is not a valid excel file");
//            }
//
//        }
//
//    }

}
