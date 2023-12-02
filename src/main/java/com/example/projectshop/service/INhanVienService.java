package com.example.projectshop.service;

import com.example.projectshop.domain.NhanVien;
import com.example.projectshop.domain.ThuongHieu;
import com.example.projectshop.dto.BaseResponse;
import com.example.projectshop.dto.nhanvien.NhanVienRequest;
import com.example.projectshop.dto.nhanvien.NhanVienResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface INhanVienService {
    Page<NhanVien> getAll(Pageable pageable);

    NhanVien findById(Integer id);

    Page<NhanVien> findAllByName(String name, Pageable pageable);

    NhanVien findByName(String name);

    NhanVien create(NhanVien nhanVien);

    NhanVien update(NhanVien nhanVien, Integer id);

    NhanVien delete(Integer id);

    BaseResponse registerAccount(NhanVienRequest nhanVienRequest);

    NhanVien insertNhanVien(NhanVienRequest nhanVienRequest);

    public NhanVienResponse authenticateUser(NhanVienRequest nhanVienRequest);
}
