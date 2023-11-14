package com.example.projectshop.service;

import com.example.projectshop.domain.PhieuGiamGia;
import com.example.projectshop.domain.ThuongHieu;
import com.example.projectshop.dto.phieugiamgia.PhieuGiamGiaRequest;
import com.example.projectshop.dto.phieugiamgia.PhieuGiamGiaResponse;
import com.example.projectshop.dto.thuonghieu.ThuongHieuRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IPhieuGiamGiaService {

    Page<PhieuGiamGia> findAll(
            Integer page,
            Integer pageSize,
            String sortField,
            Boolean isSortDesc,
            String keyword
    );

    PhieuGiamGia findById(Integer id);

    PhieuGiamGia create(PhieuGiamGiaRequest PhieuGiamGiaRequest);

    PhieuGiamGia update(Integer id, PhieuGiamGiaRequest PhieuGiamGiaRequest);

    PhieuGiamGia delete(Integer id);
}
