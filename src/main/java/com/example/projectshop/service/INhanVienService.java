package com.example.projectshop.service;

import com.example.projectshop.domain.MauSac;
import com.example.projectshop.domain.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface INhanVienService {
    Page<NhanVien> getAll(Pageable pageable);

    Optional<NhanVien> findById(Integer id);

    Page<NhanVien> findAllByName(String name,Pageable pageable);

    NhanVien create(NhanVien nhanVien);
    NhanVien update(NhanVien nhanVien,Integer id);
    NhanVien delete(Integer id);


}
