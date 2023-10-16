package com.example.projectshop.service;

import com.example.projectshop.domain.Nguoidung;
import com.example.projectshop.dto.nguoidung.NguoiDungResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface INguoiDungService {
//  List<NguoiDungResponse> getall();
   List<Nguoidung> getall();



}
