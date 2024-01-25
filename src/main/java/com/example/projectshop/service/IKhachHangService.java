package com.example.projectshop.service;

import com.example.projectshop.domain.KhachHang;
import com.example.projectshop.dto.danhmuc.ExcelDanhMuc;
import com.example.projectshop.dto.khachhang.ExportExcelKhachHang;
import com.example.projectshop.dto.khachhang.KhachHangRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IKhachHangService {

    Page<KhachHang> findAll(
            Integer page,
            Integer pageSize,
            String sortField,
            Boolean isSortDesc,
            String keyword
    );

    KhachHang findById(Integer id);

    List<ExportExcelKhachHang> exportExcel();

    KhachHang create(KhachHangRequest khachHangRequest);

    KhachHang update(Integer id, KhachHangRequest khachHangRequest);

    KhachHang delete(Integer id);

    KhachHang registerKhachHang(KhachHangRequest khachHangRequest);

    KhachHang loginKhachHang(String email, String matKhau);

    KhachHang findByEmail(String email);


    KhachHang findBySdt(String sdt);


    KhachHang updateKHv1(Integer id,KhachHang kh);


    boolean isSoDienThoaiExists(String soDienThoai);

    boolean isEmailExists(String email);

}
