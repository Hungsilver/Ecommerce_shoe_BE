package com.example.projectshop.service;

import com.example.projectshop.domain.ChucVu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.Optional;

public interface IChucVuService {
    Page<ChucVu> getAll(Pageable pageable);

    Optional<ChucVu> findById(Integer id);

    Page<ChucVu> findAllByName(String name,Pageable pageable);

    ChucVu create(ChucVu chucVu);
    ChucVu update(ChucVu chucVu,Integer id);
    ChucVu delete(Integer id);
}
