package com.example.projectshop.service.impl;

import com.example.projectshop.domain.KichCo;
import com.example.projectshop.dto.kichco.ExcelKichCo;
import com.example.projectshop.repository.KichCoRepository;
import com.example.projectshop.service.IKichCoService;
import com.example.projectshop.utilities.utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KichCoServiceImpl implements IKichCoService {
    @Autowired
    private KichCoRepository kichCoRepository;

    private String p_chu = "\\d+";


    @Override
    public Page<KichCo> getAll(Pageable pageable) {
        return kichCoRepository.findAll(pageable);
    }

    @Override
    public KichCo findById(Integer id) {
        if (id != null) {
        return kichCoRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public KichCo findByName(String name) {
        Optional<KichCo> kichCo = kichCoRepository.findByName(name);
        if (kichCo.isPresent()) {
            return kichCo.get();
        }
        return null;
    }

    @Override
    public Page<KichCo> findAllByName(String size, Pageable pageable) {

        return kichCoRepository.findAllBySize("%" +size+"%",pageable);
    }

    @Override
    public List<ExcelKichCo> importExcel(List<ExcelKichCo> excelKichCos) {
        // tạo list để chứa các đối tượng bị lỗi
        List<ExcelKichCo> errorImports = new ArrayList<>();
        // sử dụng for để lặp các đối tượng được truyền vào từ file excel
        for (ExcelKichCo x : excelKichCos) {
            // kiểm nếu tên truyền vào null || space add vào errorImports
            if (x.getSize() == null || x.getSize().isBlank() || !x.getSize().matches(p_chu)) {
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

            // lấy ra đối tượng theo tên được truyền vào từ file excel
            KichCo kichCo = this.findByName(x.getSize());
            // kiểm tra nếu chưa có trong db thì thêm mới
            if (kichCo == null) {
                KichCo kichCo1 = KichCo.builder()
                        .id(null)
                        .size(Integer.valueOf(x.getSize()))
                        .trangThai(utility.getNumberByNameStatus(x.getTrangThai()))
                        .build();
                System.out.println("case3");
                kichCoRepository.save(kichCo1);
            } else {// nếu đã có thì cập nhật lại thông tin
                KichCo kichCo2 = KichCo.builder()
                        .id(kichCo.getId())
                        .size(Integer.valueOf(x.getSize()))
                        .trangThai(utility.getNumberByNameStatus(x.getTrangThai()))
                        .build();
                System.out.println("case4");
                kichCoRepository.save(kichCo2);
            }
        }
        return errorImports;
    }

    @Override
    public List<ExcelKichCo> exportExcel() {
        List<ExcelKichCo> excelKichCos = new ArrayList<>();
        Integer index = 1;
        for (KichCo x : kichCoRepository.findAll()) {// convert từ KichCo sang ExcelCLDG
            ExcelKichCo excelKichCo = ExcelKichCo.builder()
                    .stt(index++)
                    .size(String.valueOf(x.getSize()))
                    .trangThai(utility.trangThaiSanPham(x.getTrangThai()))
                    .build();
            excelKichCos.add(excelKichCo);
        }
        return excelKichCos;
    }

    @Override
    public KichCo create(KichCo kc) {
        return kichCoRepository.save(kc);
    }

    @Override
    public KichCo update(KichCo kc, Integer id) {
        kc.setId(id);
        return kichCoRepository.save(kc);
    }

    @Override
    public KichCo delete(Integer id) {
        Optional<KichCo> xx = kichCoRepository.findById(id);
        if (xx.isPresent()) {
            xx.get().setTrangThai(0);
            return kichCoRepository.save(xx.get());
        }
        return null;
    }
}
