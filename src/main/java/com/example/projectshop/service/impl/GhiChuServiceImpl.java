package com.example.projectshop.service.impl;

import com.example.projectshop.domain.GhiChu;
import com.example.projectshop.domain.NhanVien;
import com.example.projectshop.repository.GhiChuRepository;
import com.example.projectshop.repository.HoaDonRepository;
import com.example.projectshop.service.IGhiChuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;
import java.time.LocalDateTime;

@Service
public class GhiChuServiceImpl implements IGhiChuService {
    @Autowired
    private GhiChuRepository ghiChuRepo;

    @Autowired
    private HoaDonRepository hoaDonRepo;

    @Autowired
    private WebApplicationContext appContext;

    private LocalDateTime currentDateTime = LocalDateTime.now();

    @Override
    public Page<GhiChu> findByIdHoaDon(Integer page,
                                       Integer pageSize,
                                       Integer idHoaDon) {
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, pageSize);
        return ghiChuRepo.findByIdHoaDon(idHoaDon, pageable);
    }

    @Override
    public GhiChu add(Integer idHoaDon, String ghiChu, Integer trangThai) {
        NhanVien nhanVien = (NhanVien) appContext.getServletContext().getAttribute("nhanVien");
        Date currentDate = new Date(System.currentTimeMillis());
        GhiChu ghiChu1 = GhiChu.builder()
                .id(null)
                .ghiChu(ghiChu)
                .ngayTao(currentDate)
                .trangThai(trangThai)
                .hoaDon(hoaDonRepo.findById(idHoaDon).get())
                .nhanVien(nhanVien)
                .build();
        return ghiChuRepo.save(ghiChu1);
    }
}
