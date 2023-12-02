package com.example.projectshop.service.impl;

import com.example.projectshop.domain.ChatLieuDeGiay;
import com.example.projectshop.domain.DanhMuc;
import com.example.projectshop.dto.chatlieudegiay.ExcelCLDG;
import com.example.projectshop.dto.danhmuc.DanhMucRequest;
import com.example.projectshop.dto.danhmuc.ExcelDanhMuc;
import com.example.projectshop.repository.DanhMucRepository;
import com.example.projectshop.service.IDanhMucSevice;
import com.example.projectshop.service.ObjectMapperUtils;
import com.example.projectshop.utils.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DanhMucServiceImpl implements IDanhMucSevice {
    @Autowired
    private DanhMucRepository danhMucRepo;

    @Override
    public Page<DanhMuc> findAll(Integer page,
                                 Integer pageSize,
                                 String sortField,
                                 Boolean isSortDesc,
                                 String keyword) {
        Sort sort = Sort.by(isSortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, pageSize, sort);
        if (keyword != null && !keyword.isEmpty()) {
            return danhMucRepo.findAllByName(keyword, pageable);
        } else {
            return danhMucRepo.findAll(pageable);
        }
    }

    @Override
    public DanhMuc findById(Integer id) {
        if (id != null) {
        return danhMucRepo.findById(id).get();
        }
        return null;
    }

    @Override
    public DanhMuc findByName(String name) {
        Optional<DanhMuc> danhMuc = danhMucRepo.findByName(name);
        if (danhMuc.isPresent()) {
            return danhMuc.get();
        }
        return null;
    }

    @Override
    public List<ExcelDanhMuc> importExcel(List<ExcelDanhMuc> excelDanhMucs) {
        // tạo list để chứa các đối tượng bị lỗi
        List<ExcelDanhMuc> errorImports = new ArrayList<>();
        // sử dụng for để lặp các đối tượng được truyền vào từ file excel
        for (ExcelDanhMuc x : excelDanhMucs) {
            // lấy ra đối tượng theo tên được truyền vào từ file excel
            DanhMuc danhMuc = this.findByName(x.getTenDanhMuc());

            // kiểm nếu tên truyền vào null || space add vào errorImports
            if (x.getTenDanhMuc() == null || x.getTenDanhMuc().isBlank()) {
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
            if (danhMuc == null) {
                DanhMuc danhMuc1 = DanhMuc.builder()
                        .id(null)
                        .ten(x.getTenDanhMuc())
                        .trangThai(utils.getNumberByNameStatus(x.getTrangThai()))
                        .build();
                System.out.println("case3");
                danhMucRepo.save(danhMuc1);
            } else {// nếu đã có thì cập nhật lại thông tin
                DanhMuc danhMuc2 = DanhMuc.builder()
                        .id(danhMuc.getId())
                        .ten(x.getTenDanhMuc())
                        .trangThai(utils.getNumberByNameStatus(x.getTrangThai()))
                        .build();
                System.out.println("case3");
                danhMucRepo.save(danhMuc2);
            }
        }
        return errorImports;
    }

    @Override
    public List<ExcelDanhMuc> exportExcel() {
        List<ExcelDanhMuc> excelDanhMucs = new ArrayList<>();
        Integer index = 1;
        for (DanhMuc x : danhMucRepo.findAll()) {// convert từ ChatLieuDeGiay sang ExcelCLDG
            ExcelDanhMuc excelDanhMuc = ExcelDanhMuc.builder()
                    .stt(index++)
                    .tenDanhMuc(x.getTen())
                    .trangThai(utils.trangThaiSanPham(x.getTrangThai()))
                    .build();
            excelDanhMucs.add(excelDanhMuc);
        }
        return excelDanhMucs;
    }

    @Override
    public DanhMuc create(DanhMucRequest danhMucRequest) {
        DanhMuc danhMuc = ObjectMapperUtils.map(danhMucRequest, DanhMuc.class);
        danhMuc.setId(null);
        return danhMucRepo.save(danhMuc);
    }

    @Override
    public DanhMuc update(Integer id, DanhMucRequest danhMucRequest) {
        DanhMuc danhMuc = ObjectMapperUtils.map(danhMucRequest, DanhMuc.class);
        danhMuc.setId(id);
        return danhMucRepo.save(danhMuc);
    }

    @Override
    public DanhMuc delete(Integer id) {
        Optional<DanhMuc> danhMuc = danhMucRepo.findById(id);
        if (danhMuc.isPresent()) {
            danhMuc.get().setTrangThai(1);
            return danhMucRepo.save(danhMuc.get());
        }
        return null;
    }

}
