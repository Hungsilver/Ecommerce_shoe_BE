package com.example.projectshop.service;

import com.example.projectshop.domain.KhachHang;
import com.example.projectshop.dto.khachhang.KhachHangRequest;
import com.example.projectshop.dto.khachhang.KhachHangRespone;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IKhachHangService {

    List<KhachHang> findAll();

    Page<KhachHang> getAll(String pageParam,String limitParam);

    KhachHangRespone create(KhachHangRequest khachHangRequest);

    KhachHangRespone update(Integer id,KhachHangRequest khachHangRequest);

    void delete (Integer id);

    Page<KhachHang> timKiem(String timKiem,String pageParam,String limitParam);
}
