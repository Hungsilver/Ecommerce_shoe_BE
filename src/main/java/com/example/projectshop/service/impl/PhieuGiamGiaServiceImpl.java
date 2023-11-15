package com.example.projectshop.service.impl;

import com.example.projectshop.domain.PhieuGiamGia;
import com.example.projectshop.dto.phieugiamgia.PhieuGiamGiaRequest;
import com.example.projectshop.repository.PhieuGiamGiaRepository;
import com.example.projectshop.service.IPhieuGiamGiaService;
import com.example.projectshop.service.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PhieuGiamGiaServiceImpl implements IPhieuGiamGiaService {

    @Autowired
    private PhieuGiamGiaRepository phieuGiamGiaRepository;

    @Override
    public Page<PhieuGiamGia> findAll(Integer page,
                                      Integer pageSize,
                                      String sortField,
                                      Boolean isSortDesc,
                                      String keyword) {

        Sort sort = Sort.by(isSortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, pageSize, sort);
        if (keyword != null && !keyword.isEmpty()) {
            return phieuGiamGiaRepository.findAllByMa(keyword, pageable);
        } else {
            return phieuGiamGiaRepository.findAll(pageable);
        }
    }

    @Override
    public PhieuGiamGia findById(Integer id) {
        if (id != null){
            return phieuGiamGiaRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public PhieuGiamGia create(PhieuGiamGiaRequest phieuGiamGiaRequest) {
        PhieuGiamGia phieuGiamGia = PhieuGiamGia.builder()
                .id(null)
                .ma(phieuGiamGiaRequest.getMa())
                .ten(phieuGiamGiaRequest.getTen())
                .chietKhau(phieuGiamGiaRequest.getChietKhau())
                .hinhThucGiamGia(phieuGiamGiaRequest.getHinhThucGiamGia())
                .thoiGianBatDau(phieuGiamGiaRequest.getThoiGianBatDau())
                .thoiGianKetThuc(phieuGiamGiaRequest.getThoiGianKetThuc())
                .ma(phieuGiamGiaRequest.getMoTa())
                .trangThai(phieuGiamGiaRequest.getTrangThai())
                .build();
        return phieuGiamGiaRepository.save(phieuGiamGia);
    }

    @Override
    public PhieuGiamGia update(Integer id, PhieuGiamGiaRequest phieuGiamGiaRequest) {
        PhieuGiamGia phieuGiamGia = PhieuGiamGia.builder()
                .id(id)
                .ma(phieuGiamGiaRequest.getMa())
                .ten(phieuGiamGiaRequest.getTen())
                .chietKhau(phieuGiamGiaRequest.getChietKhau())
                .hinhThucGiamGia(phieuGiamGiaRequest.getHinhThucGiamGia())
                .thoiGianBatDau(phieuGiamGiaRequest.getThoiGianBatDau())
                .thoiGianKetThuc(phieuGiamGiaRequest.getThoiGianKetThuc())
                .ma(phieuGiamGiaRequest.getMoTa())
                .trangThai(phieuGiamGiaRequest.getTrangThai())
                .build();
        return phieuGiamGiaRepository.save(phieuGiamGia);
    }

    @Override
    public PhieuGiamGia delete(Integer id) {
        Optional<PhieuGiamGia> phieuGiamGia = phieuGiamGiaRepository.findById(id);
        if (phieuGiamGia.isPresent()) {
            phieuGiamGia.get().setTrangThai(0);
            return phieuGiamGiaRepository.save(phieuGiamGia.get());
        }
        return null;
    }
}
