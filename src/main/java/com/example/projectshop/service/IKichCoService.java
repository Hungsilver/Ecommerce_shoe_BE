package com.example.projectshop.service;

import com.example.projectshop.domain.KichCo;
import com.example.projectshop.domain.MauSac;
import com.example.projectshop.dto.kichco.KichCoRequest;
import com.example.projectshop.dto.kichco.KichCoResponse;
import com.example.projectshop.dto.mausac.MauSacResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IKichCoService {
    Page<KichCo> getAll(Pageable pageable);


    Optional<KichCo> findById(Integer id);

    Page<KichCo> findAllByName(String size,Pageable pageable);

    KichCo create(KichCo kc);

    KichCo update(KichCo  kc , Integer id);

    KichCo delete(Integer id);

}
