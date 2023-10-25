package com.example.projectshop.service.impl;

import com.example.projectshop.domain.GioiThieu;
import com.example.projectshop.dto.gioithieu.GioiThieuRequest;
import com.example.projectshop.repository.GioiThieuRepository;
import com.example.projectshop.service.IGioiThieuService;
import com.example.projectshop.service.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GioiThieuServiceImpl implements IGioiThieuService {
    @Autowired
    private GioiThieuRepository gioiThieuRepo;

    @Override
    public Page<GioiThieu> findAll(Integer page,
                                   Integer pageSize,
                                   String sortField,
                                   Boolean isSortDesc,
                                   String keyword) {
        Sort sort = Sort.by(isSortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, pageSize, sort);
        if (keyword != null && !keyword.isEmpty()) {
            return gioiThieuRepo.findAllByName(keyword, pageable);
        } else {
            return gioiThieuRepo.findAll(pageable);
        }
    }

    @Override
    public Optional<GioiThieu> findById(Integer id) {
        return gioiThieuRepo.findById(id);
    }

    @Override
    public GioiThieu create(GioiThieuRequest GioiThieuRequest) {
        GioiThieu gioiThieu = ObjectMapperUtils.map(GioiThieuRequest, GioiThieu.class);
        gioiThieu.setId(null);
        return gioiThieuRepo.save(gioiThieu);
    }

    @Override
    public GioiThieu update(Integer id, GioiThieuRequest GioiThieuRequest) {
        GioiThieu gioiThieu = ObjectMapperUtils.map(GioiThieuRequest, GioiThieu.class);
        gioiThieu.setId(id);
        return gioiThieuRepo.save(gioiThieu);
    }

    @Override
    public GioiThieu delete(Integer id) {
        Optional<GioiThieu> gioiThieu = this.findById(id);
        if (gioiThieu.isPresent()) {
            gioiThieu.get().setTrangThai(0);
            return gioiThieuRepo.save(gioiThieu.get());
        }
        return null;
    }
}
