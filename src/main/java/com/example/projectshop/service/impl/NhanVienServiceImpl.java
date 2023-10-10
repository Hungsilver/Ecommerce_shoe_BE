package com.example.projectshop.service.impl;

import com.example.projectshop.domain.Nhanvien;
import com.example.projectshop.repository.NhanVienRepository;
import com.example.projectshop.service.INhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NhanVienServiceImpl implements INhanVienService {
    @Autowired
    private NhanVienRepository nvRepository;

    @Override
    public Page<Nhanvien> getAll(Pageable pageable) {
        return nvRepository.findAll(pageable);
    }

    @Override
    public Nhanvien add(Nhanvien cv) {
        return null;
    }

    @Override
    public Nhanvien update(Nhanvien cv) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
