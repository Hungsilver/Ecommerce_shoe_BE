package com.example.projectshop.service;

import com.example.projectshop.domain.Mausac;
import com.example.projectshop.dto.mausac.MauSacRequest;
import com.example.projectshop.dto.mausac.MauSacResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IMauSacService {
    List<MauSacResponse> getAll();

    MauSacResponse findById(Integer id);

    MauSacResponse create(MauSacRequest mauSacRequest);

    MauSacResponse update(MauSacRequest mauSacRequest, Integer id);

    void delete(Integer id);

    Page<MauSacResponse> findAllMauSac(String pageParam, String limitParam);

}
