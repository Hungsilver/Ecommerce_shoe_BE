package com.example.projectshop.service;

import com.example.projectshop.domain.GioHangChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IGioHangCTService {

        Page<GioHangChiTiet> getall(Pageable pageable);

}
