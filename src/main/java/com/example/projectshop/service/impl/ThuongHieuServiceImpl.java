package com.example.projectshop.service.impl;

import com.example.projectshop.domain.Thuonghieu;
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
        List<Thuonghieu> thu = thuongHieuRepository.findAll();
        // ánh xạ từ thuonghieu sang thuonghieuReponse bằng mapAll
        List<ThuongHieuResponse> reponse = ObjectMapperUtils.mapAll(thu, ThuongHieuResponse.class);
        return reponse;
    }

    @Override
    public ThuongHieuResponse findById(Integer id) {
        Thuonghieu thchitiet = thuongHieuRepository.findById(id).orElse(null);
        ThuongHieuResponse response = ObjectMapperUtils.map(thchitiet, ThuongHieuResponse.class);

        return response;
    }

    @Override
    public ThuongHieuResponse create(ThuongHieuRequest thuongHieuRequest) {
        Thuonghieu th = ObjectMapperUtils.map(thuongHieuRequest, Thuonghieu.class);
        th.setTen(thuongHieuRequest.getTen());
        thuongHieuRepository.save(th);
        ThuongHieuResponse response = ObjectMapperUtils.map(th, ThuongHieuResponse.class);
        return response;
    }

    @Override
    public ThuongHieuResponse update(ThuongHieuRequest thuongHieuRequest) {
        Thuonghieu th = ObjectMapperUtils.map(thuongHieuRequest, Thuonghieu.class);
        th.setTen(thuongHieuRequest.getTen());
        th.setId(thuongHieuRequest.getId());
        thuongHieuRepository.save(th);
        ThuongHieuResponse response = ObjectMapperUtils.map(th, ThuongHieuResponse.class);
        return response;
    }

    @Override
    public void delete(Integer id) {
        thuongHieuRepository.deleteById(id);
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
