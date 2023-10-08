package com.example.projectshop.service.impl;

import com.example.projectshop.domain.Nhanvien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface INhanVienService {
    Page<Nhanvien> getAll(Pageable pageable);
    Nhanvien add(Nhanvien cv);
    Nhanvien update(Nhanvien cv);
    void delete(Integer id);
}
