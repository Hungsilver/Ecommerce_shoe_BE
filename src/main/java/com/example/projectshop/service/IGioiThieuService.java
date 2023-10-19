package com.example.projectshop.service;

import com.example.projectshop.dto.gioithieu.GioiThieuReponse;
import com.example.projectshop.dto.gioithieu.GioiThieuRequest;
import com.example.projectshop.dto.thuonghieu.ThuongHieuRequest;

import java.util.List;

public interface IGioiThieuService {

    List<GioiThieuReponse> getall();


    GioiThieuReponse create(GioiThieuRequest gioiThieuRequest);

    GioiThieuReponse update(GioiThieuRequest gioiThieuRequest);

    GioiThieuReponse detail(Integer id);

    void delete(Integer id);


}
