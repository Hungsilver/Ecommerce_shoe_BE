package com.example.projectshop.service.impl;

import com.example.projectshop.domain.MauSac;
import com.example.projectshop.dto.mausac.ExcelMauSac;
import com.example.projectshop.repository.MauSacRepository;
import com.example.projectshop.service.IMauSacService;
import com.example.projectshop.utilities.utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MauSacSeviceImpl implements IMauSacService {

    @Autowired
    private MauSacRepository mauSacRepository;


    @Override
    public Page<MauSac> getAll(Pageable pageable) {
        return mauSacRepository.findAll(pageable);
    }

    @Override
    public MauSac findById(Integer id) {
        if (id != null) {
            return mauSacRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public MauSac findByName(String name) {
        Optional<MauSac> mauSac = mauSacRepository.findByName(name);
        if (mauSac.isPresent()) {
            return mauSac.get();
        }
        return null;
    }

    @Override
    public Page<MauSac> findAllByName(String name, Pageable pageable) {

        return mauSacRepository.findAllByTen("%" + name + "%", pageable);
    }

    @Override
    public List<ExcelMauSac> importExcel(List<ExcelMauSac> excelMauSacs) {
        // tạo list để chứa các đối tượng bị lỗi
        List<ExcelMauSac> errorImports = new ArrayList<>();
        // sử dụng for để lặp các đối tượng được truyền vào từ file excel
        for (ExcelMauSac x : excelMauSacs) {
            // lấy ra đối tượng theo tên được truyền vào từ file excel
            MauSac mauSac = this.findByName(x.getTenMauSac());

            // kiểm nếu tên truyền vào null || space add vào errorImports
            if (x.getTenMauSac() == null || x.getTenMauSac().isBlank()) {
                errorImports.add(x);
                System.out.println("case 1");
                continue;
            }

            // kiểm nếu trạng thái là null add vào errorImports
            if (utility.getNumberByNameStatus(x.getTrangThai()) == null) {
                errorImports.add(x);
                System.out.println("case 2");
                continue;
            }

            // kiểm tra nếu chưa có trong db thì thêm mới
            if (mauSac == null) {
                MauSac mauSac1 = MauSac.builder()
                        .id(null)
                        .ten(x.getTenMauSac())
                        .trangThai(utility.getNumberByNameStatus(x.getTrangThai()))
                        .build();
                System.out.println("case3");
                mauSacRepository.save(mauSac1);
            } else {// nếu đã có thì cập nhật lại thông tin
                MauSac mauSac2 = MauSac.builder()
                        .id(mauSac.getId())
                        .ten(x.getTenMauSac())
                        .trangThai(utility.getNumberByNameStatus(x.getTrangThai()))
                        .build();
                System.out.println("case3");
                mauSacRepository.save(mauSac2);
            }
        }
        return errorImports;
    }

    @Override
    public List<ExcelMauSac> exportExcel() {
        List<ExcelMauSac> excelMauSacs = new ArrayList<>();
        Integer index = 1;
        for (MauSac x : mauSacRepository.findAll()) {// convert từ MauSac sang ExcelMauSac
            ExcelMauSac excelMauSac = ExcelMauSac.builder()
                    .stt(index++)
                    .tenMauSac(x.getTen())
                    .trangThai(utility.trangThaiSanPham(x.getTrangThai()))
                    .build();
            excelMauSacs.add(excelMauSac);
        }
        return excelMauSacs;
    }

    @Override
    public MauSac create(MauSac mauSac) {
        mauSac.setId(null);
        MauSac ms = mauSacRepository.save(mauSac);
        return ms;
    }

    @Override
    public MauSac update(MauSac mauSac, Integer id) {
        mauSac.setId(id);
        return mauSacRepository.save(mauSac);
    }

    @Override
    public MauSac delete(Integer id) {
        Optional<MauSac> ms = mauSacRepository.findById(id);
        if(ms.isPresent()){
            ms.get().setTrangThai(0);
            return mauSacRepository.save(ms.get());
        }
        return null;
    }


}
