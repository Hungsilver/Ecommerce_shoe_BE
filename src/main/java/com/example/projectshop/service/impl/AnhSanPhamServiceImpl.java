package com.example.projectshop.service.impl;

import com.example.projectshop.domain.AnhSanPham;
import com.example.projectshop.repository.AnhSanPhamRepository;
import com.example.projectshop.service.IAnhSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnhSanPhamServiceImpl implements IAnhSanPhamService {

    @Autowired
    private AnhSanPhamRepository anhSanPhamRepo;

    @Override
    public void delete(String id) {
        AnhSanPham anhSanPham = anhSanPhamRepo.findById(id).get();
        anhSanPhamRepo.delete(anhSanPham);
    }
}
