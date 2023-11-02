package com.example.projectshop.service;

import com.example.projectshop.domain.MauSac;
import com.example.projectshop.dto.mausac.MauSacRequest;
import com.example.projectshop.dto.mausac.MauSacResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IMauSacService {
    Page<MauSac> getAll(Pageable pageable);

    Optional<MauSac> findById(Integer id);

    Page<MauSac> findAllByName(String name,Pageable pageable);

    MauSac create(MauSac mauSac);

    MauSac update(MauSac  mauSac , Integer id);

    MauSac delete(Integer id);


}
