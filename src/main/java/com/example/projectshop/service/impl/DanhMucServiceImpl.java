package com.example.projectshop.service.impl;

import com.example.projectshop.domain.DanhMuc;
import com.example.projectshop.dto.danhmuc.DanhMucRequest;
import com.example.projectshop.repository.DanhMucRepository;
import com.example.projectshop.service.IDanhMucSevice;
import com.example.projectshop.service.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DanhMucServiceImpl implements IDanhMucSevice {
    @Autowired
    private DanhMucRepository danhMucRepo;

    @Override
    public Page<DanhMuc> findAll(Integer page,
                                 Integer pageSize,
                                 String sortField,
                                 Boolean isSortDesc,
                                 String keyword) {
        Sort sort = Sort.by(isSortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, pageSize, sort);
        if (keyword != null && !keyword.isEmpty()) {
            return danhMucRepo.findAllByName(keyword, pageable);
        } else {
            return danhMucRepo.findAll(pageable);
        }
    }

    @Override
    public Optional<DanhMuc> findById(Integer id) {
        return danhMucRepo.findById(id);
    }

    @Override
    public DanhMuc create(DanhMucRequest danhMucRequest) {
        DanhMuc danhMuc = ObjectMapperUtils.map(danhMucRequest, DanhMuc.class);
        danhMuc.setId(null);
        return danhMucRepo.save(danhMuc);
    }

    @Override
    public DanhMuc update(Integer id, DanhMucRequest danhMucRequest) {
        DanhMuc danhMuc = ObjectMapperUtils.map(danhMucRequest, DanhMuc.class);
        danhMuc.setId(id);
        return danhMucRepo.save(danhMuc);
    }

    @Override
    public DanhMuc delete(Integer id) {
        Optional<DanhMuc> th = this.findById(id);
        if (th.isPresent()) {
            th.get().setTrangThai(0);
            return danhMucRepo.save(th.get());
        }
        return null;
    }

}
