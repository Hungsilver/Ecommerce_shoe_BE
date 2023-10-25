package com.example.projectshop.service.impl;

import com.example.projectshop.domain.MauSac;
import com.example.projectshop.domain.NhanVien;
import com.example.projectshop.repository.NhanVienRepository;
import com.example.projectshop.service.INhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NhanVienServiceImpl implements INhanVienService {

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Override
    public Page<NhanVien> getAll(Pageable pageable) {
        return nhanVienRepository.findAll(pageable);
    }

    @Override
    public Optional<NhanVien> findById(Integer id) {
        return nhanVienRepository.findById(id);
    }

    @Override
    public Page<NhanVien> findAllByName(String name, Pageable pageable) {
        return nhanVienRepository.findAllByTen("%" + name + "%",pageable);
    }

    @Override
    public NhanVien create(NhanVien nhanVien) {
        nhanVien.setId(null);

        return nhanVienRepository.save(nhanVien);
    }

    @Override
    public NhanVien update(NhanVien nhanVien, Integer id) {
        nhanVien.setId(id);
        return nhanVienRepository.save(nhanVien);
    }

    @Override
    public NhanVien delete(Integer id) {
        Optional<NhanVien> ms = nhanVienRepository.findById(id);
        if(ms.isPresent()){
            ms.get().setTrangThai(0);
            return nhanVienRepository.save(ms.get());
        }
        return null;
    }
}
