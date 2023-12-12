package com.example.projectshop.service;

import com.example.projectshop.domain.HoaDon;
import com.example.projectshop.domain.HoaDonChiTiet;
import com.example.projectshop.dto.hoadon.HoaDonChiTietRequest;
import com.example.projectshop.dto.hoadon.HoaDonRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface IHoaDonService {

    Page<HoaDon> findAll(Integer status,
                         String keyword,
                         String sortField,
                         Boolean isSortDesc,
                         Integer page,
                         Integer pageSize);

    HoaDon findById(Integer id);

    HoaDon findByMa(String ma);

    HoaDon shopPayments(Integer idHoaDon, HoaDonRequest hoaDonRequest);

    HoaDon shopCreateInvoice(Integer idNhanVien);

    HoaDonChiTiet shopCreateInvoiceDetail(HoaDonChiTietRequest hoaDonChiTietRequest);

    HoaDonChiTiet shopUpdateInvoiceDetail(Integer idHDCT,HoaDonChiTietRequest hoaDonChiTietRequest);

    void shopDeleteInvoiceDetail(Integer id);

    HoaDon onlinePayment(HoaDonRequest hoaDonRequest);

    void vnPayment(String maHoaDon);

    String vnPayService(HoaDonRequest hoaDonRequest) throws UnsupportedEncodingException;

    HoaDon update(Integer id, HoaDonRequest hoaDonRequest);

    void delete(String maHoaDon);

    HoaDon updateStatus(Integer id, Integer status);

    void exportPDF(HttpServletResponse response, Integer id) throws IOException;

    HoaDon CreateInvoice();
}
