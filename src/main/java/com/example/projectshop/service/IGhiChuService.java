package com.example.projectshop.service;

import com.example.projectshop.domain.GhiChu;
import org.springframework.data.domain.Page;

public interface IGhiChuService {

    Page<GhiChu> findByIdHoaDon(Integer page,
                                Integer pageSize,
                                Integer idHoaDon);

    GhiChu add(Integer idHoaDon,String ghiChu,Integer trangThai);
}
