package com.example.projectshop.service;

import com.example.projectshop.dto.kichco.KichCoRequest;
import com.example.projectshop.dto.kichco.KichCoResponse;
import com.example.projectshop.dto.mausac.MauSacResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IKichCoService {
    List<KichCoResponse> getAll();

    KichCoResponse findById(Integer id);

    KichCoResponse create(KichCoRequest kichCoRequest);

    KichCoResponse update(KichCoRequest kichCoRequest, Integer id);

    void delete(Integer id);

    Page<KichCoResponse> findAllKichCo(String pageParam, String limitParam);

}
