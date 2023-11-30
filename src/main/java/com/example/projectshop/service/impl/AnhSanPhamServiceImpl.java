package com.example.projectshop.service.impl;

import com.example.projectshop.domain.AnhSanPham;
import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.dto.anhsanpham.ExcelASP;
import com.example.projectshop.repository.AnhSanPhamRepository;
import com.example.projectshop.service.IAnhSanPhamService;
import com.example.projectshop.service.IChiTietSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnhSanPhamServiceImpl implements IAnhSanPhamService {

    @Autowired
    private AnhSanPhamRepository anhSanPhamRepo;

    @Autowired
    private IChiTietSanPhamService chiTietSanPhamService;


    @Override
    public List<ExcelASP> importExcel(List<ExcelASP> excelASPS) {
        List<ExcelASP> errorImports = new ArrayList<>();
        for (ExcelASP x : excelASPS) {
            ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findByMa(x.getMaSanPhamChiTiet());
            if (chiTietSanPham == null) {
                errorImports.add(x);
                System.out.println("case 1");
                continue;
            }
            AnhSanPham anhSanPham = AnhSanPham.builder()
                    .id(null)
                    .chiTietSanPham(chiTietSanPham)
                    .ten(x.getLinkAnh())
                    .build();
            anhSanPhamRepo.save(anhSanPham);
            System.out.println("case2");
        }
        return errorImports;
    }

    @Override
    public List<ExcelASP> exportExcel() {
        Integer index = 1;
        List<ExcelASP> excelASPS = new ArrayList<>();
        for (AnhSanPham x: anhSanPhamRepo.findAll()){
            ExcelASP excelASP = ExcelASP.builder()
                    .stt(index++)
                    .maSanPhamChiTiet(x.getChiTietSanPham().getMa())
                    .linkAnh(x.getTen())
                    .build();
            excelASPS.add(excelASP);
        }
        return excelASPS;
    }

    @Override
    public void delete(String id) {
        AnhSanPham anhSanPham = anhSanPhamRepo.findById(id).get();
        anhSanPhamRepo.delete(anhSanPham);
    }
}
