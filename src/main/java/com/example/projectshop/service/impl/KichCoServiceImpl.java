package com.example.projectshop.service.impl;

import com.example.projectshop.domain.KichCo;
import com.example.projectshop.dto.kichco.KichCoRequest;
import com.example.projectshop.dto.kichco.KichCoResponse;
import com.example.projectshop.repository.KichCoRepository;
import com.example.projectshop.service.IKichCoService;
import com.example.projectshop.service.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KichCoServiceImpl implements IKichCoService {
    @Autowired
    private KichCoRepository kichCoRepository;

    @Override
    public List<KichCoResponse> getAll() {
        List<KichCoResponse> list = ObjectMapperUtils.mapAll(kichCoRepository.findAll(), KichCoResponse.class);
        return list;
    }

    @Override
    public KichCoResponse findById(Integer id) {
        return ObjectMapperUtils.map(kichCoRepository.findById(id).get(), KichCoResponse.class);
    }

    @Override
    public KichCoResponse create(KichCoRequest kichCoRequest) {
        KichCo entity = ObjectMapperUtils.map(kichCoRequest, KichCo.class);
        entity.setSize(kichCoRequest.getSize());
        entity = kichCoRepository.save(entity);
        KichCoResponse response = ObjectMapperUtils.map(entity, KichCoResponse.class);
        return response;
    }

    @Override
    public KichCoResponse update(KichCoRequest kichCoRequest, Integer id) {
        KichCo eDb = kichCoRepository.findById(id).get();
        KichCo entity = ObjectMapperUtils.map(kichCoRequest, KichCo.class);
        entity.setId(eDb.getId());
        entity.setSize(kichCoRequest.getSize());
        entity = kichCoRepository.save(entity);
        return ObjectMapperUtils.map(kichCoRepository.save(entity), KichCoResponse.class);
    }

    @Override
    public void delete(Integer id) {
        kichCoRepository.deleteById(id);
    }

    @Override
    public Page<KichCoResponse> findAllKichCo(String pageParam, String limitParam) {
        Integer page = pageParam == null ? 0 : Integer.valueOf(pageParam);
        Integer limit = limitParam == null ? 3 : Integer.valueOf(limitParam);
        Pageable pageable = PageRequest.of(page, limit);
        Page<KichCoResponse> list = ObjectMapperUtils.mapEntityPageIntoDtoPage(kichCoRepository.findAll(pageable), KichCoResponse.class);
        return list;    }
}
