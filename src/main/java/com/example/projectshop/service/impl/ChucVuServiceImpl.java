package com.example.projectshop.service.impl;

import com.example.projectshop.domain.ChucVu;
import com.example.projectshop.domain.MauSac;
import com.example.projectshop.repository.ChucVuRepository;
import com.example.projectshop.service.IChucVuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Optional;
@Service
public class ChucVuServiceImpl implements IChucVuService {

    @Autowired
    private ChucVuRepository chucVuRepository;
    @Override
    public Page<ChucVu> getAll(Pageable pageable) {
        return chucVuRepository.findAll(pageable);
    }

    @Override
    public Optional<ChucVu> findById(Integer id) {
        return chucVuRepository.findById(id);
    }

    @Override
    public Page<ChucVu> findAllByName(String name, Pageable pageable) {
        return chucVuRepository.findAllByTen("%" + name + "%",pageable);
    }

    @Override
    public ChucVu create(ChucVu chucVu) {
        return chucVuRepository.save(chucVu);
    }

    @Override
    public ChucVu update(ChucVu chucVu, Integer id) {
        chucVu.setId(id);
        return chucVuRepository.save(chucVu);
    }

    @Override
    public ChucVu delete(Integer id) {
        Optional<ChucVu> ms = chucVuRepository.findById(id);
        if(ms.isPresent()){
            ms.get().setTrangThai(0);
            return chucVuRepository.save(ms.get());
        }
        return null;
    }
}
