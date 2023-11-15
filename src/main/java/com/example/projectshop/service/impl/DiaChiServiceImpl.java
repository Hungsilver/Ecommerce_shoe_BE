package com.example.projectshop.service.impl;

import com.example.projectshop.domain.DiaChi;
import com.example.projectshop.dto.diachi.DiaChiRequest;
import com.example.projectshop.repository.DiaChiRepository;
import com.example.projectshop.service.IDiaChiService;
import com.example.projectshop.service.IKhachHangService;
import com.example.projectshop.service.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DiaChiServiceImpl implements IDiaChiService {

    @Autowired
    private DiaChiRepository diaChiRepo;

    @Autowired
    private IKhachHangService khachHangService;

    @Override
    public Page<DiaChi> findAll(Integer page,
                                Integer pageSize,
                                String sortField,
                                Boolean isSortDesc,
                                String keyword) {
        Sort sort = Sort.by(isSortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, pageSize, sort);
        if (keyword != null && !keyword.isEmpty()) {
            return diaChiRepo.findAllByDiaChi(keyword, pageable);
        } else {
            return diaChiRepo.findAll(pageable);
        }
    }

    @Override
    public Optional<DiaChi> findById(Integer id) {
        return diaChiRepo.findById(id);
    }

    @Override
    public DiaChi create(DiaChiRequest diaChiRequest) {
        DiaChi diaChi = DiaChi.builder()
                .id(null)
                .diaChi(diaChiRequest.getDiaChi())
                .phuongXa(diaChiRequest.getPhuongXa())
                .quanHuyen(diaChiRequest.getQuanHuyen())
                .tinhThanh(diaChiRequest.getTinhThanh())
                .trangThai(diaChiRequest.getTrangThai())
                .khachHang(khachHangService.findById(diaChiRequest.getKhachHang()))
                .build();
        return diaChiRepo.save(diaChi);
    }

    @Override
    public DiaChi update(Integer id, DiaChiRequest diaChiRequest) {
        DiaChi diaChi = DiaChi.builder()
                .id(id)
                .diaChi(diaChiRequest.getDiaChi())
                .phuongXa(diaChiRequest.getPhuongXa())
                .quanHuyen(diaChiRequest.getQuanHuyen())
                .tinhThanh(diaChiRequest.getTinhThanh())
                .trangThai(diaChiRequest.getTrangThai())
                .khachHang(khachHangService.findById(diaChiRequest.getKhachHang()))
                .build();
        return diaChiRepo.save(diaChi);
    }

    @Override
    public DiaChi delete(Integer id) {
        Optional<DiaChi> diaChi = this.findById(id);
        if (diaChi.isPresent()) {
            diaChi.get().setTrangThai(1);
            return diaChiRepo.save(diaChi.get());
        }
        return null;
    }

}
