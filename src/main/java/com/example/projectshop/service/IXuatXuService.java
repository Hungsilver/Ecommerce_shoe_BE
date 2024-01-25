package com.example.projectshop.service;
import com.example.projectshop.domain.Xuatxu;
import com.example.projectshop.dto.danhmuc.ExcelDanhMuc;
import com.example.projectshop.dto.xuatxu.ExcelXuatXu;
import com.example.projectshop.dto.xuatxu.XuatXuRequest;
import com.example.projectshop.dto.xuatxu.XuatXuResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IXuatXuService {
    List<XuatXuResponse> getAll();

    Page<XuatXuResponse> findAllXuatXu(String pageParam, String limitParam);

    XuatXuResponse findById(Integer id);

    Xuatxu findByName(String name);

    List<ExcelXuatXu> importExcel(List<ExcelXuatXu> excelXuatXus);

    List<ExcelXuatXu> exportExcel();

    XuatXuResponse create(XuatXuRequest xuatXuRequest);

    XuatXuResponse update(XuatXuRequest xuatXuRequest, Integer id);

    void delete(Integer id);


}
