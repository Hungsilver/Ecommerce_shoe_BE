package com.example.projectshop.service;

import com.example.projectshop.domain.HoaDon;
import com.example.projectshop.domain.HoaDonChiTiet;
import com.example.projectshop.dto.hoadon.HoaDonChiTietRequest;
import com.example.projectshop.dto.hoadon.HoaDonRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

public interface IHoaDonService {

    Page<HoaDon> findAll(Integer status,
                         String keyword,
                         String sortField,
                         Boolean isSortDesc,
                         Integer page,
                         Integer pageSize);

    Optional<HoaDon> findById(Integer id);

    HoaDon shopPayments(Integer idHoaDon, HoaDonRequest hoaDonRequest);

    HoaDon shopCreateInvoice(Integer idNhanVien);

    HoaDonChiTiet shopCreateInvoiceDetail(HoaDonChiTietRequest hoaDonChiTietRequest);

    HoaDonChiTiet shopUpdateInvoiceDetail(Integer id, Integer soLuong);

    void shopDeleteInvoiceDetail(Integer id);

    HoaDon onlinePayments(HoaDonRequest hoaDonRequest);

    HoaDon update(Integer id, HoaDonRequest hoaDonRequest);

    HoaDon choVanChuyen(Integer id);

    HoaDon dangGiao(Integer id);

    HoaDon daGiao(Integer id);

    HoaDon daHuy(Integer id);

    HoaDon traHang(Integer id);

    void exportPDF(HttpServletResponse response, Integer id) throws IOException;


}
