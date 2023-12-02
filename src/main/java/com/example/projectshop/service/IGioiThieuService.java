package com.example.projectshop.service;

import com.example.projectshop.domain.DanhMuc;
import com.example.projectshop.domain.GioiThieu;
import com.example.projectshop.dto.gioithieu.ExportExcelGioiThieu;
import com.example.projectshop.dto.gioithieu.ImportExcelGioiThieu;
import com.example.projectshop.dto.gioithieu.GioiThieuRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IGioiThieuService {
    Page<GioiThieu> findAll(
            Integer page,
            Integer pageSize,
            String sortField,
            Boolean isSortDesc,
            String keyword
    );

    Optional<GioiThieu> findById(Integer id);

    GioiThieu findByName(String name);

    List<ImportExcelGioiThieu> importExcel(List<ImportExcelGioiThieu> importExcelGioiThieuses);

    List<ExportExcelGioiThieu> exportExcel();

    GioiThieu create(GioiThieuRequest gioiThieuRequest);

    GioiThieu update(Integer id, GioiThieuRequest gioiThieuRequest);

    GioiThieu delete(Integer id);
}
