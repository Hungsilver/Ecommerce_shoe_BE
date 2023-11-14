package com.example.projectshop.service.impl;

import com.example.projectshop.domain.ThuongHieu;
import com.example.projectshop.domain.Xuatxu;
import com.example.projectshop.dto.thuonghieu.ThuongHieuRequest;
import com.example.projectshop.dto.thuonghieu.ThuongHieuResponse;
import com.example.projectshop.repository.ThuongHieuRepository;
import com.example.projectshop.service.IThuongHieuService;
import com.example.projectshop.service.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ThuongHieuServiceImpl implements IThuongHieuService {

    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    @Override
    public Page<ThuongHieu> findAll(Integer page,
                                    Integer pageSize,
                                    String sortField,
                                    Boolean isSortDesc,
                                    String keyword) {
        Sort sort = Sort.by(isSortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, pageSize, sort);
        if (keyword != null && !keyword.isEmpty()) {
            return thuongHieuRepository.findAllByName(keyword, pageable);
        } else {
            return thuongHieuRepository.findAll(pageable);
        }
    }

    @Override
    public ThuongHieu findById(Integer id) {
        if (id != null) {
            return thuongHieuRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public ThuongHieu create(ThuongHieuRequest thuongHieuRequest) {
        ThuongHieu thuongHieu = ObjectMapperUtils.map(thuongHieuRequest, ThuongHieu.class);
        thuongHieu.setId(null);
        return thuongHieuRepository.save(thuongHieu);
    }

    @Override
    public ThuongHieu update(Integer id,ThuongHieuRequest thuongHieuRequest) {
        ThuongHieu thuongHieu = ObjectMapperUtils.map(thuongHieuRequest, ThuongHieu.class);
        thuongHieu.setId(id);
        return thuongHieuRepository.save(thuongHieu);
    }

    @Override
    public ThuongHieu delete(Integer id) {
        Optional<ThuongHieu> thuongHieu = thuongHieuRepository.findById(id);
        if (thuongHieu.isPresent()){
            thuongHieu.get().setTrangThai(0);
            return thuongHieuRepository.save(thuongHieu.get());
        }
        return null;
    }

}
