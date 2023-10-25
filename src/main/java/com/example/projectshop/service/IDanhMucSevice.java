package com.example.projectshop.service;

import com.example.projectshop.domain.DanhMuc;
import com.example.projectshop.dto.danhmuc.DanhMucRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface IDanhMucSevice {
    Page<DanhMuc> findAll(
            Integer page,
            Integer pageSize,
            String sortField,
            Boolean isSortDesc,
            String keyword
    );

    Optional<DanhMuc> findById(Integer id);

    DanhMuc create(DanhMucRequest danhMucRequest);

    DanhMuc update(Integer id, DanhMucRequest danhMucRequest);

    DanhMuc delete(Integer id);
}
