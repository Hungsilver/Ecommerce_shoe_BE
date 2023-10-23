package com.example.projectshop.service;

import com.example.projectshop.dto.diachi.DiaChiRequest;
import com.example.projectshop.dto.diachi.DiaChiResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IDiaChiService {

    List<DiaChiResponse> findAll();

    Page<DiaChiResponse> getAll(String pageParam, String limitParam);

    DiaChiResponse getOne(Integer id);

    DiaChiResponse create(DiaChiRequest diaChiRequest);

    DiaChiResponse update(Integer id, DiaChiRequest diaChiRequest);

    void delete(Integer id);


}
