package com.example.projectshop.service.impl;

import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.HoaDon;
import com.example.projectshop.domain.HoaDonChiTiet;
import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamRequest;
import com.example.projectshop.dto.hoadon.HoaDonChiTietRequest;
import com.example.projectshop.dto.hoadon.HoaDonRequest;
import com.example.projectshop.repository.HoaDonChiTietRepository;
import com.example.projectshop.repository.HoaDonRepository;
import com.example.projectshop.service.IHoaDonService;
import com.example.projectshop.service.IChiTietSanPhamService;
import com.example.projectshop.service.IKhachHangService;
import com.example.projectshop.service.IPhieuGiamGiaService;
import com.example.projectshop.service.ObjectMapperUtils;
import com.example.projectshop.utils.utils;
//import com.itextpdf.text.BaseColor;
//import com.itextpdf.text.Chunk;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.FontFactory;
//import com.itextpdf.text.PageSize;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.pdf.BaseFont;
//import com.itextpdf.text.pdf.PdfWriter;
//import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class HoaDonServiceImpl implements IHoaDonService {
    @Autowired
    private HoaDonRepository hoaDonRepo;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepo;

    @Autowired
    private IChiTietSanPhamService chiTietSanPhamService;

    @Autowired
    private IPhieuGiamGiaService phieuGiamGiaService;

    @Autowired
    private IKhachHangService khachHangService;

    // lấy ra ngày hiện tại
    private LocalDate curruntDate = LocalDate.now();

    @Override
    public Page<HoaDon> findAll(Integer status, String keyword, String sortField, Boolean isSortDesc, Integer page, Integer pageSize) {
        Sort sort = Sort.by(isSortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, pageSize, sort);
        return hoaDonRepo.findAll(status, keyword, pageable);
    }

    @Override
    public Optional<HoaDon> findById(Integer id) {
        return hoaDonRepo.findById(id);
    }

    @Override
    public HoaDon update(Integer id, HoaDonRequest hoaDonRequest) {
        Optional<HoaDon> resultFindById = this.findById(id);

        // start add hoadon
        HoaDon hoaDon = new HoaDon();
        hoaDon.setId(id);
        hoaDon.setTenKhachHang(hoaDonRequest.getTenKhachHang());
        hoaDon.setSoDienThoai(hoaDonRequest.getSoDienThoai());
        hoaDon.setDiaChi(hoaDonRequest.getDiaChi());
        hoaDon.setPhuongXa(hoaDonRequest.getPhuongXa());
        hoaDon.setQuanHuyen(hoaDonRequest.getQuanHuyen());
        hoaDon.setTinhThanh(hoaDonRequest.getTinhThanh());
        hoaDon.setNgayTao(resultFindById.get().getNgayTao());
        hoaDon.setNgayCapNhat(Date.valueOf(curruntDate));
        hoaDon.setTongTien(hoaDonRequest.getTongTien());
        hoaDon.setPhiVanChuyen(hoaDonRequest.getPhiVanChuyen());
        hoaDon.setPhuongThucThanhToan(hoaDonRequest.getPhuongThucThanhToan());
        hoaDon.setTrangThai(hoaDonRequest.getTrangThai());
        hoaDon.setPhieuGiamGia(phieuGiamGiaService.findById(hoaDonRequest.getPhieuGiamGia()));
        hoaDon.setKhachHang(khachHangService.findById(hoaDonRequest.getKhachHang()));
        hoaDon.setNhanVien(null); // nhân viên vẫn để null
        hoaDonRepo.save(hoaDon);
        return hoaDon;
    }

    @Override
    public HoaDon shopPayments(Integer idHoaDon, HoaDonRequest hoaDonRequest) {
        HoaDon hoaDon = this.findById(idHoaDon).get();

        // start add hoadon
        HoaDon hoaDon1 = HoaDon.builder()
                .id(idHoaDon)
                .maHoaDon(hoaDon.getMaHoaDon())
                .tenKhachHang(hoaDonRequest.getTenKhachHang())
                .soDienThoai(hoaDonRequest.getSoDienThoai())
                .diaChi(hoaDonRequest.getDiaChi())
                .phuongXa(hoaDonRequest.getPhuongXa())
                .quanHuyen(hoaDonRequest.getQuanHuyen())
                .tinhThanh(hoaDonRequest.getTinhThanh())
                .ngayTao(hoaDon.getNgayTao())
                .ngayCapNhat(Date.valueOf(curruntDate))
                .tongTien(hoaDonRequest.getTongTien())
                .tienGiam(hoaDonRequest.getTienGiam())
                .tongTienSauGiam(hoaDonRequest.getTongTienSauGiam())
                .phiVanChuyen(hoaDonRequest.getPhiVanChuyen())
                .phuongThucThanhToan(hoaDonRequest.getPhuongThucThanhToan())
                .trangThai(1)
                .phieuGiamGia(phieuGiamGiaService.findById(hoaDonRequest.getPhieuGiamGia()))
                .khachHang(khachHangService.findById(hoaDonRequest.getKhachHang()))
                .nhanVien(null)
                .build();

        hoaDonRepo.save(hoaDon1);
        // end add hoadon

        return hoaDon;
    }

    @Override
    public HoaDon shopCreateInvoice(Integer idNhanVien) {
        String maHoaDon = hoaDonRepo.getTop1ByIdMax().getMaHoaDon();
        String maHoaDonMoi = utils.renderCodeHoaDon(maHoaDon);
        // tạo hóa đơn chờ
        HoaDon hoaDon = new HoaDon();
        hoaDon.setNgayTao(Date.valueOf(curruntDate));
        hoaDon.setId(null);
        hoaDon.setMaHoaDon(maHoaDonMoi);
        hoaDon.setTrangThai(0);
        hoaDon.setNhanVien(null);
        return hoaDonRepo.save(hoaDon);
    }

    @Override
    public HoaDonChiTiet shopCreateInvoiceDetail(HoaDonChiTietRequest hoaDonChiTietRequest) {
        // thêm sản phẩm vào hóa đơn => tạo hóa đơn chi tiết
        //start insert hoadonchitiet
        HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
        hoaDonChiTiet.setId(null);
        hoaDonChiTiet.setSoLuong(hoaDonChiTietRequest.getSoLuong());
        hoaDonChiTiet.setDonGia(hoaDonChiTietRequest.getDonGia());
        hoaDonChiTiet.setHoaDon(this.findById(hoaDonChiTietRequest.getIdHoaDon()).get());
        hoaDonChiTiet.setChiTietSanPham(chiTietSanPhamService.findById(hoaDonChiTietRequest.getIdChiTietSanPham()).get());
        // end insert hoadonchitiet

        // start update chitietsanpham
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findById(hoaDonChiTietRequest.getIdChiTietSanPham()).get();
        chiTietSanPham.setSoLuong(chiTietSanPham.getSoLuong() - hoaDonChiTiet.getSoLuong());
        chiTietSanPhamService.update(hoaDonChiTietRequest.getIdChiTietSanPham(), ObjectMapperUtils.map(chiTietSanPham, ChiTietSanPhamRequest.class));
        return hoaDonChiTietRepo.save(hoaDonChiTiet);
    }

    @Override
    public HoaDonChiTiet shopUpdateInvoiceDetail(Integer id, Integer soLuong) {
        // cập nhật lại số lượng sản phẩm trong hóa đơn chi tiết
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepo.findById(id).get();
        BigDecimal giamoi = hoaDonChiTiet.getChiTietSanPham().getGiaBan().multiply(new BigDecimal(soLuong));
        hoaDonChiTiet.setSoLuong(soLuong);
        hoaDonChiTiet.setDonGia(giamoi);
        return hoaDonChiTietRepo.save(hoaDonChiTiet);
    }

    @Override
    public void shopDeleteInvoiceDetail(Integer id) {
        // xóa hóa đơn chi tiết
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepo.findById(id).get();

        // start update chitietsanpham
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findById(hoaDonChiTiet.getChiTietSanPham().getId()).get();
        System.out.println(hoaDonChiTiet.getSoLuong());
        chiTietSanPham.setSoLuong(chiTietSanPham.getSoLuong() + hoaDonChiTiet.getSoLuong());
        chiTietSanPhamService.update(chiTietSanPham.getId(), ObjectMapperUtils.map(chiTietSanPham, ChiTietSanPhamRequest.class));
        // end update chitietsanpham

        hoaDonChiTietRepo.delete(hoaDonChiTiet);
    }


    @Override
    public HoaDon onlinePayments(HoaDonRequest hoaDonRequest) {
        String maHoaDon = hoaDonRepo.getTop1ByIdMax().getMaHoaDon();
        String maHoaDonMoi = utils.renderCodeHoaDon(maHoaDon);
        // start add hoadon
        HoaDon hoaDon = HoaDon.builder()
                .id(null)
                .maHoaDon(maHoaDonMoi)
                .tenKhachHang(hoaDonRequest.getTenKhachHang())
                .soDienThoai(hoaDonRequest.getSoDienThoai())
                .diaChi(hoaDonRequest.getDiaChi())
                .phuongXa(hoaDonRequest.getPhuongXa())
                .quanHuyen(hoaDonRequest.getQuanHuyen())
                .tinhThanh(hoaDonRequest.getTinhThanh())
                .ngayTao(Date.valueOf(curruntDate))
                .ngayCapNhat(null)
                .tongTien(hoaDonRequest.getTongTien())
                .tienGiam(hoaDonRequest.getTienGiam())
                .tongTienSauGiam(hoaDonRequest.getTongTienSauGiam())
                .phiVanChuyen(hoaDonRequest.getPhiVanChuyen())
                .phuongThucThanhToan(hoaDonRequest.getPhuongThucThanhToan())
                .trangThai(1)
                .phieuGiamGia(phieuGiamGiaService.findById(hoaDonRequest.getPhieuGiamGia()))
                .khachHang(khachHangService.findById(hoaDonRequest.getKhachHang()))
                .nhanVien(null)
                .build();
        hoaDonRepo.save(hoaDon);
        // end add hoadon

        for (HoaDonChiTietRequest x : hoaDonRequest.getHoaDonChiTietRequests()) {
            // start add hoadonchitiet
            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
            hoaDonChiTiet.setId(null);
            hoaDonChiTiet.setDonGia(x.getDonGia());
            hoaDonChiTiet.setSoLuong(x.getSoLuong());
            hoaDonChiTiet.setHoaDon(hoaDon);
            hoaDonChiTiet.setChiTietSanPham(chiTietSanPhamService.findById(x.getIdChiTietSanPham()).get());
            hoaDonChiTietRepo.save(hoaDonChiTiet);
            // and add hoadonchitiet

            // start update chitietsanpham
            ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findById(x.getIdChiTietSanPham()).get();
            chiTietSanPham.setSoLuong(chiTietSanPham.getSoLuong() - x.getSoLuong());
            chiTietSanPhamService.update(x.getIdChiTietSanPham(), ObjectMapperUtils.map(chiTietSanPham, ChiTietSanPhamRequest.class));
            // and update chitietsanpham

            // start update giohangchitiet
            //
        }
        return hoaDon;
    }


    @Override
    public HoaDon choVanChuyen(Integer id) {
        HoaDon hoaDon = hoaDonRepo.findById(id).get();
        hoaDon.setTrangThai(2);
        return hoaDonRepo.save(hoaDon);
    }

    @Override
    public HoaDon dangGiao(Integer id) {
        HoaDon hoaDon = hoaDonRepo.findById(id).get();
        hoaDon.setTrangThai(3);
        return hoaDonRepo.save(hoaDon);
    }

    @Override
    public HoaDon daGiao(Integer id) {
        HoaDon hoaDon = hoaDonRepo.findById(id).get();
        hoaDon.setTrangThai(4);
        return hoaDonRepo.save(hoaDon);
    }

    @Override
    public HoaDon daHuy(Integer id) {
        HoaDon hoaDon = hoaDonRepo.findById(id).get();
        hoaDon.setTrangThai(5);
        return hoaDonRepo.save(hoaDon);
    }

    @Override
    public HoaDon traHang(Integer id) {
        HoaDon hoaDon = hoaDonRepo.findById(id).get();
        hoaDon.setTrangThai(6);
        return hoaDonRepo.save(hoaDon);
    }

//    @Override
//    public void exportPDF(HttpServletResponse response) throws IOException, DocumentException {
//        HoaDon hoaDon = hoaDonRepo.getTop1ByIdMax();
//
//        Document document = new Document(PageSize.A4);
//        PdfWriter writer = PdfWriter.getInstance(document,response.getOutputStream());
//
//        document.open();
//
//        Font font = new Font(  BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED)
//        );
//        document.add(new Paragraph("Xin chào, đây là một ví dụ xuất PDF bằng iText trong Spring Boot.",font));
//        document.close();
//
//    }

}
