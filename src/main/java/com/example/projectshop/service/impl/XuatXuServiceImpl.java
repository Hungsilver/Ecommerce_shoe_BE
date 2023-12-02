package com.example.projectshop.service.impl;

import com.example.projectshop.domain.DanhMuc;
import com.example.projectshop.domain.Xuatxu;
import com.example.projectshop.dto.danhmuc.ExcelDanhMuc;
import com.example.projectshop.dto.xuatxu.ExcelXuatXu;
import com.example.projectshop.repository.XuatXuRepository;
import com.example.projectshop.utils.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class XuatXuServiceImpl {
    @Autowired
    private XuatXuRepository xuatXuRepository;

    public Page<Xuatxu> findAll(Pageable pageable) {
        return xuatXuRepository.findAll(pageable);
    }

    public Xuatxu findById(Integer id) {
        if (id != null) {
            return xuatXuRepository.findById(id).get();
        }
        return null;
    }

    public Xuatxu findByName(String name) {
        Optional<Xuatxu> xuatxu = xuatXuRepository.findByName(name);
        if (xuatxu.isPresent()) {
            return xuatxu.get();
        }
        return null;
    }

    public Page<Xuatxu> findAllByName(String name, Pageable pageable) {
        return xuatXuRepository.findAllByTen("%"+name+"%", pageable);
    }

    public List<ExcelXuatXu> importExcel(List<ExcelXuatXu> excelXuatXus) {
        // tạo list để chứa các đối tượng bị lỗi
        List<ExcelXuatXu> errorImports = new ArrayList<>();
        // sử dụng for để lặp các đối tượng được truyền vào từ file excel
        for (ExcelXuatXu x : excelXuatXus) {
            // lấy ra đối tượng theo tên được truyền vào từ file excel
            Xuatxu xuatxu = this.findByName(x.getTenXuatXu());

            // kiểm nếu tên truyền vào null || space add vào errorImports
            if (x.getTenXuatXu() == null || x.getTenXuatXu().isBlank()) {
                errorImports.add(x);
                System.out.println("case 1");
                continue;
            }

            // kiểm nếu trạng thái là null add vào errorImports
            if (utils.getNumberByNameStatus(x.getTrangThai()) == null) {
                errorImports.add(x);
                System.out.println("case 2");
                continue;
            }

            // kiểm tra nếu chưa có trong db thì thêm mới
            if (xuatxu == null) {
                Xuatxu xuatxu1 = Xuatxu.builder()
                        .id(null)
                        .ten(x.getTenXuatXu())
                        .trangThai(utils.getNumberByNameStatus(x.getTrangThai()))
                        .build();
                System.out.println("case3");
                xuatXuRepository.save(xuatxu1);
            } else {// nếu đã có thì cập nhật lại thông tin
                Xuatxu xuatxu2 = Xuatxu.builder()
                        .id(xuatxu.getId())
                        .ten(x.getTenXuatXu())
                        .trangThai(utils.getNumberByNameStatus(x.getTrangThai()))
                        .build();
                System.out.println("case3");
                xuatXuRepository.save(xuatxu2);
            }
        }
        return errorImports;
    }

    public List<ExcelXuatXu> exportExcel() {
        List<ExcelXuatXu> excelXuatXus = new ArrayList<>();
        Integer index = 1;
        for (Xuatxu x : xuatXuRepository.findAll()) {// convert từ XuatXu sang ExcelXuaXu
            ExcelXuatXu excelXuatXu = ExcelXuatXu.builder()
                    .stt(index++)
                    .tenXuatXu(x.getTen())
                    .trangThai(utils.trangThaiSanPham(x.getTrangThai()))
                    .build();
            excelXuatXus.add(excelXuatXu);
        }
        return excelXuatXus;
    }

    public Xuatxu create(Xuatxu xuatxu) {
        xuatxu.setId(null);
        return xuatXuRepository.save(xuatxu);
    }

    public Xuatxu update(Xuatxu xuatxu, Integer id) {
        xuatxu.setId(id);
        return xuatXuRepository.save(xuatxu);
    }

    public Xuatxu delete(Integer id) {
        Optional<Xuatxu> xx = xuatXuRepository.findById(id);
        if (xx.isPresent()) {
            xx.get().setTrangThai(0);
            return xuatXuRepository.save(xx.get());
        }
        return null;
    }
}
