package com.example.projectshop.service.impl;

import com.example.projectshop.domain.DiaChi;
import com.example.projectshop.dto.diachi.DiaChiRequest;
import com.example.projectshop.dto.diachi.DiaChiResponse;
import com.example.projectshop.repository.DiaChiRepository;
import com.example.projectshop.service.IDiaChiService;
import com.example.projectshop.service.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiaChiServiceImpl implements IDiaChiService {

    @Autowired
    private DiaChiRepository diaChiRepo;

    @Override
    public List<DiaChiResponse> findAll() {
        List<DiaChiResponse> list = ObjectMapperUtils.mapAll(diaChiRepo.findAll(), DiaChiResponse.class);
        return list;
    }

    @Override
    public Page<DiaChiResponse> getAll(String pageParam, String limitParam) {
        Integer page = pageParam == null ? 0 : Integer.valueOf(pageParam);
        Integer limit = limitParam == null ? 2 : Integer.valueOf(limitParam);
        Pageable pageable = PageRequest.of(page, limit);
        Page<DiaChiResponse> list = ObjectMapperUtils.mapEntityPageIntoDtoPage(diaChiRepo.findAll(pageable), DiaChiResponse.class);
        return list;
    }

    @Override
    public DiaChiResponse getOne(Integer id) {
        return ObjectMapperUtils.map(diaChiRepo.findById(id).get(),DiaChiResponse.class);
    }

    @Override
    public DiaChiResponse create(DiaChiRequest diaChiRequest) {
        DiaChi entity = new DiaChi();
        entity.setId(null);
        entity.setDiaChi(diaChiRequest.getDiaChi());
        entity.setPhuongXa(diaChiRequest.getPhuongXa());
        entity.setQuanHuyen(diaChiRequest.getQuanHuyen());
        entity.setTinhThanh(diaChiRequest.getTinhThanh());
        entity.setTrangThai(diaChiRequest.getTrangThai());
        DiaChiResponse entityRs = ObjectMapperUtils.map(diaChiRepo.save(entity), DiaChiResponse.class);
        return entityRs;
    }

    @Override
    public DiaChiResponse update(Integer id, DiaChiRequest diaChiRequest) {
        DiaChi entity = new DiaChi();
        entity.setId(id);
        entity.setDiaChi(diaChiRequest.getDiaChi());
        entity.setPhuongXa(diaChiRequest.getPhuongXa());
        entity.setQuanHuyen(diaChiRequest.getQuanHuyen());
        entity.setTinhThanh(diaChiRequest.getTinhThanh());
        entity.setTrangThai(diaChiRequest.getTrangThai());
        DiaChiResponse entityRs = ObjectMapperUtils.map(diaChiRepo.save(entity), DiaChiResponse.class);
        return entityRs;
    }

    @Override
    public void delete(Integer id) {diaChiRepo.deleteById(id);
    }

}
