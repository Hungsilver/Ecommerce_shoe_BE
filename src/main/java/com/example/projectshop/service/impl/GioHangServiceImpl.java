package com.example.projectshop.service.impl;

import com.example.projectshop.domain.GioHang;
import com.example.projectshop.repository.GioHangRepository;
import com.example.projectshop.service.IGioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GioHangServiceImpl implements IGioHangService {
    @Autowired
    private GioHangRepository gioHangRepository;

    @Override
    public GioHang add(GioHang gh) {
        return  gioHangRepository.save(gh) ;
    }
}
