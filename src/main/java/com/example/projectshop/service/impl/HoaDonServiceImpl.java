package com.example.projectshop.service.impl;

import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.GioHang;
import com.example.projectshop.domain.GioHangChiTiet;
import com.example.projectshop.domain.HoaDon;
import com.example.projectshop.domain.HoaDonChiTiet;
import com.example.projectshop.domain.KhachHang;
import com.example.projectshop.domain.NhanVien;
import com.example.projectshop.domain.PhieuGiamGia;
import com.example.projectshop.domain.TraHang;
import com.example.projectshop.domain.TraHangChiTiet;
import com.example.projectshop.dto.hoadon.HoaDonChiTietRequest;
import com.example.projectshop.dto.hoadon.HoaDonRequest;
import com.example.projectshop.repository.*;
import com.example.projectshop.service.IChiTietSanPhamService;
import com.example.projectshop.service.IHoaDonService;
import com.example.projectshop.service.IKhachHangService;
import com.example.projectshop.service.IPhieuGiamGiaService;
import com.example.projectshop.utilities.QRCodeGenerator;
import com.example.projectshop.utilities.utility;
import com.example.projectshop.vnpay.ConfigVNpay;
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
import com.lowagie.text.pdf.draw.LineSeparator;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

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
    private KhachHangRepository khachHangRepository;

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepo;

    @Autowired
    NhanVienRepository nhanVienRepository;

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepo;

    @Autowired
    private GioHangRepository gioHangRepo;

    @Autowired
    private PhieuGiamGiaRepository phieuGiamGiaRepository;

    private TraHangRepository traHangRepo;


    @Autowired
    private TraHangChiTietRepository traHangChiTietRepo;

    @Autowired
    private WebApplicationContext appContext;

    // Tạo một đối tượng NumberFormat với định dạng tiền tệ và đơn vị VND
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));


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

        Sort sort = Sort.by(isSortDesc ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, pageSize, sort);
        return hoaDonRepo.findAll(status, keyword, pageable);
    }

    @Override
    public HoaDon findById(Integer id) {
        Optional<HoaDon> hoaDon = hoaDonRepo.findById(id);
        if (hoaDon.isPresent()) {
            return hoaDon.get();
        }
        return null;
    }

    @Override
    public HoaDonChiTiet findByIdHDCT(Integer id) {
        return hoaDonChiTietRepo.findById(id).get();
    }

    @Override
    public HoaDon findByMa(String ma) {
        Optional<HoaDon> hoaDon = hoaDonRepo.findByMa(ma);
        if (hoaDon.isPresent()) {
            return hoaDon.get();
        }
        return null;
    }

    @Override
    public List<HoaDon> findByIdKhachHangAnhTrangThai(Integer trangThai) {
        KhachHang khachHang = (KhachHang) appContext.getServletContext().getAttribute("khachHang");
        return hoaDonRepo.findByIdKhachHangAndTrangThai(trangThai, khachHang.getId());
    }

    @Override // update hóa đơn
    public HoaDon update(Integer id, HoaDonRequest hoaDonRequest) {
        HoaDon resultFindById = this.findById(id);

        // start add hoadon
        HoaDon hoaDon = new HoaDon();
        hoaDon.setId(id);
        hoaDon.setTenKhachHang(hoaDonRequest.getTenKhachHang());
        hoaDon.setSoDienThoai(hoaDonRequest.getSoDienThoai());
        hoaDon.setDiaChi(hoaDonRequest.getDiaChi());
        hoaDon.setPhuongXa(hoaDonRequest.getPhuongXa());
        hoaDon.setQuanHuyen(hoaDonRequest.getQuanHuyen());
        hoaDon.setTinhThanh(hoaDonRequest.getTinhThanh());
        hoaDon.setNgayTao(resultFindById.getNgayTao());
        hoaDon.setNgayCapNhat(Date.valueOf(curruntDate));
        hoaDon.setTongTien(new BigDecimal(hoaDonRequest.getTongTien()));
        hoaDon.setPhiVanChuyen(new BigDecimal(hoaDonRequest.getPhiVanChuyen()));
        hoaDon.setPhuongThucThanhToan(hoaDonRequest.getPhuongThucThanhToan());
        hoaDon.setTrangThai(hoaDonRequest.getTrangThai());
        hoaDon.setPhieuGiamGia(phieuGiamGiaService.findById(hoaDonRequest.getPhieuGiamGia()));
        hoaDon.setKhachHang(khachHangService.findById(hoaDonRequest.getKhachHang()));
        hoaDon.setNhanVien(null); // nhân viên vẫn để null
        hoaDonRepo.save(hoaDon);
        return hoaDon;
    }

    @Override
    public HoaDon updateInvoice(HoaDon hoaDon) {
        HoaDon hoaDon2 = this.hoaDonRepo.findById(hoaDon.getId()).get();
        for (HoaDonChiTiet x : hoaDon2.getListHoaDonChiTiet()) {
            for (HoaDonChiTiet y : hoaDon.getListHoaDonChiTiet()) {
                if (x.getId().equals(y.getId())) {
                    if (x.getSoLuong() > y.getSoLuong()) {
                        Integer soLuong = x.getSoLuong() - y.getSoLuong();
                        ChiTietSanPham chiTietSanPham = this.chiTietSanPhamService.findById(x.getChiTietSanPham().getId());
                        chiTietSanPham.setSoLuong(x.getChiTietSanPham().getSoLuong() + soLuong);
                        this.chiTietSanPhamRepo.save(chiTietSanPham);
                    } else if (y.getSoLuong() > x.getSoLuong()) {
                        Integer soLuong = y.getSoLuong() - x.getSoLuong();
                        ChiTietSanPham chiTietSanPham = this.chiTietSanPhamService.findById(x.getChiTietSanPham().getId());
                        chiTietSanPham.setSoLuong(x.getChiTietSanPham().getSoLuong() - soLuong);
                        this.chiTietSanPhamRepo.save(chiTietSanPham);
                    } else {

                    }
                    // cập nhật hóa đơn chi tiết
                    HoaDonChiTiet hoaDonChiTiet = HoaDonChiTiet.builder()
                            .id(x.getId())
                            .hoaDon(hoaDon2)
                            .chiTietSanPham(x.getChiTietSanPham())
                            .soLuong(y.getSoLuong())
                            .donGia(x.getDonGia())
                            .build();
                    this.hoaDonChiTietRepo.save(hoaDonChiTiet);
                    break;
                }
            }
        }
//        // start add hoadon
        HoaDon hoaDon1 = HoaDon.builder()
                .id(hoaDon.getId())
                .maHoaDon(hoaDon.getMaHoaDon())
                .tenKhachHang(hoaDon.getTenKhachHang())
                .soDienThoai(hoaDon.getSoDienThoai())
                .diaChi(hoaDon.getDiaChi())
                .phuongXa(hoaDon.getPhuongXa())
                .quanHuyen(hoaDon.getQuanHuyen())
                .tinhThanh(hoaDon.getTinhThanh())
                .ngayTao(hoaDon.getNgayTao())
                .ngayCapNhat(null)
                .tongTien(new BigDecimal(String.valueOf(hoaDon.getTongTien())))
                .tienGiam(new BigDecimal(String.valueOf(hoaDon.getTienGiam())))
                .tongTienSauGiam(new BigDecimal(String.valueOf(hoaDon.getTongTienSauGiam())))
                .phiVanChuyen(new BigDecimal(String.valueOf(hoaDon.getPhiVanChuyen())))
                .phuongThucThanhToan(hoaDon.getPhuongThucThanhToan())
                .trangThai(hoaDon.getTrangThai())
                .phieuGiamGia(hoaDon.getPhieuGiamGia())
                .khachHang(hoaDon.getKhachHang())
                .nhanVien(null)
//                .listHoaDonChiTiet(hoaDon.getListHoaDonChiTiet())
                .build();
        return hoaDonRepo.save(hoaDon1);
    }

    @Override
    public void delete(String maHoaDon) {
        HoaDon hoaDon = this.findByMa(maHoaDon);// find hoadon by ma

        // xóa hóa đơn chi tiết
        List<HoaDonChiTiet> hoaDonChiTiets = hoaDonChiTietRepo.findByIdHoaDon(hoaDon.getId());
        for (HoaDonChiTiet x : hoaDonChiTiets) {
            // start update chitietsanpham
            ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findById(x.getChiTietSanPham().getId());
            ChiTietSanPham chiTietSanPham1 = ChiTietSanPham.builder()
                    .id(chiTietSanPham.getId())
                    .ma(chiTietSanPham.getMa())
                    .giaBan(chiTietSanPham.getGiaBan())
                    .soLuong(chiTietSanPham.getSoLuong() + x.getSoLuong())
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

            hoaDonChiTietRepo.delete(x);//xóa hóa đơn chi tiết
        }

        hoaDonRepo.delete(hoaDon);
    }

    @Override
    public HoaDon huyDonHang(Integer id) {
        HoaDon hoaDon = this.findById(id);
        hoaDon.setNgayCapNhat(Date.valueOf(curruntDate));
        hoaDon.setTrangThai(6);

        for (HoaDonChiTiet x : hoaDon.getListHoaDonChiTiet()) {
            ChiTietSanPham chiTietSanPham = x.getChiTietSanPham();
            chiTietSanPham.setSoLuong(chiTietSanPham.getSoLuong() + x.getSoLuong());
            this.chiTietSanPhamRepo.save(chiTietSanPham);
        }
        return this.hoaDonRepo.save(hoaDon);
    }

    @Override
    public HoaDonChiTiet deleteHdct(Integer idHdct) {
        HoaDonChiTiet hoaDonChiTiet = this.hoaDonChiTietRepo.findById(idHdct).get();
        ChiTietSanPham chiTietSanPham = hoaDonChiTiet.getChiTietSanPham();
        chiTietSanPham.setSoLuong(chiTietSanPham.getSoLuong() + hoaDonChiTiet.getSoLuong());
        this.chiTietSanPhamRepo.save(chiTietSanPham);
        this.hoaDonChiTietRepo.delete(hoaDonChiTiet);
        return hoaDonChiTiet;
    }


    @Override // thanh toán tại quầy
    public HoaDon shopPayments(Integer idHoaDon, HoaDonRequest hoaDonRequest) throws UnsupportedEncodingException {
        HoaDon hoaDon = this.findById(idHoaDon);
        System.out.println("hoa don req "+ hoaDonRequest);
        // start add hoadon
        HoaDon hoaDon1 = HoaDon.builder()
                .id(idHoaDon)
                .maHoaDon(hoaDon.getMaHoaDon())
//                .tenKhachHang(hoaDonRequest.getTenKhachHang())
                .soDienThoai(hoaDonRequest.getSoDienThoai())
                .diaChi(hoaDonRequest.getDiaChi())
//                .phuongXa(hoaDonRequest.getPhuongXa())
//                .quanHuyen(hoaDonRequest.getQuanHuyen())
//                .tinhThanh(hoaDonRequest.getTinhThanh())
                .ngayTao(hoaDon.getNgayTao())
                .ngayCapNhat(Date.valueOf(curruntDate))
                .tongTien(new BigDecimal(hoaDonRequest.getTongTien()))
                .tienGiam(new BigDecimal(hoaDonRequest.getTienGiam()))
//                .tienGiam(new BigDecimal(0))
//                .tongTienSauGiam(new BigDecimal(hoaDonRequest.getTongTienSauGiam()))
                .tongTienSauGiam(new BigDecimal(hoaDonRequest.getTongTienSauGiam()))
                .phiVanChuyen(new BigDecimal(hoaDonRequest.getPhiVanChuyen()))
//                .phiVanChuyen(new BigDecimal(0))
                .phuongThucThanhToan(hoaDonRequest.getPhuongThucThanhToan())
                .trangThai(1)
//                .phieuGiamGia(null)
                .phieuGiamGia(phieuGiamGiaService.findById(hoaDonRequest.getPhieuGiamGia()))
//                .phieuGiamGia(null)
//                .khachHang(null)
                .khachHang(khachHangService.findById(hoaDonRequest.getKhachHang()))
                .nhanVien(nhanVienRepository.findById(hoaDonRequest.getNhanVien()).get())
                .build();

        hoaDonRepo.save(hoaDon1);
        // end add hoadon
        return hoaDon;
    }

    @Override
    public String vnPayShopService(Integer idHoaDon, HoaDonRequest hoaDonRequest) throws UnsupportedEncodingException {
        HoaDon hoaDon = this.findById(idHoaDon);
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
                .tongTien(new BigDecimal(hoaDonRequest.getTongTien()))
                .tienGiam(new BigDecimal(0))
//                .tienGiam(new BigDecimal(hoaDonRequest.getTienGiam()))
//                .tongTienSauGiam(new BigDecimal(0))
                .tongTienSauGiam(new BigDecimal(hoaDonRequest.getTongTienSauGiam()))
//                .phiVanChuyen(new BigDecimal(hoaDonRequest.getPhiVanChuyen()))
                .phiVanChuyen(new BigDecimal(0))
                .phuongThucThanhToan(hoaDonRequest.getPhuongThucThanhToan())
                .trangThai(0)
                .phieuGiamGia(phieuGiamGiaService.findById(hoaDonRequest.getPhieuGiamGia()))
                .khachHang(null)
//                .khachHang(khachHangService.findById(hoaDonRequest.getKhachHang()))
                .nhanVien(null)
                .build();

        hoaDonRepo.save(hoaDon1);
        // end add hoadon
// thanh toán vnpay tại quầy

        // start service vnpay
        long amount = Integer.parseInt(hoaDonRequest.getTongTienSauGiam()) * 100L;
//        long amount =0;
        String vnp_TxnRef = hoaDon.getMaHoaDon();

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", ConfigVNpay.vnp_Version);
        vnp_Params.put("vnp_Command", ConfigVNpay.vnp_Command);
        vnp_Params.put("vnp_TmnCode", ConfigVNpay.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", null);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", ConfigVNpay.vnp_ReturnShopUrl);
        vnp_Params.put("vnp_IpAddr", "127.0.0.1");

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
//            String fieldName = (String) itr.next();
//            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
//            if (fieldValue != null && !fieldValue.isEmpty()) {

                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = ConfigVNpay.hmacSHA512(ConfigVNpay.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = ConfigVNpay.vnp_PayUrl + "?" + queryUrl;
        // end service vnpay
        return paymentUrl;
// end thanh toán vnpay tại quầy
    }

    @Override // tạo hóa đơn chờ
    public HoaDon shopCreateInvoice(Integer idNhanVien) {
        if (hoaDonRepo.getTop1ByIdMax() == null) {
            // tạo hóa đơn chờ
            HoaDon hoaDon = new HoaDon();
            hoaDon.setNgayTao(Date.valueOf(curruntDate));
            hoaDon.setId(null);
            hoaDon.setMaHoaDon(utility.renderCodeHoaDon());
            hoaDon.setTrangThai(0);
            hoaDon.setNhanVien(null);

            return hoaDonRepo.save(hoaDon);
        }
        // tạo hóa đơn chờ
        HoaDon hoaDon = new HoaDon();
        hoaDon.setNgayTao(Date.valueOf(curruntDate));
        hoaDon.setId(null);
        hoaDon.setMaHoaDon(utility.renderCodeHoaDon());
        hoaDon.setTrangThai(0);
        hoaDon.setNhanVien(nhanVienRepository.findById(1).get());
        hoaDon.setKhachHang(null);

        return hoaDonRepo.save(hoaDon);
    }

    @Override // tạo mới hóa đơn chi tiết
    public HoaDonChiTiet shopCreateInvoiceDetail(HoaDonChiTietRequest hoaDonChiTietRequest) {
        // thêm sản phẩm vào hóa đơn => tạo hóa đơn chi tiết

        // lấy hoadonchitiet by idsanpham, idctsp
        HoaDonChiTiet hoaDonChiTiet1 = hoaDonChiTietRepo.findByIdHoaDonAndIdCTSP(hoaDonChiTietRequest.getIdHoaDon(), hoaDonChiTietRequest.getIdChiTietSanPham());

        if (hoaDonChiTiet1 != null) {// kiểm tra nếu hoadonchitiet đã có trong db thì cập nhật lại hoadonchitiet
            BigDecimal donGia = hoaDonChiTiet1.getChiTietSanPham().getGiaBan();
            BigDecimal soLuongBigDecimal = new BigDecimal(hoaDonChiTiet1.getSoLuong() + hoaDonChiTietRequest.getSoLuong());
            donGia = donGia.multiply(soLuongBigDecimal);

            HoaDonChiTiet hoaDonChiTiet = HoaDonChiTiet.builder()
                    .id(hoaDonChiTiet1.getId())
                    .hoaDon(hoaDonChiTiet1.getHoaDon())
                    .chiTietSanPham(hoaDonChiTiet1.getChiTietSanPham())
                    .soLuong(hoaDonChiTiet1.getSoLuong() + hoaDonChiTietRequest.getSoLuong())
                    .donGia(hoaDonChiTiet1.getDonGia())
//                    .donGia(hoaDonChiTiet1.getDonGia())

                    .build();
            // start update chitietsanpham
            ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findById(hoaDonChiTietRequest.getIdChiTietSanPham());
            chiTietSanPham.setSoLuong(chiTietSanPham.getSoLuong() - hoaDonChiTietRequest.getSoLuong());
            chiTietSanPhamRepo.save(chiTietSanPham);
            return hoaDonChiTietRepo.save(hoaDonChiTiet);
        }


        //start insert hoadonchitiet
        HoaDonChiTiet hoaDonChiTiet = HoaDonChiTiet.builder()
                .id(null)
                .soLuong(hoaDonChiTietRequest.getSoLuong())
                .donGia(hoaDonChiTietRequest.getDonGia())
                .hoaDon(this.findById(hoaDonChiTietRequest.getIdHoaDon()))
                .chiTietSanPham(chiTietSanPhamService.findById(hoaDonChiTietRequest.getIdChiTietSanPham()))
                .build();

        // end insert hoadonchitiet
        System.out.println(hoaDonChiTiet);
        // start update chitietsanpham
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findById(hoaDonChiTietRequest.getIdChiTietSanPham());
        chiTietSanPham.setSoLuong(chiTietSanPham.getSoLuong() - hoaDonChiTiet.getSoLuong());
        chiTietSanPhamRepo.save(chiTietSanPham);
        return hoaDonChiTietRepo.save(hoaDonChiTiet);

    }

    @Override // cập nhật hóa đơn chi tiết
    public HoaDonChiTiet shopUpdateInvoiceDetail(Integer idHDCT, HoaDonChiTietRequest hoaDonChiTietRequest) {
        // cập nhật lại số lượng sản phẩm trong hóa đơn chi tiết
        Optional<HoaDonChiTiet> hoaDonChiTiet = hoaDonChiTietRepo.findById(idHDCT);
        if (hoaDonChiTiet.isPresent()) {
            HoaDonChiTiet hoaDonChiTiet1 = HoaDonChiTiet.builder()
                    .id(idHDCT)
                    .hoaDon(hoaDonChiTiet.get().getHoaDon())
                    .soLuong(hoaDonChiTietRequest.getSoLuong())
                    .donGia(hoaDonChiTietRequest.getDonGia())
                    .chiTietSanPham(hoaDonChiTiet.get().getChiTietSanPham())
                    .build();

//            hoaDonChiTiet.get().setSoLuong(hoaDonChiTiet.get().getSoLuong() + hoaDonChiTietRequest.getSoLuong()) ;
            // start update chitietsanpham
            ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findById(hoaDonChiTiet.get().getChiTietSanPham().getId());
            chiTietSanPham.setSoLuong((chiTietSanPham.getSoLuong() + hoaDonChiTiet.get().getSoLuong()) - hoaDonChiTietRequest.getSoLuong());
            chiTietSanPhamRepo.save(chiTietSanPham);
            return hoaDonChiTietRepo.save(hoaDonChiTiet.get());
        }
        return null;// lỗi không cập nhật được
    }

    @Override
    public void shopDeleteInvoiceDetail(Integer id) {
        // xóa hóa đơn chi tiết
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepo.findById(id).get();

        // start update chitietsanpham
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findById(hoaDonChiTiet.getChiTietSanPham().getId());
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
    public HoaDon onlinePayment(HoaDonRequest hoaDonRequest) {
        KhachHang khachHang = (KhachHang) appContext.getServletContext().getAttribute("khachHang");
        System.out.println("khachHang" + khachHang.getHoTen());
        PhieuGiamGia phieuGiamGia = phieuGiamGiaService.findById(hoaDonRequest.getPhieuGiamGia());
        NhanVien nhanVien = null;
        GioHang gioHang = gioHangRepo.findByIdKhachHang(khachHang.getId());

        // start add hoadon
        HoaDon hoaDon = HoaDon.builder()
                .id(null)
                .maHoaDon(utility.renderCodeHoaDon())
                .tenKhachHang(hoaDonRequest.getTenKhachHang())
                .soDienThoai(hoaDonRequest.getSoDienThoai())
                .diaChi(hoaDonRequest.getDiaChi())
                .phuongXa(hoaDonRequest.getPhuongXa())
                .quanHuyen(hoaDonRequest.getQuanHuyen())
                .tinhThanh(hoaDonRequest.getTinhThanh())
                .ngayTao(Date.valueOf(curruntDate))
                .ngayCapNhat(null)
                .tongTien(new BigDecimal(hoaDonRequest.getTongTien()))
                .tienGiam(new BigDecimal(hoaDonRequest.getTienGiam()))
                .tongTienSauGiam(new BigDecimal(hoaDonRequest.getTongTienSauGiam()))
                .phiVanChuyen(new BigDecimal(hoaDonRequest.getPhiVanChuyen()))
                .phuongThucThanhToan(hoaDonRequest.getPhuongThucThanhToan())
                .trangThai(2)
                .phieuGiamGia(phieuGiamGia)
                .khachHang(khachHang)
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
            hoaDonChiTiet.setChiTietSanPham(chiTietSanPhamService.findById(x.getIdChiTietSanPham()));
            hoaDonChiTietRepo.save(hoaDonChiTiet);
            // and add hoadonchitiet


            // start update chitietsanpham
            ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findById(hoaDonChiTiet.getChiTietSanPham().getId());
            ChiTietSanPham chiTietSanPham1 = ChiTietSanPham.builder()
                    .id(chiTietSanPham.getId())
                    .ma(chiTietSanPham.getMa())
                    .giaBan(chiTietSanPham.getGiaBan())
                    .soLuong(chiTietSanPham.getSoLuong() - x.getSoLuong())
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

            // start update giohangchitiet
            GioHangChiTiet gioHangChiTiet = gioHangChiTietRepo.findByIdGioHangAndIdCTSP(gioHang.getId(), chiTietSanPham.getId());
            GioHangChiTiet gioHangChiTiet1 = GioHangChiTiet.builder()
                    .id(gioHangChiTiet.getId())
                    .gioHang(gioHang)
                    .chiTietSanPham(chiTietSanPham)
                    .giaBan(gioHangChiTiet.getGiaBan())
                    .soLuong(gioHangChiTiet.getSoLuong() - x.getSoLuong())
                    .build();
            gioHangChiTietRepo.save(gioHangChiTiet1);

            if (gioHangChiTiet1.getSoLuong() <= 0) {// kiểm tra nếu số lượng trong ghct <= 0 --> delete ghct
                gioHangChiTietRepo.delete(gioHangChiTiet);
            }
            // end update giohangchitiet

        }
        return hoaDon;
    }

    @Override
    public void vnPayment(String maHoaDon) {
        // start update hoadon
        HoaDon hoaDon = this.findByMa(maHoaDon);
        hoaDon.setTrangThai(2);
        hoaDonRepo.save(hoaDon);
        // end update hoadon

        // start update giohangchitiet
        GioHang gioHang = gioHangRepo.findByIdKhachHang(hoaDon.getKhachHang().getId());
        for (HoaDonChiTiet x : hoaDon.getListHoaDonChiTiet()) {
            ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findById(x.getChiTietSanPham().getId());
            GioHangChiTiet gioHangChiTiet = gioHangChiTietRepo.findByIdGioHangAndIdCTSP(gioHang.getId(), chiTietSanPham.getId());
            GioHangChiTiet gioHangChiTiet1 = GioHangChiTiet.builder()
                    .id(gioHangChiTiet.getId())
                    .gioHang(gioHang)
                    .chiTietSanPham(chiTietSanPham)
                    .giaBan(gioHangChiTiet.getGiaBan())
                    .soLuong(gioHangChiTiet.getSoLuong() - x.getSoLuong())
                    .build();
            gioHangChiTietRepo.save(gioHangChiTiet1);

            if (gioHangChiTiet1.getSoLuong() <= 0) {// kiểm tra nếu số lượng trong ghct <= 0 --> delete ghct
                gioHangChiTietRepo.delete(gioHangChiTiet);
            }
            // end update giohangchitiet
        }
    }

    @Override
    public String vnPayService(HoaDonRequest hoaDonRequest) throws UnsupportedEncodingException {
        String maHoaDon = utility.renderCodeHoaDon();
        KhachHang khachHang = (KhachHang) appContext.getServletContext().getAttribute("khachHang");
        PhieuGiamGia phieuGiamGia = phieuGiamGiaService.findById(hoaDonRequest.getPhieuGiamGia());
        NhanVien nhanVien = null;
        GioHang gioHang = gioHangRepo.findByIdKhachHang(khachHang.getId());
        // start add hoadon
        HoaDon hoaDon = HoaDon.builder()
                .id(null)
                .maHoaDon(maHoaDon)
                .tenKhachHang(hoaDonRequest.getTenKhachHang())
                .soDienThoai(hoaDonRequest.getSoDienThoai())
                .diaChi(hoaDonRequest.getDiaChi())
                .phuongXa(hoaDonRequest.getPhuongXa())
                .quanHuyen(hoaDonRequest.getQuanHuyen())
                .tinhThanh(hoaDonRequest.getTinhThanh())
                .ngayTao(Date.valueOf(curruntDate))
                .ngayCapNhat(null)
                .tongTien(new BigDecimal(hoaDonRequest.getTongTien()))
                .tienGiam(new BigDecimal(hoaDonRequest.getTienGiam()))
                .tongTienSauGiam(new BigDecimal(hoaDonRequest.getTongTienSauGiam()))
                .phiVanChuyen(new BigDecimal(hoaDonRequest.getPhiVanChuyen()))
                .phuongThucThanhToan(hoaDonRequest.getPhuongThucThanhToan())
                .trangThai(2)
                .phieuGiamGia(phieuGiamGia)
                .khachHang(khachHang)
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
            hoaDonChiTiet.setChiTietSanPham(chiTietSanPhamService.findById(x.getIdChiTietSanPham()));
            hoaDonChiTietRepo.save(hoaDonChiTiet);
            // and add hoadonchitiet


            // start update chitietsanpham
            ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findById(hoaDonChiTiet.getChiTietSanPham().getId());
            ChiTietSanPham chiTietSanPham1 = ChiTietSanPham.builder()
                    .id(chiTietSanPham.getId())
                    .ma(chiTietSanPham.getMa())
                    .giaBan(chiTietSanPham.getGiaBan())
                    .soLuong(chiTietSanPham.getSoLuong() - x.getSoLuong())
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
        }

        // start service vnpay
//        int tongTien = Integer.valueOf(hoaDonRequest.getTongTienSauGiam());
        Integer amount = Integer.valueOf(hoaDonRequest.getTongTienSauGiam()) + Integer.valueOf(hoaDonRequest.getPhiVanChuyen());
        String vnp_TxnRef = maHoaDon;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", ConfigVNpay.vnp_Version);
        vnp_Params.put("vnp_Command", ConfigVNpay.vnp_Command);
        vnp_Params.put("vnp_TmnCode", ConfigVNpay.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount) + "00");
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", null);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", ConfigVNpay.vnp_ReturnOnlineUrl);
        vnp_Params.put("vnp_IpAddr", "127.0.0.1");

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = ConfigVNpay.hmacSHA512(ConfigVNpay.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = ConfigVNpay.vnp_PayUrl + "?" + queryUrl;
        // end service vnpay
        return paymentUrl;
    }


    @Override // cập nhật trạng thái hóa đơn
    public HoaDon updateStatus(Integer id, Integer status) {
        NhanVien nhanVien = (NhanVien) appContext.getServletContext().getAttribute("nhanVien");
        HoaDon hoaDon = this.findById(id);
        if (hoaDon == null) {
            return null;
        }

        if (status == 0) {
            hoaDon.setNgayCapNhat(Date.valueOf(curruntDate));
            hoaDon.setNhanVien(nhanVien);
            hoaDon.setTrangThai(0);// cập nhật trạng thái hóa đơn => đã thanh toán
            return hoaDonRepo.save(hoaDon);
        } else if (status == 1) {
            hoaDon.setNgayCapNhat(Date.valueOf(curruntDate));
            hoaDon.setNhanVien(nhanVien);
            hoaDon.setTrangThai(1);// cập nhật trạng thái hóa đơn => chờ xác nhận
            return hoaDonRepo.save(hoaDon);
        } else if (status == 2) {
            if (hoaDon.getTrangThai() == 6) { // từ trạng thái = 6 quay về trạng thái =2 thì cập nhật số lượng ctsp
                for (HoaDonChiTiet x : hoaDon.getListHoaDonChiTiet()) {
                    ChiTietSanPham chiTietSanPham = x.getChiTietSanPham();
                    chiTietSanPham.setSoLuong(chiTietSanPham.getSoLuong() - x.getSoLuong());
                    this.chiTietSanPhamRepo.save(chiTietSanPham);
                }
            }
            hoaDon.setNgayCapNhat(Date.valueOf(curruntDate));
            hoaDon.setNhanVien(nhanVien);
            hoaDon.setTrangThai(2);// cập nhật trạng thái hóa đơn => chờ xác nhận
            return hoaDonRepo.save(hoaDon);
        } else if (status == 3) {
            hoaDon.setNgayCapNhat(Date.valueOf(curruntDate));
            hoaDon.setNhanVien(nhanVien);
            hoaDon.setTrangThai(3);// cập nhật trạng thái hóa đơn => chờ vận chuyển
            return hoaDonRepo.save(hoaDon);
        } else if (status == 4) {
            hoaDon.setNgayCapNhat(Date.valueOf(curruntDate));
            hoaDon.setNhanVien(nhanVien);
            hoaDon.setTrangThai(4);// cập nhật trạng thái hóa đơn => đang giao hàng
            return hoaDonRepo.save(hoaDon);
        } else if (status == 5) {
            hoaDon.setNgayCapNhat(Date.valueOf(curruntDate));
            hoaDon.setNhanVien(nhanVien);
            hoaDon.setTrangThai(5);// cập nhật trạng thái hóa đơn => đã giao hàng
            return hoaDonRepo.save(hoaDon);
        } else if (status == 6) {
            for (HoaDonChiTiet x : hoaDon.getListHoaDonChiTiet()) {
                ChiTietSanPham chiTietSanPham = x.getChiTietSanPham();
                chiTietSanPham.setSoLuong(chiTietSanPham.getSoLuong() + x.getSoLuong());
                this.chiTietSanPhamRepo.save(chiTietSanPham);
            }
            hoaDon.setNgayCapNhat(Date.valueOf(curruntDate));
            hoaDon.setNhanVien(nhanVien);
            hoaDon.setTrangThai(6);// cập nhật trạng thái hóa đơn => đã hủy
            return hoaDonRepo.save(hoaDon);
        } else if (status == 7) {
            hoaDon.setNgayCapNhat(Date.valueOf(curruntDate));
            hoaDon.setNhanVien(nhanVien);
            hoaDon.setTrangThai(7);// cập nhật trạng thái hóa đơn => trả hàng
            return hoaDonRepo.save(hoaDon);
        } else {
            return null;
        }
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
                "Địa chỉ: 13 phố Trịnh Văn Bô, phường Phương Canh, quận Nam Từ Niêm, TP Hà Nội\n"
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

        KhachHang khachHang = hoaDon.getKhachHang();
        String khachHangText;
        if (khachHang != null) {
            khachHangText = khachHang.getHoTen();
        } else {
            khachHangText = "Khách lẻ";
        }

        String diaChi = hoaDon.getDiaChi() + " - " + hoaDon.getPhuongXa() + " - " + hoaDon.getQuanHuyen() + " - " + hoaDon.getTinhThanh();

        Paragraph infoInvoice = new Paragraph("\nNgày mua: " + hoaDon.getNgayTao() +
                "\nKhách hàng: " + khachHangText +
                "\nĐịa chỉ: "  +
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

//            cellProduct.setPhrase(new Phrase(String.valueOf(x.getDonGia()) + " đ"));
            cellProduct.setPhrase(new Phrase(currencyFormatter.format(x.getDonGia())));
            cellProduct.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
            tableProduct.addCell(cellProduct);

            cellProduct.setPhrase(new Phrase(currencyFormatter.format(x.getDonGia().multiply(new BigDecimal(x.getSoLuong())))));
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

        cellTotalAmout.setPhrase(new Phrase("\n" + currencyFormatter.format(tongTien)));
        cellTotalAmout.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
        tableTotalAmout.addCell(cellTotalAmout);


        cellTotalAmout.setPhrase(new Phrase("\nGiảm giá"));
        cellTotalAmout.setHorizontalAlignment(PdfCell.ALIGN_LEFT);
        cellTotalAmout.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
        tableTotalAmout.addCell(cellTotalAmout);

        if (hoaDon.getPhieuGiamGia() == null) {
//            cellTotalAmout.setPhrase(new Phrase(String.valueOf("\n0 đ")));
            cellTotalAmout.setPhrase(new Phrase(" "));
            cellTotalAmout.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
            cellTotalAmout.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
            tableTotalAmout.addCell(cellTotalAmout);
        } else {
            cellTotalAmout.setPhrase(new Phrase(String.valueOf("\n" + currencyFormatter.format(hoaDon.getTienGiam()))));
            cellTotalAmout.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
            cellTotalAmout.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
            tableTotalAmout.addCell(cellTotalAmout);
        }

        cellTotalAmout.setPhrase(new Phrase("Phí ship"));
        cellTotalAmout.setHorizontalAlignment(PdfCell.ALIGN_LEFT);
        cellTotalAmout.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
        tableTotalAmout.addCell(cellTotalAmout);

        cellTotalAmout.setPhrase(new Phrase(currencyFormatter.format(hoaDon.getPhiVanChuyen())));
        cellTotalAmout.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
        cellTotalAmout.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
        tableTotalAmout.addCell(cellTotalAmout);

        cellTotalAmout.setPhrase(new Phrase("Tổng tiền thanh toán"));
        cellTotalAmout.setHorizontalAlignment(PdfCell.ALIGN_LEFT);
        cellTotalAmout.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
        tableTotalAmout.addCell(cellTotalAmout);

        cellTotalAmout.setPhrase(new Phrase(currencyFormatter.format(hoaDon.getTongTienSauGiam())));
        cellTotalAmout.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
        cellTotalAmout.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
        tableTotalAmout.addCell(cellTotalAmout);

        cellTotalAmout.setPhrase(new Phrase("Phương thức thanh toán"));
        cellTotalAmout.setHorizontalAlignment(PdfCell.ALIGN_LEFT);
        cellTotalAmout.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
        tableTotalAmout.addCell(cellTotalAmout);

        cellTotalAmout.setPhrase(new Phrase(String.valueOf(utility.trangThaiThanhToan(hoaDon.getPhuongThucThanhToan()))));
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
    public void exportPDFGiaoHang(HttpServletResponse response, Integer id) throws IOException {
        HoaDon hoaDon = hoaDonRepo.findById(id).get();
        BigDecimal tongTien = hoaDonChiTietRepo.tongTienByIdHoaDon(id);

        // Tạo một đối tượng NumberFormat với định dạng tiền tệ và đơn vị VND
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));


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
                "Địa chỉ: 13 phố Trịnh Văn Bô, phường Phương Canh, quận Nam Từ Niêm, TP Hà Nội\n"
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


        // đường kẻ ngang
        LineSeparator line = new LineSeparator();
        line.setLineWidth(1); // Đặt độ dày của đường kẻ ngang

        PdfPTable tableAddress = new PdfPTable(3);
        tableAddress.setWidthPercentage(100);
        tableAddress.setWidths(new float[]{45, 5, 45});

        PdfPCell cellAddress = new PdfPCell();

        cellAddress.setPhrase(new Phrase(" "));
        cellAddress.setBorder(PdfPCell.NO_BORDER);
        tableAddress.addCell(cellAddress);

        cellAddress.setPhrase(new Phrase(" "));
        cellAddress.setBorder(PdfPCell.NO_BORDER);
        tableAddress.addCell(cellAddress);

        cellAddress.setPhrase(new Phrase(" "));
        cellAddress.setBorder(PdfPCell.NO_BORDER);
        tableAddress.addCell(cellAddress);

        cellAddress.setPhrase(new Phrase("Từ"));
        cellAddress.setBorder(PdfPCell.NO_BORDER);
        tableAddress.addCell(cellAddress);

        cellAddress.setPhrase(new Phrase(""));
        cellAddress.setBorder(PdfPCell.NO_BORDER);
        tableAddress.addCell(cellAddress);

        cellAddress.setPhrase(new Phrase("Đến"));
        cellAddress.setBorder(PdfPCell.NO_BORDER);
        tableAddress.addCell(cellAddress);

        cellAddress.setPhrase(new Phrase("Shop Genz-S"));
        cellAddress.setBorder(PdfPCell.NO_BORDER);
        tableAddress.addCell(cellAddress);

        cellAddress.setPhrase(new Phrase(""));
        cellAddress.setBorder(PdfPCell.NO_BORDER);
        tableAddress.addCell(cellAddress);

        cellAddress.setPhrase(new Phrase(hoaDon.getTenKhachHang()));
        cellAddress.setBorder(PdfPCell.NO_BORDER);
        tableAddress.addCell(cellAddress);

        cellAddress.setPhrase(new Phrase("13 phố Trịnh Văn Bô, phường Phương Canh, quận Nam Từ Niêm, TP Hà Nội"));
        cellAddress.setBorder(PdfPCell.NO_BORDER);
        tableAddress.addCell(cellAddress);

        cellAddress.setPhrase(new Phrase(" "));
        cellAddress.setBorder(PdfPCell.NO_BORDER);
        tableAddress.addCell(cellAddress);

        cellAddress.setPhrase(new Phrase(hoaDon.getDiaChi() + ", " + hoaDon.getPhuongXa() + ", " + hoaDon.getQuanHuyen() + ", " + hoaDon.getTinhThanh()));
        cellAddress.setBorder(PdfPCell.NO_BORDER);
        tableAddress.addCell(cellAddress);

        cellAddress.setPhrase(new Phrase("0375111058"));
        cellAddress.setBorder(PdfPCell.NO_BORDER);
        tableAddress.addCell(cellAddress);

        cellAddress.setPhrase(new Phrase(" "));
        cellAddress.setBorder(PdfPCell.NO_BORDER);
        tableAddress.addCell(cellAddress);

        cellAddress.setPhrase(new Phrase(hoaDon.getSoDienThoai()));
        cellAddress.setBorder(PdfPCell.NO_BORDER);
        tableAddress.addCell(cellAddress);

        cellAddress.setPhrase(new Phrase(" "));
        cellAddress.setBorder(PdfPCell.NO_BORDER);
        tableAddress.addCell(cellAddress);

        cellAddress.setPhrase(new Phrase(" "));
        cellAddress.setBorder(PdfPCell.NO_BORDER);
        tableAddress.addCell(cellAddress);

        cellAddress.setPhrase(new Phrase(" "));
        cellAddress.setBorder(PdfPCell.NO_BORDER);
        tableAddress.addCell(cellAddress);

//------------------
        // Tạo bảng với 5 cột
        PdfPTable tableProduct = new PdfPTable(3);
        tableProduct.setWidthPercentage(100);
        tableProduct.setWidths(new float[]{10, 80, 10});

        PdfPCell cellProduct = new PdfPCell();

        cellProduct.setPhrase(new Phrase(" "));
        cellProduct.setBorder(PdfPCell.NO_BORDER);
        tableProduct.addCell(cellProduct);

        cellProduct.setPhrase(new Phrase(" "));
        cellProduct.setBorder(PdfPCell.NO_BORDER);
        tableProduct.addCell(cellProduct);

        cellProduct.setPhrase(new Phrase(" "));
        cellProduct.setBorder(PdfPCell.NO_BORDER);
        tableProduct.addCell(cellProduct);

        int index = 0;
        for (HoaDonChiTiet x : hoaDon.getListHoaDonChiTiet()) {
            ++index;
            cellProduct.setPhrase(new Phrase(String.valueOf(index)));
            cellProduct.setBorder(PdfPCell.NO_BORDER);
            tableProduct.addCell(cellProduct);

            cellProduct.setPhrase(new Phrase(x.getChiTietSanPham().getSanPham().getTen() + " [ " + x.getChiTietSanPham().getMa() + " ]"));
            cellProduct.setBorder(PdfPCell.NO_BORDER);
            tableProduct.addCell(cellProduct);

            cellProduct.setPhrase(new Phrase("SL. " + x.getSoLuong()));
            cellProduct.setBorder(PdfPCell.NO_BORDER);
            tableProduct.addCell(cellProduct);
        }

        cellProduct.setPhrase(new Phrase(" "));
        cellProduct.setBorder(PdfPCell.NO_BORDER);
        tableProduct.addCell(cellProduct);

        cellProduct.setPhrase(new Phrase(" "));
        cellProduct.setBorder(PdfPCell.NO_BORDER);
        tableProduct.addCell(cellProduct);

        cellProduct.setPhrase(new Phrase(" "));
        cellProduct.setBorder(PdfPCell.NO_BORDER);
        tableProduct.addCell(cellProduct);
        //

        Font fontAmount = FontFactory.getFont("Arial Unicode MS", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        fontAmount.setSize(15);// size chữ
        Font fontXacNhan = FontFactory.getFont("Arial Unicode MS", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        fontXacNhan.setSize(10);// size chữ
        // Tạo bảng với 5 cột
        PdfPTable tableAmount = new PdfPTable(2);
        tableAmount.setWidthPercentage(100);
        tableAmount.setWidths(new float[]{50, 50});

        PdfPCell cellAmount = new PdfPCell();

        cellAmount.setPhrase(new Phrase(" "));
        cellAmount.setBorder(PdfPCell.NO_BORDER);
        tableAmount.addCell(cellAmount);

        cellAmount.setPhrase(new Phrase(" "));
        cellAmount.setBorder(PdfPCell.NO_BORDER);
        tableAmount.addCell(cellAmount);

        if (hoaDon.getPhuongThucThanhToan() == 0) {
            cellAmount.setPhrase(new Phrase("Tiền thu người nhận"));
            cellAmount.setBorder(PdfPCell.NO_BORDER);
            cellAmount.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
            tableAmount.addCell(cellAmount);

            cellAmount.setPhrase(new Phrase("Chữ ký người nhận", fontAmount));
            cellAmount.setBorder(PdfPCell.NO_BORDER);
            cellAmount.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
            tableAmount.addCell(cellAmount);

            cellAmount.setPhrase(new Phrase(currencyFormatter.format(hoaDon.getTongTienSauGiam().add(hoaDon.getPhiVanChuyen())), fontAmount));
            cellAmount.setBorder(PdfPCell.NO_BORDER);
            tableAmount.addCell(cellAmount);

            cellAmount.setPhrase(new Phrase("Xác nhận đơn hàng nguyên vẹn không móp/méo", fontXacNhan));
            cellAmount.setBorder(PdfPCell.NO_BORDER);
            tableAmount.addCell(cellAmount);
        } else {
            cellAmount.setPhrase(new Phrase("Tiền thu người nhận"));
            cellAmount.setBorder(PdfPCell.NO_BORDER);
            cellAmount.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
            tableAmount.addCell(cellAmount);

            cellAmount.setPhrase(new Phrase("Chữ ký người nhận", fontAmount));
            cellAmount.setBorder(PdfPCell.NO_BORDER);
            cellAmount.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
            tableAmount.addCell(cellAmount);

            cellAmount.setPhrase(new Phrase("0 đ", fontAmount));
            cellAmount.setBorder(PdfPCell.NO_BORDER);
            tableAmount.addCell(cellAmount);

            cellAmount.setPhrase(new Phrase("Xác nhận đơn hàng nguyên vẹn không móp/méo", fontXacNhan));
            cellAmount.setBorder(PdfPCell.NO_BORDER);
            tableAmount.addCell(cellAmount);
        }
        //
        document.add(title);
        document.add(tableInfoShop);
        document.add(line);
        document.add(tableAddress);
        document.add(line);
        document.add(tableProduct);
        document.add(line);
        document.add(tableAmount);
        document.close();
    }

    @Override
    public void exportPDFTraHang(HttpServletResponse response, Integer id) throws IOException {
        TraHang traHang = traHangRepo.findById(id).get();
        HoaDon hoaDon = traHang.getHoaDon();
        BigDecimal tongTienHangMua = hoaDon.getTongTien();
        BigDecimal tongTienHangTra = traHangChiTietRepo.tongTienByIdTraHang(id);


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
                "Địa chỉ: 13 phố Trịnh Văn Bô, phường Phương Canh, quận Nam Từ Niêm, TP Hà Nội\n"
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

        KhachHang khachHang = hoaDon.getKhachHang();
        String khachHangText;
        if (khachHang != null) {
            khachHangText = khachHang.getHoTen();
        } else {
            khachHangText = "Khách lẻ";
        }

        String diaChi = hoaDon.getDiaChi() + " - " + hoaDon.getPhuongXa() + " - " + hoaDon.getQuanHuyen() + " - " + hoaDon.getTinhThanh();

        Paragraph infoInvoice = new Paragraph("\nNgày mua: " + hoaDon.getNgayTao() +
                "\nKhách hàng: " + khachHangText +
                "\nĐịa chỉ: " + diaChi +
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

//            cellProduct.setPhrase(new Phrase(String.valueOf(x.getDonGia()) + " đ"));
            cellProduct.setPhrase(new Phrase(currencyFormatter.format(x.getDonGia())));
            cellProduct.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
            tableProduct.addCell(cellProduct);

            cellProduct.setPhrase(new Phrase(currencyFormatter.format(x.getDonGia().multiply(new BigDecimal(x.getSoLuong())))));
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

        cellTotalAmout.setPhrase(new Phrase(currencyFormatter.format(tongTienHangMua)));
        cellTotalAmout.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
        tableTotalAmout.addCell(cellTotalAmout);


        Paragraph titleTraHang = new Paragraph("\nDANH SÁCH SẢN PHẨM KHÁCH HÀNG TRẢ\n", fontTitleTable);
        titleTraHang.setAlignment(Paragraph.ALIGN_CENTER);
        titleTraHang.setSpacingAfter(15f);

        // Tạo bảng với 5 cột
        PdfPTable tableReturnProduct = new PdfPTable(5);
        tableReturnProduct.setWidthPercentage(100);
        tableReturnProduct.setWidths(new float[]{10, 40, 10, 20, 20});

        PdfPCell cellReturnProduct = new PdfPCell();
        cellReturnProduct.setPadding(5);
        cellReturnProduct.setPhrase(new Phrase("STT"));
        cellReturnProduct.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
        tableReturnProduct.addCell(cellReturnProduct);

        cellReturnProduct.setPhrase(new Phrase("Sản phẩm"));
        cellReturnProduct.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
        tableReturnProduct.addCell(cellReturnProduct);

        cellReturnProduct.setPhrase(new Phrase("Số lượng"));
        cellReturnProduct.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
        tableReturnProduct.addCell(cellReturnProduct);

        cellReturnProduct.setPhrase(new Phrase("Đơn giá"));
        cellReturnProduct.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
        tableReturnProduct.addCell(cellReturnProduct);

        cellReturnProduct.setPhrase(new Phrase("Thành tiền"));
        cellReturnProduct.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
        tableReturnProduct.addCell(cellReturnProduct);

        int i = 0;
        for (TraHangChiTiet x : traHang.getListTraHangChiTiet()) {
            ++i;
            cellReturnProduct.setPhrase(new Phrase(String.valueOf(i)));
            cellReturnProduct.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
            tableReturnProduct.addCell(cellReturnProduct);

            cellReturnProduct.setPhrase(new Phrase(x.getHoaDonChiTiet().getChiTietSanPham().getSanPham().getTen() + "[" + x.getHoaDonChiTiet().getChiTietSanPham().getMa() + "]"));
            cellReturnProduct.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
            tableReturnProduct.addCell(cellReturnProduct);

            cellReturnProduct.setPhrase(new Phrase(String.valueOf(x.getSoLuong())));
            cellReturnProduct.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
            tableReturnProduct.addCell(cellReturnProduct);

//            cellReturnProduct.setPhrase(new Phrase(String.valueOf(x.getDonGia()) + " đ"));
            cellReturnProduct.setPhrase(new Phrase(currencyFormatter.format(x.getDonGia())));
            cellReturnProduct.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
            tableReturnProduct.addCell(cellReturnProduct);

            cellReturnProduct.setPhrase(new Phrase(currencyFormatter.format(x.getDonGia().multiply(new BigDecimal(x.getSoLuong())))));
            cellReturnProduct.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
            tableReturnProduct.addCell(cellReturnProduct);
        }


        PdfPTable tableTotalTraHang = new PdfPTable(2);
        tableTotalTraHang.setWidthPercentage(100);
        tableTotalTraHang.setWidths(new float[]{80, 20});

        PdfPCell cellTotalTraHang = new PdfPCell();

        cellTotalTraHang.setPhrase(new Phrase("Tổng tiền"));
        cellTotalTraHang.setHorizontalAlignment(PdfCell.ALIGN_CENTER);
        tableTotalTraHang.addCell(cellTotalTraHang);

        cellTotalTraHang.setPhrase(new Phrase(currencyFormatter.format(tongTienHangTra)));
        cellTotalTraHang.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
        tableTotalTraHang.addCell(cellTotalTraHang);

        cellTotalTraHang.setPhrase(new Phrase("\nChiết khấu:"));
        cellTotalTraHang.setHorizontalAlignment(PdfCell.ALIGN_LEFT);
        cellTotalTraHang.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
        tableTotalTraHang.addCell(cellTotalTraHang);

        if (hoaDon.getPhieuGiamGia() == null) {
            cellTotalTraHang.setPhrase(new Phrase(String.valueOf("\n0 đ")));
            cellTotalTraHang.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
            cellTotalTraHang.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
            tableTotalTraHang.addCell(cellTotalTraHang);
        } else {
            if (hoaDon.getPhieuGiamGia().getHinhThucGiamGia() == true) {
                cellTotalTraHang.setPhrase(new Phrase(String.valueOf("\n" + currencyFormatter.format(hoaDon.getPhieuGiamGia().getChietKhau()))));
                cellTotalTraHang.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
                cellTotalTraHang.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
                tableTotalTraHang.addCell(cellTotalTraHang);
            } else {

                cellTotalTraHang.setPhrase(new Phrase("\n" + hoaDon.getPhieuGiamGia().getChietKhau() + "%"));
                cellTotalTraHang.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
                cellTotalTraHang.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
                tableTotalTraHang.addCell(cellTotalTraHang);
            }
        }

        cellTotalTraHang.setPhrase(new Phrase("\nPhí ship:"));
        cellTotalTraHang.setHorizontalAlignment(PdfCell.ALIGN_LEFT);
        cellTotalTraHang.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
        tableTotalTraHang.addCell(cellTotalTraHang);


        cellTotalTraHang.setPhrase(new Phrase("\n"+currencyFormatter.format(hoaDon.getPhiVanChuyen())));
        cellTotalTraHang.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
        cellTotalTraHang.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
        tableTotalTraHang.addCell(cellTotalTraHang);

        cellTotalTraHang.setPhrase(new Phrase("\nTổng tiền thanh toán:"));
        cellTotalTraHang.setHorizontalAlignment(PdfCell.ALIGN_LEFT);
        cellTotalTraHang.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
        tableTotalTraHang.addCell(cellTotalTraHang);


        cellTotalTraHang.setPhrase(new Phrase("\n"+currencyFormatter.format(hoaDon.getTongTienSauGiam().add(hoaDon.getPhiVanChuyen()))));
        cellTotalTraHang.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
        cellTotalTraHang.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
        tableTotalTraHang.addCell(cellTotalTraHang);

        cellTotalTraHang.setPhrase(new Phrase("\nTrạng thái đơn hàng:"));
        cellTotalTraHang.setHorizontalAlignment(PdfCell.ALIGN_LEFT);
        cellTotalTraHang.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
        tableTotalTraHang.addCell(cellTotalTraHang);


        cellTotalTraHang.setPhrase(new Phrase("\n Hoàn thành"));
        cellTotalTraHang.setHorizontalAlignment(PdfCell.ALIGN_RIGHT);
        cellTotalTraHang.setBorder(PdfPCell.NO_BORDER); // Không có đường viền
        tableTotalTraHang.addCell(cellTotalTraHang);


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
        document.add(titleTraHang);
        document.add(tableReturnProduct);
        document.add(tableTotalTraHang);
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
            hoaDon.setMaHoaDon(utility.renderCodeHoaDon());
            hoaDon.setTrangThai(0);
            hoaDon.setNhanVien(null);
            return hoaDonRepo.save(hoaDon);
        }
        // tạo hóa đơn chờ
        HoaDon hoaDon = new HoaDon();
        hoaDon.setNgayTao(Date.valueOf(curruntDate));
        hoaDon.setId(null);
        hoaDon.setMaHoaDon(utility.renderCodeHoaDon());
        hoaDon.setTrangThai(0);
        hoaDon.setKhachHang(null);
//        hoaDon.setKhachHang(khachHangRepository.findById(6).get());
        hoaDon.setNhanVien(nhanVienRepository.findById(1).get());
        return hoaDonRepo.save(hoaDon);
    }

    @Override
    public Optional<HoaDon> findByInvoiceNew() {
        return hoaDonRepo.findTopByTrangThaiAndPhuongThucThanhToanOrderByNgayTaoDesc(1, 1);
    }

    @Override
    public List<HoaDonChiTiet> findByIdInvoice(Integer idInvoice) {
        return hoaDonChiTietRepo.findByIdHoaDon(idInvoice);
    }

    @Override
    public HoaDonChiTiet updateSalesQuantityAtTheCounter(Integer idHDCT, Integer soLuong) {
        Optional<HoaDonChiTiet> hoaDonChiTiet = hoaDonChiTietRepo.findById(idHDCT);
        if (hoaDonChiTiet.isPresent()) {
            hoaDonChiTiet.get().setSoLuong(hoaDonChiTiet.get().getSoLuong() + soLuong) ;
            // start update chitietsanpham
            ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findById(hoaDonChiTiet.get().getChiTietSanPham().getId());
            chiTietSanPham.setSoLuong(chiTietSanPham.getSoLuong() +  - soLuong);
            chiTietSanPhamRepo.save(chiTietSanPham);
            return hoaDonChiTietRepo.save(hoaDonChiTiet.get());
        }
        return null;// lỗi không cập nhật được
    }

    @Override
    public PhieuGiamGia addPhieuGiamGiaToHoaDon(Integer idHoaDon, String maPhieuGiamGia) {
        Optional<PhieuGiamGia> optionalPhieuGiamGia = phieuGiamGiaRepository.findByMa(maPhieuGiamGia);

        if (optionalPhieuGiamGia.isPresent()) {
            PhieuGiamGia phieuGiamGia = optionalPhieuGiamGia.get();
            HoaDon hoaDon = hoaDonRepo.findById(idHoaDon).orElse(null);
            System.out.println("aaaaaaaaaaaaaa" + phieuGiamGia.getMa());
            if (hoaDon != null) {
                hoaDon.setPhieuGiamGia(phieuGiamGia);
                hoaDonRepo.save(hoaDon);
                return phieuGiamGia; // Trả về đối tượng PhieuGiamGia sau khi thêm vào hóa đơn
            }
        }
        return null; // Trả về null nếu không tìm thấy hoặc có lỗi
    }



    @Override
    public void cancellingInvoice(Integer idHoaDon) {
        Optional<HoaDon> optionalHoaDon = hoaDonRepo.findById(idHoaDon);
        if (optionalHoaDon.isPresent()) {
            HoaDon hoaDon = optionalHoaDon.get();

            // kiem tra id hdct co ton tai hay khong
            List<HoaDonChiTiet> hoaDonChiTietList = hoaDon.getListHoaDonChiTiet();
            if (hoaDonChiTietList != null && !hoaDonChiTietList.isEmpty()) {

                for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTietList) {
                    ChiTietSanPham chiTietSanPham = hoaDonChiTiet.getChiTietSanPham();
                    int soLuongHuy = hoaDonChiTiet.getSoLuong();
                    chiTietSanPham.setSoLuong(chiTietSanPham.getSoLuong() + soLuongHuy);
                    chiTietSanPhamRepo.save(chiTietSanPham);
                }
                hoaDon.setTrangThai(6);
                hoaDonRepo.save(hoaDon);
            } else {
                hoaDonRepo.delete(hoaDon);
            }
        }
    }

    public HoaDon updateTongTien(Integer idTraHang) {
        TraHang traHang = traHangRepo.findById(idTraHang).get();
        HoaDon hoaDon = traHang.getHoaDon();

        BigDecimal tongTien = traHang.getTienTraKhach().subtract(hoaDon.getPhiVanChuyen());
        hoaDon.setTongTienSauGiam(hoaDon.getTongTienSauGiam().subtract(tongTien));
        return hoaDonRepo.save(hoaDon);
    }
}
