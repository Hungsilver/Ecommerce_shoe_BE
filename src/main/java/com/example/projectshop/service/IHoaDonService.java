package com.example.projectshop.service;

import com.example.projectshop.domain.HoaDon;
import com.example.projectshop.domain.HoaDonChiTiet;
import com.example.projectshop.dto.hoadon.HoaDonChiTietRequest;
import com.example.projectshop.dto.hoadon.HoaDonRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface IHoaDonService {

    Page<HoaDon> findAll(Integer status,
                         String keyword,
                         String sortField,
                         Boolean isSortDesc,
                         Integer page,
                         Integer pageSize);

    HoaDon findById(Integer id);

    HoaDonChiTiet findByIdHDCT(Integer id);

    HoaDon findByMa(String ma);

    List<HoaDon> findByIdKhachHangAnhTrangThai(Integer trangThai);

    HoaDon shopPayments(Integer idHoaDon, HoaDonRequest hoaDonRequest) throws UnsupportedEncodingException;

    String vnPayShopService(Integer idHoaDon, HoaDonRequest hoaDonRequest) throws UnsupportedEncodingException;

    HoaDon shopCreateInvoice(Integer idNhanVien);

    HoaDonChiTiet shopCreateInvoiceDetail(HoaDonChiTietRequest hoaDonChiTietRequest);

    HoaDonChiTiet shopUpdateInvoiceDetail(Integer idHDCT,HoaDonChiTietRequest hoaDonChiTietRequest);

    void shopDeleteInvoiceDetail(Integer id);

    HoaDon onlinePayment(HoaDonRequest hoaDonRequest);

    void vnPayment(String maHoaDon);

    String vnPayService(HoaDonRequest hoaDonRequest) throws UnsupportedEncodingException;

    HoaDon update(Integer id, HoaDonRequest hoaDonRequest);

    HoaDon updateInvoice(HoaDon hoaDon);

    void delete(String maHoaDon);

    HoaDon huyDonHang(Integer id);

    HoaDonChiTiet deleteHdct(Integer idHdct);

    HoaDon updateStatus(Integer id, Integer status);

    void exportPDF(HttpServletResponse response, Integer id) throws IOException;

    HoaDon CreateInvoice();

    Optional<HoaDon> findByInvoiceNew();
}
