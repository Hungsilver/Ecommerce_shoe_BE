package com.example.projectshop.service.impl;

import com.example.projectshop.dto.mausac.MauSacResponse;
import com.example.projectshop.dto.thuonghieu.ThuongHieuRequest;
import com.example.projectshop.dto.thuonghieu.ThuongHieuResponse;
import com.example.projectshop.repository.ThuongHieuRepository;
import com.example.projectshop.service.IThuongHieuService;
import com.example.projectshop.service.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThuongHieuServiceImpl implements IThuongHieuService {

    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    @Override
    public List<ThuongHieuResponse> getAll() {
        return null;
    }

    @Override
    public ThuongHieuResponse findById(Integer id) {
        return null;
    }

    @Override
    public ThuongHieuResponse create(ThuongHieuRequest thuongHieuRequest) {
        return null;
    }

    @Override
    public ThuongHieuResponse update(ThuongHieuRequest thuongHieuRequest, Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Page<ThuongHieuResponse> findAllThuongHieu(String pageParam, String limitParam) {
        Integer page = pageParam == null ? 0 : Integer.valueOf(pageParam);
        Integer limit = limitParam == null ? 3 : Integer.valueOf(limitParam);
        Pageable pageable = PageRequest.of(page, limit);
        Page<ThuongHieuResponse> list = ObjectMapperUtils.mapEntityPageIntoDtoPage(thuongHieuRepository.findAll(pageable), ThuongHieuResponse.class);
        return list;
    }
}
