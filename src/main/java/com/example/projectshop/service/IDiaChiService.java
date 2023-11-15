package com.example.projectshop.service;

import com.example.projectshop.domain.DiaChi;
import com.example.projectshop.domain.GioiThieu;
import com.example.projectshop.dto.diachi.DiaChiRequest;
import com.example.projectshop.dto.diachi.DiaChiResponse;
import com.example.projectshop.dto.gioithieu.GioiThieuRequest;
import org.springframework.data.domain.Page;

import java.util.List;
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
