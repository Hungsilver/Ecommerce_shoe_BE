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

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
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
import java.nio.charset.Charset;
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
        if (hoaDonRepo.getTop1ByIdMax() == null) {
            String maHoaDonMoi = utils.renderCodeHoaDon("000000000000");
            // tạo hóa đơn chờ
            HoaDon hoaDon = new HoaDon();
            hoaDon.setNgayTao(Date.valueOf(curruntDate));
            hoaDon.setId(null);
            hoaDon.setMaHoaDon(maHoaDonMoi);
            hoaDon.setTrangThai(0);
            hoaDon.setNhanVien(null);
            return hoaDonRepo.save(hoaDon);
        }
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


    @Override
    public void exportPDF(HttpServletResponse response, Integer id) throws IOException {
        HoaDon hoaDon = hoaDonRepo.findById(id).get();
        BigDecimal tongTien = hoaDonChiTietRepo.tongTienByIdHoaDon(id);

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
                "Địa chỉ: \n" +
                "Ngân hàng: MBBank - Số tài khoản: 5678915032003 \n" +
                "Chủ tài khoản: TRINH TRONG VU "
                , fontContent);
        infoShop.setAlignment(Paragraph.ALIGN_CENTER);

        Paragraph titleInvoice = new Paragraph("\nHÓA ĐƠN BÁN HÀNG", fontTitle);
        titleInvoice.setAlignment(Paragraph.ALIGN_CENTER);

        Paragraph codeInvoice = new Paragraph(hoaDon.getMaHoaDon(), fontContent);
        codeInvoice.setAlignment(Paragraph.ALIGN_CENTER);

        Paragraph infoInvoice = new Paragraph("\nNgày mua: " + hoaDon.getNgayTao() +
                "\nKhách hàng: " + hoaDon.getKhachHang() +
                "\nĐịa chỉ: " + hoaDon.getDiaChi() +
                "\nSố điện thoại: " + hoaDon.getSoDienThoai() +
                "\nNhân viên bán hàng: " + hoaDon.getNhanVien().getHoTen()
                , fontContent);
        infoInvoice.setAlignment(Paragraph.ALIGN_LEFT);

        Font fontTitleTable = FontFactory.getFont("Arial Unicode MS", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        fontTitle.setSize(17);
        Paragraph titleTable = new Paragraph("\nDANH SÁCH SẢN PHẨM KHÁCH HÀNG MUA\n", fontTitleTable);
        titleTable.setAlignment(Paragraph.ALIGN_CENTER);
        titleTable.setSpacingAfter(15f);


        // Tạo bảng với 5 cột
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{10, 40, 10, 20, 20});

        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);
        cell.setPhrase(new Phrase("STT"));
        cell.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
        table.addCell(cell);

        cell.setPhrase(new Phrase("Sản phẩm"));
        cell.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
        table.addCell(cell);

        cell.setPhrase(new Phrase("Số lượng"));
        cell.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
        table.addCell(cell);

        cell.setPhrase(new Phrase("Đơn giá"));
        cell.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
        table.addCell(cell);

        cell.setPhrase(new Phrase("Thành tiền"));
        cell.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
        table.addCell(cell);

        int index = 0;
        for (HoaDonChiTiet x : hoaDon.getListHoaDonChiTiet()) {
            ++index;
            cell.setPhrase(new Phrase(String.valueOf(index)));
            cell.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
            table.addCell(cell);

            cell.setPhrase(new Phrase(x.getChiTietSanPham().getSanPham().getTen() + "[" + x.getChiTietSanPham().getMa() + "]"));
            cell.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
            table.addCell(cell);

            cell.setPhrase(new Phrase(String.valueOf(x.getSoLuong())));
            cell.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
            table.addCell(cell);

            cell.setPhrase(new Phrase(String.valueOf(x.getDonGia()) + " đ"));
            cell.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
            table.addCell(cell);

            cell.setPhrase(new Phrase(String.valueOf(x.getDonGia().multiply(new BigDecimal(x.getSoLuong()))) + " đ"));
            cell.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
            table.addCell(cell);
        }


        PdfPTable table1 = new PdfPTable(2);
        table1.setWidthPercentage(100);
        table1.setWidths(new float[]{80, 20});

        PdfPCell cell1 = new PdfPCell();

        cell1.setPhrase(new Phrase("Tổng tiền"));
        cell1.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
        table1.addCell(cell1);

        cell1.setPhrase(new Phrase(String.valueOf(tongTien) + " đ"));
        cell1.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
        table1.addCell(cell1);


        cell1.setPhrase(new Phrase("\nChiết khấu"));
        cell1.setHorizontalAlignment(PdfCell.ALIGN_LEFT);
        cell1.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
        table1.addCell(cell1);

        if (hoaDon.getPhieuGiamGia() == null ) {
            cell1.setPhrase(new Phrase(String.valueOf( "\n0 đ")));
            cell1.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
            cell1.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
            table1.addCell(cell1);
        }  else {
            cell1.setPhrase(new Phrase(String.valueOf("\n"+hoaDon.getTienGiam()+ " đ")));
            cell1.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
            cell1.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
            table1.addCell(cell1);
        }

        cell1.setPhrase(new Phrase("Phí ship"));
        cell1.setHorizontalAlignment(PdfCell.ALIGN_LEFT);
        cell1.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
        table1.addCell(cell1);

        cell1.setPhrase(new Phrase(String.valueOf(hoaDon.getPhiVanChuyen())+" đ"));
        cell1.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
        cell1.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
        table1.addCell(cell1);

        cell1.setPhrase(new Phrase("Tổng tiền phải thanh toán"));
        cell1.setHorizontalAlignment(PdfCell.ALIGN_LEFT);
        cell1.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
        table1.addCell(cell1);

        cell1.setPhrase(new Phrase(String.valueOf(hoaDon.getTongTienSauGiam())+" đ"));
        cell1.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
        cell1.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
        table1.addCell(cell1);

        cell1.setPhrase(new Phrase("Trạng thái đơn hàng"));
        cell1.setHorizontalAlignment(PdfCell.ALIGN_LEFT);
        cell1.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
        table1.addCell(cell1);

        cell1.setPhrase(new Phrase(String.valueOf(utils.trangThaiDonHang(hoaDon.getTrangThai()))));
        cell1.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
        cell1.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
        table1.addCell(cell1);

        Paragraph footer = new Paragraph("\n\n\n\n\n---Cám ơn quý khách---", fontContent);
        footer.setAlignment(Paragraph.ALIGN_CENTER);// set vị trí

        document.add(title);
        document.add(infoShop);
        document.add(titleInvoice);
        document.add(codeInvoice);
        document.add(infoInvoice);
        document.add(titleTable);
        document.add(table);
        document.add(table1);
        document.add(footer);
        document.close();
    }
}
