package com.example.projectshop.service.impl;

import com.example.projectshop.domain.PhieuGiamGia;
import com.example.projectshop.dto.phieugiamgia.PhieuGiamGiaRequest;
import com.example.projectshop.dto.phieugiamgia.PhieuGiamGiaResponse;
import com.example.projectshop.repository.HoaDonRepository;
import com.example.projectshop.repository.PhieuGiamGiaRepository;
import com.example.projectshop.service.IPhieuGiamGiaService;
import com.example.projectshop.service.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhieuGiamGiaServiceImpl implements IPhieuGiamGiaService {

    @Autowired
    private PhieuGiamGiaRepository phieuGiamGiaRepository;

    @Autowired
    private HoaDonRepository hoaDonRepo;

    @Override
    public List<PhieuGiamGiaResponse> findAll() {
        List<PhieuGiamGiaResponse> list = ObjectMapperUtils.mapAll(phieuGiamGiaRepository.findAll(),PhieuGiamGiaResponse.class);
        return list;
    }

    @Override
    public Page<PhieuGiamGiaResponse> getAll(String pageParam, String limitParam) {
        Integer page = pageParam == null ? 0 : Integer.valueOf(pageParam);
        Integer limit = limitParam == null ? 5 : Integer.valueOf(limitParam);
        Pageable pageable = PageRequest.of(page,limit);
        Page<PhieuGiamGiaResponse> list = ObjectMapperUtils.mapEntityPageIntoDtoPage(phieuGiamGiaRepository.findAll(pageable),PhieuGiamGiaResponse.class);
        return list;
    }

    @Override
    public PhieuGiamGiaResponse getOne(Integer id) {
        PhieuGiamGiaResponse response = ObjectMapperUtils.map(phieuGiamGiaRepository.findById(id),PhieuGiamGiaResponse.class);
        return response;
    }

    @Override
    public PhieuGiamGiaResponse create(PhieuGiamGiaRequest phieuGiamGiaRequest) {
        PhieuGiamGia phieuGiamGia = ObjectMapperUtils.map(phieuGiamGiaRequest,PhieuGiamGia.class);
        phieuGiamGia.setId(null);
        phieuGiamGia.setMa(phieuGiamGiaRequest.getMa());
        phieuGiamGia.setTen(phieuGiamGiaRequest.getTen());
        phieuGiamGia.setChietKhau(phieuGiamGiaRequest.getChietKhau());
        phieuGiamGia.setHinhThucGiamGia(phieuGiamGiaRequest.getHinhThucGiamGia());
        phieuGiamGia.setThoiGianBatDau(phieuGiamGiaRequest.getThoiGianBatDau());
        phieuGiamGia.setThoiGianKetThuc(phieuGiamGiaRequest.getThoiGianKetThuc());
        phieuGiamGia.setMoTa(phieuGiamGiaRequest.getMoTa());
        phieuGiamGia.setTrangThai(phieuGiamGiaRequest.getTrangThai());
        phieuGiamGia.setKhachHang(phieuGiamGiaRequest.getKhachHang());
        phieuGiamGiaRepository.save(phieuGiamGia);
        return ObjectMapperUtils.map(phieuGiamGia,PhieuGiamGiaResponse.class);
    }

    @Override
    public PhieuGiamGiaResponse update(Integer id,PhieuGiamGiaRequest phieuGiamGiaRequest) {
        PhieuGiamGia phieuGiamGia = ObjectMapperUtils.map(phieuGiamGiaRequest,PhieuGiamGia.class);
        phieuGiamGia.setId(id);
        phieuGiamGia.setMa(phieuGiamGiaRequest.getMa());
        phieuGiamGia.setTen(phieuGiamGiaRequest.getTen());
        phieuGiamGia.setChietKhau(phieuGiamGiaRequest.getChietKhau());
        phieuGiamGia.setHinhThucGiamGia(phieuGiamGiaRequest.getHinhThucGiamGia());
        phieuGiamGia.setThoiGianBatDau(phieuGiamGiaRequest.getThoiGianBatDau());
        phieuGiamGia.setThoiGianKetThuc(phieuGiamGiaRequest.getThoiGianKetThuc());
        phieuGiamGia.setMoTa(phieuGiamGiaRequest.getMoTa());
        phieuGiamGia.setTrangThai(phieuGiamGiaRequest.getTrangThai());
        phieuGiamGia.setKhachHang(phieuGiamGiaRequest.getKhachHang());
        phieuGiamGiaRepository.save(phieuGiamGia);
        return ObjectMapperUtils.map(phieuGiamGia,PhieuGiamGiaResponse.class);
    }

    @Override
    public void delete(Integer id) {
//        if (hoaDonRepo.findByPhieuGiamGia(id) == null){
//            phieuGiamGiaRepository.deleteById(id);
//        }
//        PhieuGiamGiaRequest phieuGiamGiaRequest = ObjectMapperUtils.map(this.getOne(id),PhieuGiamGiaRequest.class);
//        phieuGiamGiaRequest.setTrangThai(1);
//        this.update(id,phieuGiamGiaRequest);
    }
}
