package com.example.projectshop.service;

import com.example.projectshop.domain.PhieuGiamGia;
import com.example.projectshop.dto.phieugiamgia.PhieuGiamGiaResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IPhieuGiamGiaService {

    List<PhieuGiamGiaResponse> findAll();

    Page<PhieuGiamGiaResponse> getAll(String pageParam,String limitParam);

    PhieuGiamGiaResponse getOne(Integer id);
}
