package com.example.projectshop.service;

import com.example.projectshop.domain.DiaChi;
import com.example.projectshop.dto.diachi.DiaChiRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface IDiaChiService {

    Page<DiaChi> findAll(
            Integer page,
            Integer pageSize,
            String sortField,
            Boolean isSortDesc,
            String keyword
    );

    Optional<DiaChi> findById(Integer id);

    DiaChi create(DiaChiRequest gioiThieuRequest);

    DiaChi update(Integer id, DiaChiRequest gioiThieuRequest);

    DiaChi delete(Integer id);


}
