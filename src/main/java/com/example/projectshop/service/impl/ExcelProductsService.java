package com.example.projectshop.service.impl;


import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.SanPham;
import com.example.projectshop.domain.Xuatxu;
import com.example.projectshop.helper.ExcelExportProductsHelper;
import com.example.projectshop.helper.ExcelImportProductDetailHelper;
import com.example.projectshop.helper.ExcelImportProductHelper;
import com.example.projectshop.repository.DanhMucRepository;
import com.example.projectshop.repository.SanPhamRepository;
import com.example.projectshop.repository.ThuongHieuRepository;
import com.example.projectshop.repository.XuatXuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelProductsService {

    @Autowired
    private SanPhamRepository sanPhamRepository;
    @Autowired
    private ThuongHieuRepository thuongHieuRepository;
    @Autowired
    private XuatXuRepository xuatXuRepository;

    @Autowired
    private DanhMucRepository danhMucRepository;

    public ByteArrayInputStream loadProducts() {

        List<SanPham> sanPhams = sanPhamRepository.findAll();
        ByteArrayInputStream exportProduct = ExcelExportProductsHelper.SanPhamToEcexl(sanPhams);
        return exportProduct;
    }

    public void saveProductsToDatabase(MultipartFile file) {
        if (ExcelImportProductHelper.isValidExcelFile(file)) {
            try {

                List<SanPham> sanPhams = ExcelImportProductHelper.getProductsDataFromExcel(file.getInputStream(),
                        thuongHieuRepository,xuatXuRepository,danhMucRepository);
                this.sanPhamRepository.saveAll(sanPhams);
            } catch (IOException e) {
                throw new IllegalArgumentException("The file is not a valid excel file");
            }

        }
    }


}
