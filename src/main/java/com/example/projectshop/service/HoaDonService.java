package com.example.projectshop.service;

import com.example.projectshop.domain.HoaDon;
import com.example.projectshop.dto.hoadon.HoaDonRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface HoaDonService {

    Page<HoaDon> findAll(Integer status,
                         String keyword,
                         String sortField,
                         Boolean isSortDesc,
                         Integer page,
                         Integer pageSize);

    Optional<HoaDon> findById(Integer id);

    HoaDon shopCheckout(HoaDonRequest hoaDonRequest);

    HoaDon onlineCheckout(HoaDonRequest hoaDonRequest);

    HoaDon choVanChuyen(Integer id);

    HoaDon dangGiao(Integer id);

    HoaDon daGiao(Integer id);

    HoaDon daHuy(Integer id);

    HoaDon traHang(Integer id);

}
