package com.example.projectshop.service.impl;

import com.example.projectshop.domain.KhachHang;
import com.example.projectshop.dto.chatlieudegiay.ChatLieuDeGiayResponse;
import com.example.projectshop.dto.khachhang.KhachHangRequest;
import com.example.projectshop.dto.khachhang.KhachHangRespone;
import com.example.projectshop.repository.KhachHangRepository;
import com.example.projectshop.service.IKhachHangService;
import com.example.projectshop.service.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KhachHangServiceImpl implements IKhachHangService {
    @Autowired
    private KhachHangRepository khachHangRepo;
    @Override
    public List<KhachHang> findAll() {
        return khachHangRepo.findAll();
    }

    @Override
    public Page<KhachHang> getAll(String pageParam, String limitParam) {
        Integer page = pageParam == null ? 0 : Integer.valueOf(pageParam);
        Integer limit = limitParam == null ? 6 : Integer.valueOf(limitParam);
        Pageable pageable = PageRequest.of(page, limit);
        return khachHangRepo.findAll(pageable);
    }

    @Override
    public KhachHangRespone create(KhachHangRequest khachHangRequest) {
        KhachHang entity = new KhachHang();
        entity.setId(null);
        entity.setEmail(khachHangRequest.getEmail());
        entity.setHoTen(khachHangRequest.getHoTen());
        entity.setMatKhau(khachHangRequest.getMatKhau());
        entity.setNgaySinh(khachHangRequest.getNgaySinh());
        entity.setSoDienThoai(khachHangRequest.getSoDienThoai());
        entity.setTrangThai(khachHangRequest.getTrangThai());
        KhachHangRespone entityRs = ObjectMapperUtils.map(khachHangRepo.save(entity),KhachHangRespone.class);
        return entityRs;
    }

    @Override
    public KhachHangRespone update(Integer id,KhachHangRequest khachHangRequest) {
        KhachHang entity = new KhachHang();
        entity.setId(id);
        entity.setEmail(khachHangRequest.getEmail());
        entity.setHoTen(khachHangRequest.getHoTen());
        entity.setMatKhau(khachHangRequest.getMatKhau());
        entity.setNgaySinh(khachHangRequest.getNgaySinh());
        entity.setSoDienThoai(khachHangRequest.getSoDienThoai());
        entity.setTrangThai(khachHangRequest.getTrangThai());
        KhachHangRespone entityRs = ObjectMapperUtils.map(khachHangRepo.save(entity),KhachHangRespone.class);
        return entityRs;
    }

    @Override
    public void delete(Integer id) {
khachHangRepo.deleteById(id);
    }

    @Override
    public Page<KhachHang> timKiem(String timKiem, String pageParam, String limitParam) {
        Integer page = pageParam == null ? 0 : Integer.valueOf(pageParam);
        Integer limit = limitParam == null ? 6 : Integer.valueOf(limitParam);
        Pageable pageable = PageRequest.of(page, limit);
return khachHangRepo.timKiem(timKiem,pageable);
    }
}
