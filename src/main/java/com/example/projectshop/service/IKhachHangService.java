package com.example.projectshop.service;

import com.example.projectshop.domain.KhachHang;
import com.example.projectshop.domain.ThuongHieu;
import com.example.projectshop.dto.khachhang.KhachHangRequest;
import com.example.projectshop.dto.khachhang.KhachHangRespone;
import com.example.projectshop.dto.thuonghieu.ThuongHieuRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IKhachHangService {

    Page<KhachHang> findAll(
            Integer page,
            Integer pageSize,
            String sortField,
            Boolean isSortDesc,
            String keyword
    );

    Optional<KhachHang> findById(Integer id);

    KhachHang create(KhachHangRequest khachHangRequest);

    KhachHang update(Integer id, KhachHangRequest khachHangRequest);

    KhachHang delete(Integer id);
}
