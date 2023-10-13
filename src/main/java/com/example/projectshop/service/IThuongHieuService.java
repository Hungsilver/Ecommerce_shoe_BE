package com.example.projectshop.service;

import com.example.projectshop.dto.mausac.MauSacResponse;
import com.example.projectshop.dto.thuonghieu.ThuongHieuRequest;
import com.example.projectshop.dto.thuonghieu.ThuongHieuResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IThuongHieuService {
    List<ThuongHieuResponse> getAll();

    ThuongHieuResponse findById(Integer id);

    ThuongHieuResponse create(ThuongHieuRequest thuongHieuRequest);

    ThuongHieuResponse update(ThuongHieuRequest thuongHieuRequest, Integer id);

    void delete(Integer id);

    Page<ThuongHieuResponse> findAllThuongHieu(String pageParam, String limitParam);

}
