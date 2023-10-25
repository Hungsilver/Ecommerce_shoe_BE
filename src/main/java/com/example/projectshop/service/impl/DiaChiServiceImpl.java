package com.example.projectshop.service.impl;

import com.example.projectshop.domain.DiaChi;
import com.example.projectshop.dto.diachi.DiaChiRequest;
import com.example.projectshop.repository.DiaChiRepository;
import com.example.projectshop.service.IDiaChiService;
import com.example.projectshop.service.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DiaChiServiceImpl implements IDiaChiService {

    @Autowired
    private DiaChiRepository diaChiRepo;

    @Override
    public Page<DiaChi> findAll(Integer page,
                                Integer pageSize,
                                String sortField,
                                Boolean isSortDesc,
                                String keyword) {
        Sort sort = Sort.by(isSortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, pageSize, sort);
        if (keyword != null && !keyword.isEmpty()) {
            return diaChiRepo.findAllByDiaChi(keyword, pageable);
        } else {
            return diaChiRepo.findAll(pageable);
        }
    }

    @Override
    public Optional<DiaChi> findById(Integer id) {
        return diaChiRepo.findById(id);
    }

    @Override
    public DiaChi create(DiaChiRequest diaChiRequest) {
        DiaChi diaChi = ObjectMapperUtils.map(diaChiRequest, DiaChi.class);
        diaChi.setId(null);
        return diaChiRepo.save(diaChi);
    }

    @Override
    public DiaChi update(Integer id, DiaChiRequest DiaChiRequest) {
        DiaChi diaChi = ObjectMapperUtils.map(DiaChiRequest, DiaChi.class);
        diaChi.setId(id);
        return diaChiRepo.save(diaChi);
    }

    @Override
    public DiaChi delete(Integer id) {
        Optional<DiaChi> diaChi = this.findById(id);
        if (diaChi.isPresent()) {
            diaChi.get().setTrangThai(0);
            return diaChiRepo.save(diaChi.get());
        }
        return null;
    }

}
