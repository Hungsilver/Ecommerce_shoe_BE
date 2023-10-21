package com.example.projectshop.service;
import com.example.projectshop.dto.mausac.MauSacResponse;
import com.example.projectshop.dto.xuatxu.XuatXuRequest;
import com.example.projectshop.dto.xuatxu.XuatXuResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IXuatXuService {
    List<XuatXuResponse> getAll();

    XuatXuResponse findById(Integer id);

    XuatXuResponse create(XuatXuRequest xuatXuRequest);

    XuatXuResponse update(XuatXuRequest xuatXuRequest, Integer id);

    void delete(Integer id);

    Page<XuatXuResponse> findAllXuatXu(String pageParam, String limitParam);

}
