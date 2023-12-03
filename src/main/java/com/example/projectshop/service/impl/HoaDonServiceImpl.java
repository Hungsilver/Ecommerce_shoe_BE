package com.example.projectshop.service.impl;

import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.HoaDon;
import com.example.projectshop.domain.HoaDonChiTiet;
import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamRequest;
import com.example.projectshop.dto.hoadon.HoaDonChiTietRequest;
import com.example.projectshop.dto.hoadon.HoaDonRequest;
import com.example.projectshop.repository.ChiTietSanPhamRepository;
import com.example.projectshop.repository.HoaDonChiTietRepository;
import com.example.projectshop.repository.HoaDonRepository;
import com.example.projectshop.service.IHoaDonService;
import com.example.projectshop.service.IChiTietSanPhamService;
import com.example.projectshop.service.IKhachHangService;
import com.example.projectshop.service.IPhieuGiamGiaService;
import com.example.projectshop.service.ObjectMapperUtils;
import com.example.projectshop.utils.QRCodeGenerator;
import com.example.projectshop.utils.utils;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
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

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepo;

    // lấy ra ngày hiện tại
    private LocalDate curruntDate = LocalDate.now();

    @Override
    public Page<HoaDon> findAll(Integer status, String keyword, String sortField, Boolean isSortDesc, Integer page, Integer pageSize) {
        // xóa hóa đơn với trạng thái = 0 và ngày tạo nhỏ hơn ngày hiện tại
        for (HoaDon x : hoaDonRepo.findByTrangThai(0)) {
            // kiểm tra ngày tạo mà nhỏ hơn ngày hiện tại
            if (x.getNgayTao().before(Date.valueOf(curruntDate))) {
                // xóa hóa đơn chi tiết với id hóa đơn
                for (HoaDonChiTiet hdct : hoaDonChiTietRepo.findByIdHoaDon(x.getId())) {
                    // cập nhật lại số lượng sản phẩm
                    ChiTietSanPham chiTietSanPham = ChiTietSanPham.builder()
                            .id(hdct.getChiTietSanPham().getId())
                            .ma(hdct.getChiTietSanPham().getMa())
                            .soLuong(hdct.getChiTietSanPham().getSoLuong() + hdct.getSoLuong())
                            .giaBan(hdct.getChiTietSanPham().getGiaBan())
                            .ngayTao(hdct.getChiTietSanPham().getNgayTao())
                            .ngayCapNhat(hdct.getChiTietSanPham().getNgayCapNhat())
                            .trangThai(hdct.getChiTietSanPham().getTrangThai())
                            .mauSac(hdct.getChiTietSanPham().getMauSac())
                            .kichCo(hdct.getChiTietSanPham().getKichCo())
                            .chatLieuGiay(hdct.getChiTietSanPham().getChatLieuGiay())
                            .chatLieuDeGiay(hdct.getChiTietSanPham().getChatLieuDeGiay())
                            .sanPham(hdct.getChiTietSanPham().getSanPham())
                            .build();
                    chiTietSanPhamRepo.save(chiTietSanPham);
                    hoaDonChiTietRepo.delete(hdct);
                }
                hoaDonRepo.delete(x);
            }
        }

        Sort sort = Sort.by(isSortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, pageSize, sort);
        return hoaDonRepo.findAll(status, keyword, pageable);
    }

    @Override
    public Optional<HoaDon> findById(Integer id) {
        return hoaDonRepo.findById(id);
    }

    @Override
    public Optional<HoaDon> findByMa(String ma) {
        return hoaDonRepo.findByMa(ma);
    }

    @Override // update hóa đơn
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

    @Override // thanh toán tại quầy
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

    @Override // tạo hóa đơn chờ
    public HoaDon shopCreateInvoice(Integer idNhanVien) {
        if (hoaDonRepo.getTop1ByIdMax() == null) {
            // tạo hóa đơn chờ
            HoaDon hoaDon = new HoaDon();
            hoaDon.setNgayTao(Date.valueOf(curruntDate));
            hoaDon.setId(null);
            hoaDon.setMaHoaDon(utils.renderCodeHoaDon());
            hoaDon.setTrangThai(0);
            hoaDon.setNhanVien(null);
            return hoaDonRepo.save(hoaDon);
        }
        // tạo hóa đơn chờ
        HoaDon hoaDon = new HoaDon();
        hoaDon.setNgayTao(Date.valueOf(curruntDate));
        hoaDon.setId(null);
        hoaDon.setMaHoaDon(utils.renderCodeHoaDon());
        hoaDon.setTrangThai(0);
        hoaDon.setNhanVien(null);
        return hoaDonRepo.save(hoaDon);
    }

    @Override // tạo mới hóa đơn chi tiết
    public HoaDonChiTiet shopCreateInvoiceDetail(HoaDonChiTietRequest hoaDonChiTietRequest) {
        // thêm sản phẩm vào hóa đơn => tạo hóa đơn chi tiết

        // lấy hoadonchitiet by idsanpham, idctsp
        HoaDonChiTiet hoaDonChiTiet1 = hoaDonChiTietRepo.findByIdHoaDonAndIdCTSP(hoaDonChiTietRequest.getIdHoaDon(), hoaDonChiTietRequest.getIdChiTietSanPham());
        if (hoaDonChiTiet1 != null) {// kiểm tra nếu hoadonchitiet đã có trong db thì cập nhật lại hoadonchitiet
            HoaDonChiTiet hoaDonChiTiet = HoaDonChiTiet.builder()
                    .id(hoaDonChiTiet1.getId())
                    .hoaDon(hoaDonChiTiet1.getHoaDon())
                    .chiTietSanPham(hoaDonChiTiet1.getChiTietSanPham())
                    .soLuong(hoaDonChiTiet1.getSoLuong() + hoaDonChiTietRequest.getSoLuong())
                    .donGia(hoaDonChiTietRequest.getDonGia())
                    .build();
            // start update chitietsanpham
            ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findById(hoaDonChiTietRequest.getIdChiTietSanPham()).get();
            chiTietSanPham.setSoLuong(chiTietSanPham.getSoLuong() - hoaDonChiTietRequest.getSoLuong());
            chiTietSanPhamRepo.save(chiTietSanPham);
            return hoaDonChiTietRepo.save(hoaDonChiTiet);
        }


        //start insert hoadonchitiet
        HoaDonChiTiet hoaDonChiTiet = HoaDonChiTiet.builder()
                .id(null)
                .soLuong(hoaDonChiTietRequest.getSoLuong())
                .donGia(hoaDonChiTietRequest.getDonGia())
                .hoaDon(this.findById(hoaDonChiTietRequest.getIdHoaDon()).get())
                .chiTietSanPham(chiTietSanPhamService.findById(hoaDonChiTietRequest.getIdChiTietSanPham()).get())
                .build();
        // end insert hoadonchitiet

        // start update chitietsanpham
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findById(hoaDonChiTietRequest.getIdChiTietSanPham()).get();
        chiTietSanPham.setSoLuong(chiTietSanPham.getSoLuong() - hoaDonChiTiet.getSoLuong());
        chiTietSanPhamRepo.save(chiTietSanPham);
        return hoaDonChiTietRepo.save(hoaDonChiTiet);
    }

    @Override // cập nhật hóa đơn chi tiết
    public HoaDonChiTiet shopUpdateInvoiceDetail(Integer idHDCT, HoaDonChiTietRequest hoaDonChiTietRequest) {
        // cập nhật lại số lượng sản phẩm trong hóa đơn chi tiết
        Optional<HoaDonChiTiet> hoaDonChiTiet = hoaDonChiTietRepo.findById(idHDCT);
        if (hoaDonChiTiet.isPresent()){
            HoaDonChiTiet hoaDonChiTiet1 = HoaDonChiTiet.builder()
                    .id(hoaDonChiTietRequest.getId())
                    .hoaDon(hoaDonChiTiet.get().getHoaDon())
                    .soLuong(hoaDonChiTietRequest.getSoLuong())
                    .donGia(hoaDonChiTietRequest.getDonGia())
                    .chiTietSanPham(hoaDonChiTiet.get().getChiTietSanPham())
                    .build();
            // start update chitietsanpham
            ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findById(hoaDonChiTiet.get().getChiTietSanPham().getId()).get();
            chiTietSanPham.setSoLuong((chiTietSanPham.getSoLuong()+hoaDonChiTiet.get().getSoLuong()) - hoaDonChiTietRequest.getSoLuong());
            chiTietSanPhamRepo.save(chiTietSanPham);
            return hoaDonChiTietRepo.save(hoaDonChiTiet1);
        }
        return null;// lỗi không cập nhật được
    }

    @Override // xóa hóa đơn chi tiết
    public void shopDeleteInvoiceDetail(Integer id) {
        // xóa hóa đơn chi tiết
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepo.findById(id).get();

        // start update chitietsanpham
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findById(hoaDonChiTiet.getChiTietSanPham().getId()).get();
        ChiTietSanPham chiTietSanPham1 = ChiTietSanPham.builder()
                .id(chiTietSanPham.getId())
                .ma(chiTietSanPham.getMa())
                .giaBan(chiTietSanPham.getGiaBan())
                .soLuong(chiTietSanPham.getSoLuong() + hoaDonChiTiet.getSoLuong())
                .ngayTao(chiTietSanPham.getNgayTao())
                .ngayCapNhat(chiTietSanPham.getNgayCapNhat())
                .trangThai(chiTietSanPham.getTrangThai())
                .mauSac(chiTietSanPham.getMauSac())
                .kichCo(chiTietSanPham.getKichCo())
                .chatLieuGiay(chiTietSanPham.getChatLieuGiay())
                .chatLieuDeGiay(chiTietSanPham.getChatLieuDeGiay())
                .sanPham(chiTietSanPham.getSanPham())
                .build();
        chiTietSanPhamRepo.save(chiTietSanPham1);
        // end update chitietsanpham

        hoaDonChiTietRepo.delete(hoaDonChiTiet);
    }


    @Override // thanh toán online
    public HoaDon onlinePayments(HoaDonRequest hoaDonRequest) {
        String maHoaDon = hoaDonRepo.getTop1ByIdMax().getMaHoaDon();
        // start add hoadon
        HoaDon hoaDon = HoaDon.builder()
                .id(null)
                .maHoaDon(utils.renderCodeHoaDon())
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


    @Override // cập nhật trạng thái hóa đơn => chờ vận chuyển
    public HoaDon choVanChuyen(Integer id) {
        HoaDon hoaDon = hoaDonRepo.findById(id).get();
        hoaDon.setTrangThai(2);
        return hoaDonRepo.save(hoaDon);
    }

    @Override // cập nhật trạng thái hóa đơn => đang giao hàng
    public HoaDon dangGiao(Integer id) {
        HoaDon hoaDon = hoaDonRepo.findById(id).get();
        hoaDon.setTrangThai(3);
        return hoaDonRepo.save(hoaDon);
    }

    @Override // cập nhật trạng thái hóa đơn => đã giao hàng
    public HoaDon daGiao(Integer id) {
        HoaDon hoaDon = hoaDonRepo.findById(id).get();
        hoaDon.setTrangThai(4);
        return hoaDonRepo.save(hoaDon);
    }

    @Override // cập nhật trạng thái hóa đơn => đã hủy
    public HoaDon daHuy(Integer id) {
        HoaDon hoaDon = hoaDonRepo.findById(id).get();
        hoaDon.setTrangThai(5);
        return hoaDonRepo.save(hoaDon);
    }

    @Override // cập nhật trạng thái hóa đơn => trả hàng
    public HoaDon traHang(Integer id) {
        HoaDon hoaDon = hoaDonRepo.findById(id).get();
        hoaDon.setTrangThai(6);
        return hoaDonRepo.save(hoaDon);
    }


    @Override // xuất file pdf
    public void exportPDF(HttpServletResponse response, Integer id) throws IOException {
        HoaDon hoaDon = hoaDonRepo.findById(id).get();
        BigDecimal tongTien = hoaDonChiTietRepo.tongTienByIdHoaDon(id);
        String nhanVien = null;
        if (hoaDon.getNhanVien() != null) {
            nhanVien = hoaDon.getNhanVien().getHoTen();
        }

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        // font chữ
        Font fontTitle = FontFactory.getFont("Arial Unicode MS", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        fontTitle.setSize(20);// size chữ
        Paragraph title = new Paragraph("GENZ-S ", fontTitle);
        title.setAlignment(Paragraph.ALIGN_CENTER);// set vị trí

        Font fontContent = FontFactory.getFont("Arial Unicode MS", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Paragraph infoShop = new Paragraph("\nSố điện thoại: 0375111058\n" +
                "Email: vuttph25379@fpt.edu.vn\n" +
                "Địa chỉ: Số 15 - Xã Quảng Bị - Huyện Chương Mỹ - TP.Hà Nội\n" +
                "Ngân hàng: MBBank - Số tài khoản: 5678915032003 \n" +
                "Chủ tài khoản: TRINH TRONG VU "
                , fontContent);
        infoShop.setAlignment(Paragraph.ALIGN_CENTER);

        PdfPTable tableInfoShop = new PdfPTable(3);
        tableInfoShop.setWidthPercentage(100);
        tableInfoShop.setWidths(new float[]{20, 70, 10});
        Image qrCodeImage = Image.getInstance(QRCodeGenerator.generateQrCodeHoaDon(hoaDon));

        PdfPCell cellInfoShop = new PdfPCell();
        cellInfoShop.addElement(qrCodeImage);
        cellInfoShop.setHorizontalAlignment(PdfCell.ALIGN_LEFT);
        cellInfoShop.setBorder(PdfPCell.NO_BORDER);
        tableInfoShop.addCell(cellInfoShop);
        cellInfoShop.setPhrase(infoShop);
        cellInfoShop.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
        cellInfoShop.setBorder(PdfPCell.NO_BORDER);
        tableInfoShop.addCell(cellInfoShop);
        cellInfoShop.setPhrase(new Phrase(""));
        cellInfoShop.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
        cellInfoShop.setBorder(PdfPCell.NO_BORDER);
        tableInfoShop.addCell(cellInfoShop);

        Paragraph titleInvoice = new Paragraph("\nHÓA ĐƠN BÁN HÀNG", fontTitle);
        titleInvoice.setAlignment(Paragraph.ALIGN_CENTER);

        Paragraph codeInvoice = new Paragraph(hoaDon.getMaHoaDon(), fontContent);
        codeInvoice.setAlignment(Paragraph.ALIGN_CENTER);

        Paragraph infoInvoice = new Paragraph("\nNgày mua: " + hoaDon.getNgayTao() +
                "\nKhách hàng: " + hoaDon.getKhachHang() +
                "\nĐịa chỉ: " + hoaDon.getDiaChi() +
                "\nSố điện thoại: " + hoaDon.getSoDienThoai() +
                "\nNhân viên bán hàng: " + nhanVien
                , fontContent);
        infoInvoice.setAlignment(Paragraph.ALIGN_LEFT);

        Font fontTitleTable = FontFactory.getFont("Arial Unicode MS", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        fontTitle.setSize(17);
        Paragraph titleTable = new Paragraph("\nDANH SÁCH SẢN PHẨM KHÁCH HÀNG MUA\n", fontTitleTable);
        titleTable.setAlignment(Paragraph.ALIGN_CENTER);
        titleTable.setSpacingAfter(15f);


        // Tạo bảng với 5 cột
        PdfPTable tableProduct = new PdfPTable(5);
        tableProduct.setWidthPercentage(100);
        tableProduct.setWidths(new float[]{10, 40, 10, 20, 20});

        PdfPCell cellProduct = new PdfPCell();
        cellProduct.setPadding(5);
        cellProduct.setPhrase(new Phrase("STT"));
        cellProduct.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
        tableProduct.addCell(cellProduct);

        cellProduct.setPhrase(new Phrase("Sản phẩm"));
        cellProduct.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
        tableProduct.addCell(cellProduct);

        cellProduct.setPhrase(new Phrase("Số lượng"));
        cellProduct.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
        tableProduct.addCell(cellProduct);

        cellProduct.setPhrase(new Phrase("Đơn giá"));
        cellProduct.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
        tableProduct.addCell(cellProduct);

        cellProduct.setPhrase(new Phrase("Thành tiền"));
        cellProduct.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
        tableProduct.addCell(cellProduct);

        int index = 0;
        for (HoaDonChiTiet x : hoaDon.getListHoaDonChiTiet()) {
            ++index;
            cellProduct.setPhrase(new Phrase(String.valueOf(index)));
            cellProduct.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
            tableProduct.addCell(cellProduct);

            cellProduct.setPhrase(new Phrase(x.getChiTietSanPham().getSanPham().getTen() + "[" + x.getChiTietSanPham().getMa() + "]"));
            cellProduct.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
            tableProduct.addCell(cellProduct);

            cellProduct.setPhrase(new Phrase(String.valueOf(x.getSoLuong())));
            cellProduct.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
            tableProduct.addCell(cellProduct);

            cellProduct.setPhrase(new Phrase(String.valueOf(x.getDonGia()) + " đ"));
            cellProduct.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
            tableProduct.addCell(cellProduct);

            cellProduct.setPhrase(new Phrase(String.valueOf(x.getDonGia().multiply(new BigDecimal(x.getSoLuong()))) + " đ"));
            cellProduct.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
            tableProduct.addCell(cellProduct);
        }


        PdfPTable tableTotalAmout = new PdfPTable(2);
        tableTotalAmout.setWidthPercentage(100);
        tableTotalAmout.setWidths(new float[]{80, 20});

        PdfPCell cellTotalAmout = new PdfPCell();

        cellTotalAmout.setPhrase(new Phrase("Tổng tiền"));
        cellTotalAmout.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
        tableTotalAmout.addCell(cellTotalAmout);

        cellTotalAmout.setPhrase(new Phrase(String.valueOf(tongTien) + " đ"));
        cellTotalAmout.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
        tableTotalAmout.addCell(cellTotalAmout);


        cellTotalAmout.setPhrase(new Phrase("\nChiết khấu"));
        cellTotalAmout.setHorizontalAlignment(PdfCell.ALIGN_LEFT);
        cellTotalAmout.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
        tableTotalAmout.addCell(cellTotalAmout);

        if (hoaDon.getPhieuGiamGia() == null) {
            cellTotalAmout.setPhrase(new Phrase(String.valueOf("\n0 đ")));
            cellTotalAmout.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
            cellTotalAmout.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
            tableTotalAmout.addCell(cellTotalAmout);
        } else {
            cellTotalAmout.setPhrase(new Phrase(String.valueOf("\n" + hoaDon.getTienGiam() + " đ")));
            cellTotalAmout.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
            cellTotalAmout.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
            tableTotalAmout.addCell(cellTotalAmout);
        }

        cellTotalAmout.setPhrase(new Phrase("Phí ship"));
        cellTotalAmout.setHorizontalAlignment(PdfCell.ALIGN_LEFT);
        cellTotalAmout.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
        tableTotalAmout.addCell(cellTotalAmout);

        cellTotalAmout.setPhrase(new Phrase(String.valueOf(hoaDon.getPhiVanChuyen()) + " đ"));
        cellTotalAmout.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
        cellTotalAmout.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
        tableTotalAmout.addCell(cellTotalAmout);

        cellTotalAmout.setPhrase(new Phrase("Tổng tiền phải thanh toán"));
        cellTotalAmout.setHorizontalAlignment(PdfCell.ALIGN_LEFT);
        cellTotalAmout.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
        tableTotalAmout.addCell(cellTotalAmout);

        cellTotalAmout.setPhrase(new Phrase(String.valueOf(hoaDon.getTongTienSauGiam()) + " đ"));
        cellTotalAmout.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
        cellTotalAmout.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
        tableTotalAmout.addCell(cellTotalAmout);

        cellTotalAmout.setPhrase(new Phrase("Trạng thái đơn hàng"));
        cellTotalAmout.setHorizontalAlignment(PdfCell.ALIGN_LEFT);
        cellTotalAmout.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
        tableTotalAmout.addCell(cellTotalAmout);

        cellTotalAmout.setPhrase(new Phrase(String.valueOf(utils.trangThaiDonHang(hoaDon.getTrangThai()))));
        cellTotalAmout.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
        cellTotalAmout.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
        tableTotalAmout.addCell(cellTotalAmout);

        Paragraph footer = new Paragraph("\n\n\n\n\n---Cám ơn quý khách---", fontContent);
        footer.setAlignment(Paragraph.ALIGN_CENTER);// set vị trí


        document.add(title);
        document.add(tableInfoShop);
        document.add(titleInvoice);
        document.add(codeInvoice);
        document.add(infoInvoice);
        document.add(titleTable);
        document.add(tableProduct);
        document.add(tableTotalAmout);
        document.add(footer);
        document.close();
    }

    @Override
    public HoaDon CreateInvoice() {
        if (hoaDonRepo.getTop1ByIdMax() == null) {
            // tạo hóa đơn chờ
            HoaDon hoaDon = new HoaDon();
            hoaDon.setNgayTao(Date.valueOf(curruntDate));
            hoaDon.setId(null);
            hoaDon.setMaHoaDon(utils.renderCodeHoaDon());
            hoaDon.setTrangThai(0);
            hoaDon.setNhanVien(null);
            return hoaDonRepo.save(hoaDon);
        }
        // tạo hóa đơn chờ
        HoaDon hoaDon = new HoaDon();
        hoaDon.setNgayTao(Date.valueOf(curruntDate));
        hoaDon.setId(null);
        hoaDon.setMaHoaDon(utils.renderCodeHoaDon());
        hoaDon.setTrangThai(0);
        hoaDon.setNhanVien(null);
        return hoaDonRepo.save(hoaDon);
    }
//    public HoaDon CreateInvoice() {
//        if (hoaDonRepo.getTop1ByIdMax() == null) {
//            String maHoaDonMoi = utils.renderCodeHoaDon("000000000000");
//            // tạo hóa đơn chờ
//            HoaDon hoaDon = new HoaDon();
//            hoaDon.setNgayTao(Date.valueOf(curruntDate));
//            hoaDon.setId(null);
//            hoaDon.setMaHoaDon(maHoaDonMoi);
//            hoaDon.setTrangThai(0);
//            hoaDon.setNhanVien(null);
//            return hoaDonRepo.save(hoaDon);
//        }
//        String maHoaDon = hoaDonRepo.getTop1ByIdMax().getMaHoaDon();
//        String maHoaDonMoi = utils.renderCodeHoaDon(maHoaDon);
//        // tạo hóa đơn chờ
//        HoaDon hoaDon = new HoaDon();
//        hoaDon.setNgayTao(Date.valueOf(curruntDate));
//        hoaDon.setId(null);
//        hoaDon.setMaHoaDon(maHoaDonMoi);
//        hoaDon.setTrangThai(0);
//        hoaDon.setNhanVien(null);
//        return hoaDonRepo.save(hoaDon);
//    }

}
