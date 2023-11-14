package com.example.projectshop.service;

import com.example.projectshop.domain.ThuongHieu;
import com.example.projectshop.dto.thuonghieu.ThuongHieuRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface IThuongHieuService {
    Page<ThuongHieu> findAll(
            Integer page,
            Integer pageSize,
            String sortField,
            Boolean isSortDesc,
            String keyword
    );

    ThuongHieu findById(Integer id);

    ThuongHieu create(ThuongHieuRequest thuongHieuRequest);

    ThuongHieu update(Integer id, ThuongHieuRequest thuongHieuRequest);

    ThuongHieu delete(Integer id);
}
