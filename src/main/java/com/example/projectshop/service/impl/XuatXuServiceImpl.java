package com.example.projectshop.service.impl;

import com.example.projectshop.domain.Xuatxu;
import com.example.projectshop.dto.mausac.MauSacResponse;
import com.example.projectshop.dto.xuatxu.XuatXuRequest;
import com.example.projectshop.dto.xuatxu.XuatXuResponse;
import com.example.projectshop.repository.XuatXuRepository;
import com.example.projectshop.service.IXuatXuService;
import com.example.projectshop.service.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class XuatXuServiceImpl {
//
//    @Autowired
//    private XuatXuRepository xuatXuRepository;
//
//    @Override
//    public List<XuatXuResponse> getAll() {
//        return null;
//    }
//
//    @Override
//    public XuatXuResponse findById(Integer id) {
//
//        return null;
//    }
//
//    @Override
//    public XuatXuResponse create(XuatXuRequest xuatXuRequest) {
//        return null;
//    }
//
//    @Override
//    public XuatXuResponse update(XuatXuRequest xuatXuRequest, Integer id) {
//        return null;
//    }
//
//    @Override
//    public void delete(Integer id) {
//
//    }

//    @Override
//    public Page<XuatXuResponse> findAllXuatXu(String pageParam, String limitParam) {
//        Integer page = pageParam == null ? 0 : Integer.valueOf(pageParam);
//        Integer limit = limitParam == null ? 3 : Integer.valueOf(limitParam);
//        Pageable pageable = PageRequest.of(page, limit);
//        Page<XuatXuResponse> list = ObjectMapperUtils.mapEntityPageIntoDtoPage(xuatXuRepository.findAll(pageable), XuatXuResponse.class);
//        return list;
//    }


    @Autowired
    private XuatXuRepository xuatXuRepository;

    public Page<Xuatxu> findAll(Pageable pageable) {
        return xuatXuRepository.findAll(pageable);
    }

    public Optional<Xuatxu> findById(Integer id) {
        return xuatXuRepository.findById(id);
    }

    public Page<Xuatxu> findAllByName(String name, Pageable pageable) {
        return xuatXuRepository.findAllByTen("%"+name+"%", pageable);
    }

    public Xuatxu create(Xuatxu xuatxu) {
        xuatxu.setId(null);
        return xuatXuRepository.save(xuatxu);
    }

    public Xuatxu update(Xuatxu xuatxu, Integer id) {
        xuatxu.setId(id);
        return xuatXuRepository.save(xuatxu);
    }

    public Xuatxu delete(Integer id) {
        Optional<Xuatxu> xx = xuatXuRepository.findById(id);
        if (xx.isPresent()) {
            xx.get().setTrangThai(0);
            return xuatXuRepository.save(xx.get());
        }
        return null;
    }
}
