package com.example.projectshop.service;

import com.example.projectshop.domain.GioiThieu;
import com.example.projectshop.domain.ThuongHieu;
import com.example.projectshop.dto.gioithieu.GioiThieuRequest;
import com.example.projectshop.dto.thuonghieu.ThuongHieuRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface IGioiThieuService {
    Page<GioiThieu> findAll(
            Integer page,
            Integer pageSize,
            String sortField,
            Boolean isSortDesc,
            String keyword
    );

    Optional<GioiThieu> findById(Integer id);

    GioiThieu create(GioiThieuRequest gioiThieuRequest);

    GioiThieu update(Integer id, GioiThieuRequest gioiThieuRequest);

    GioiThieu delete(Integer id);
}
