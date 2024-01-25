package com.example.projectshop.service.impl;

import com.example.projectshop.domain.ThuongHieu;
import com.example.projectshop.dto.thuonghieu.ExcelThuongHieu;
import com.example.projectshop.dto.thuonghieu.ThuongHieuRequest;
import com.example.projectshop.repository.ThuongHieuRepository;
import com.example.projectshop.service.IThuongHieuService;
import com.example.projectshop.service.ObjectMapperUtils;
import com.example.projectshop.utilities.utility;
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
public class ThuongHieuServiceImpl implements IThuongHieuService {

    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    @Override
    public Page<ThuongHieu> findAll(Integer page,
                                    Integer pageSize,
                                    String sortField,
                                    Boolean isSortDesc,
                                    String keyword) {
        Sort sort = Sort.by(isSortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, pageSize, sort);
        if (keyword != null && !keyword.isEmpty()) {
            return thuongHieuRepository.findAllByName(keyword, pageable);
        } else {
            return thuongHieuRepository.findAll(pageable);
        }
    }

    @Override
    public ThuongHieu findById(Integer id) {
        if (id != null) {
            return thuongHieuRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public ThuongHieu findByName(String name) {
        Optional<ThuongHieu> thuongHieu  = thuongHieuRepository.findByName(name);
        if (thuongHieu.isPresent()) {
            return thuongHieu.get();
        }
        return null;
    }

    @Override
    public List<ExcelThuongHieu> importExcel(List<ExcelThuongHieu> excelThuongHieus) {
        // tạo list để chứa các đối tượng bị lỗi
        List<ExcelThuongHieu> errorImports = new ArrayList<>();
        // sử dụng for để lặp các đối tượng được truyền vào từ file excel
        for (ExcelThuongHieu x : excelThuongHieus) {
            // lấy ra đối tượng theo tên được truyền vào từ file excel
            ThuongHieu thuongHieu = this.findByName(x.getTenThuongHieu());

            // kiểm nếu tên truyền vào null || space add vào errorImports
            if (x.getTenThuongHieu() == null || x.getTenThuongHieu().isBlank()) {
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
            if (thuongHieu == null) {
                ThuongHieu thuongHieu1 = ThuongHieu.builder()
                        .id(null)
                        .ten(x.getTenThuongHieu())
                        .trangThai(utility.getNumberByNameStatus(x.getTrangThai()))
                        .build();
                System.out.println("case3");
                thuongHieuRepository.save(thuongHieu1);
            } else {// nếu đã có thì cập nhật lại thông tin
                ThuongHieu thuongHieu2 = ThuongHieu.builder()
                        .id(thuongHieu.getId())
                        .ten(x.getTenThuongHieu())
                        .trangThai(utility.getNumberByNameStatus(x.getTrangThai()))
                        .build();
                System.out.println("case3");
                thuongHieuRepository.save(thuongHieu2);
            }
        }
        return errorImports;
    }

    @Override
    public List<ExcelThuongHieu> exportExcel() {
        List<ExcelThuongHieu> excelThuongHieus = new ArrayList<>();
        Integer index = 1;
        for (ThuongHieu x : thuongHieuRepository.findAll()) {// convert từ ThuongHieu sang ExcelThuongHieu
            ExcelThuongHieu excelThuongHieu = ExcelThuongHieu.builder()
                    .stt(index++)
                    .tenThuongHieu(x.getTen())
                    .trangThai(utility.trangThaiSanPham(x.getTrangThai()))
                    .build();
            excelThuongHieus.add(excelThuongHieu);
        }
        return excelThuongHieus;
    }

    @Override
    public ThuongHieu create(ThuongHieuRequest thuongHieuRequest) {
        ThuongHieu thuongHieu = ObjectMapperUtils.map(thuongHieuRequest, ThuongHieu.class);
        thuongHieu.setId(null);
        return thuongHieuRepository.save(thuongHieu);
    }

    @Override
    public ThuongHieu update(Integer id,ThuongHieuRequest thuongHieuRequest) {
        ThuongHieu thuongHieu = ObjectMapperUtils.map(thuongHieuRequest, ThuongHieu.class);
        thuongHieu.setId(id);
        return thuongHieuRepository.save(thuongHieu);
    }

    @Override
    public ThuongHieu delete(Integer id) {
        Optional<ThuongHieu> thuongHieu = thuongHieuRepository.findById(id);
        if (thuongHieu.isPresent()){
            thuongHieu.get().setTrangThai(0);
            return thuongHieuRepository.save(thuongHieu.get());
        }
        return null;
    }

}
