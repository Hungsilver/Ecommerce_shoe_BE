package com.example.projectshop.service.impl;

import com.example.projectshop.domain.Nguoidung;
import com.example.projectshop.dto.nguoidung.NguoiDungResponse;
import com.example.projectshop.repository.NguoiDungRepository;
import com.example.projectshop.service.INguoiDungService;
import com.example.projectshop.service.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NguoiDungServiceImpl implements INguoiDungService {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Override
    public List<Nguoidung> getall() {

        return nguoiDungRepository.findAll();
    }

}
