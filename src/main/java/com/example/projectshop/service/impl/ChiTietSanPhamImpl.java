package com.example.projectshop.service.impl;

import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamResponse;
import com.example.projectshop.repository.ChiTietSanPhamRepository;
import com.example.projectshop.service.IChiTietSanPhamService;
import com.example.projectshop.service.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChiTietSanPhamImpl implements IChiTietSanPhamService {

    @Autowired
    private ChiTietSanPhamRepository repo;

    @Override
    public List<ChiTietSanPhamResponse> findAll() {
        List<ChiTietSanPhamResponse> list = ObjectMapperUtils.mapAll(repo.findAll(), ChiTietSanPhamResponse.class);
        return list;
    }

    @Override
    public Page<ChiTietSanPhamResponse> getAll(String pageParam, String limitParam) {
        Integer page = pageParam == null ? 0 : Integer.valueOf(pageParam);
        Integer limit = limitParam == null ? 5 : Integer.valueOf(limitParam);
        Pageable pageable = PageRequest.of(page, limit);
        Page<ChiTietSanPhamResponse> listPage = ObjectMapperUtils.mapEntityPageIntoDtoPage(repo.findAll(pageable), ChiTietSanPhamResponse.class);
        return listPage;
    }

    @Override
    public ChiTietSanPhamResponse getOne(Integer id) {
        return ObjectMapperUtils.map(repo.findById(id).get(),ChiTietSanPhamResponse.class);
    }
}
