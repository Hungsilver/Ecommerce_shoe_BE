package com.example.projectshop.service.impl;

import com.example.projectshop.domain.Mausac;
import com.example.projectshop.dto.mausac.MauSacRequest;
import com.example.projectshop.dto.mausac.MauSacResponse;
import com.example.projectshop.repository.MauSacRepository;
import com.example.projectshop.service.IMauSacService;
import com.example.projectshop.service.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MauSacSeviceImpl implements IMauSacService {

    @Autowired
    private MauSacRepository mauSacRepository;

    @Override
    public List<MauSacResponse> getAll() {
        List<MauSacResponse> list= ObjectMapperUtils.mapAll(mauSacRepository.findAll(),MauSacResponse.class);
        return list;
    }

    @Override
    public MauSacResponse findById(Integer id) {
        return ObjectMapperUtils.map(mauSacRepository.findById(id).get(), MauSacResponse.class);
    }

    @Override
    public MauSacResponse create(MauSacRequest mauSacRequest) {
        Mausac entity = ObjectMapperUtils.map(mauSacRequest, Mausac.class);
        entity.setTen(mauSacRequest.getTen());
        entity = mauSacRepository.save(entity);
        MauSacResponse response = ObjectMapperUtils.map(entity, MauSacResponse.class);
        return response;    }

    @Override
    public MauSacResponse update(MauSacRequest mauSacRequest, Integer id) {
        Mausac eDb = mauSacRepository.findById(id).get();
        Mausac entity = ObjectMapperUtils.map(mauSacRequest, Mausac.class);
        entity.setId(eDb.getId());
        entity.setTen(mauSacRequest.getTen());
        entity = mauSacRepository.save(entity);
        return ObjectMapperUtils.map(mauSacRepository.save(entity), MauSacResponse.class);
    }

    @Override
    public void delete(Integer id) {
        mauSacRepository.deleteById(id);
    }

    @Override
    public Page<MauSacResponse> findAllMauSac(String pageParam, String limitParam) {
        Integer page = pageParam == null ? 0 : Integer.valueOf(pageParam);
        Integer limit = limitParam == null ? 3 : Integer.valueOf(limitParam);
        Pageable pageable = PageRequest.of(page, limit);
        Page<MauSacResponse> list = ObjectMapperUtils.mapEntityPageIntoDtoPage(mauSacRepository.findAll(pageable), MauSacResponse.class);
        return list;
    }


}
