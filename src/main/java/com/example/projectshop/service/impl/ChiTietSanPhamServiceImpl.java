package com.example.projectshop.service.impl;

import com.example.projectshop.domain.ChatLieuGiay;
import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.dto.chatlieugiay.ChatLieuGiayResponse;
import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamRequest;
import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamResponse;
import com.example.projectshop.repository.ChiTietSanPhamRepository;
import com.example.projectshop.service.IChiTietSanPhamService;
import com.example.projectshop.service.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChiTietSanPhamServiceImpl implements IChiTietSanPhamService {

    @Autowired
    private ChiTietSanPhamRepository repo;

    @Override
    public List<ChiTietSanPhamResponse> findAll() {
        List<ChiTietSanPhamResponse> list = ObjectMapperUtils.mapAll(repo.findAll(), ChiTietSanPhamResponse.class);
        return list;
    }

    @Override
    public Page<ChiTietSanPhamResponse> getAll(String pageParam, String limitParam) {
        Integer page = pageParam == null ? 0 : Integer.valueOf(pageParam);
        Integer limit = limitParam == null ? 5 : Integer.valueOf(limitParam);
        Pageable pageable = PageRequest.of(page, limit);
        Page<ChiTietSanPhamResponse> listPage = ObjectMapperUtils.mapEntityPageIntoDtoPage(repo.findAll(pageable), ChiTietSanPhamResponse.class);
        return listPage;
    }

    @Override
    public ChiTietSanPhamResponse getOne(Integer id) {
        return ObjectMapperUtils.map(repo.findById(id).get(),ChiTietSanPhamResponse.class);
    }

    @Override
    public ChiTietSanPhamResponse create(ChiTietSanPhamRequest chiTietSanPhamRequest) {
        ChiTietSanPham entity = new ChiTietSanPham();
        entity.setId(null);
        entity.setSoLuong(chiTietSanPhamRequest.getSoLuong());
        entity.setGiaBan(chiTietSanPhamRequest.getGiaBan());
        entity.setNgayTao(chiTietSanPhamRequest.getNgayTao());
        entity.setTrangThai(chiTietSanPhamRequest.getTrangThai());
        entity.setMauSac(chiTietSanPhamRequest.getMausac());
        entity.setKichCo(chiTietSanPhamRequest.getKichco());
        entity.setChatLieuGiay(chiTietSanPhamRequest.getChatLieuGiay());
        entity.setChatLieuDeGiay(chiTietSanPhamRequest.getChatLieuDeGiay());
        entity.setSanPham(chiTietSanPhamRequest.getSanpham());
        ChiTietSanPham entityRs = repo.save(entity);
        ChiTietSanPhamResponse response = ObjectMapperUtils.map(entityRs, ChiTietSanPhamResponse.class);
        return response;
    }

    @Override
    public ChiTietSanPhamResponse update(Integer id,ChiTietSanPhamRequest chiTietSanPhamRequest) {
        ChiTietSanPham entity = new ChiTietSanPham();
        entity.setId(id);
        entity.setSoLuong(chiTietSanPhamRequest.getSoLuong());
        entity.setGiaBan(chiTietSanPhamRequest.getGiaBan());
        entity.setNgayTao(chiTietSanPhamRequest.getNgayTao());
        entity.setTrangThai(chiTietSanPhamRequest.getTrangThai());
        entity.setMauSac(chiTietSanPhamRequest.getMausac());
        entity.setKichCo(chiTietSanPhamRequest.getKichco());
        entity.setChatLieuGiay(chiTietSanPhamRequest.getChatLieuGiay());
        entity.setChatLieuDeGiay(chiTietSanPhamRequest.getChatLieuDeGiay());
        entity.setSanPham(chiTietSanPhamRequest.getSanpham());
        ChiTietSanPham entityRs = repo.save(entity);
        ChiTietSanPhamResponse response = ObjectMapperUtils.map(entityRs, ChiTietSanPhamResponse.class);
        return response;
    }

    @Override
    public void delete(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public Page<ChiTietSanPhamResponse> timKiem(String timKiem, String pageParam, String limitParam) {
        Integer page = pageParam == null ? 0 : Integer.valueOf(pageParam);
        Integer limit = limitParam == null ? 5 : Integer.valueOf(limitParam);
        Pageable pageable = PageRequest.of(page, limit);
        Page<ChiTietSanPhamResponse> listPage = ObjectMapperUtils.mapEntityPageIntoDtoPage(repo.timKiem(timKiem,pageable), ChiTietSanPhamResponse.class);
        return listPage;
    }
}
