package com.example.projectshop.service.impl;

import com.example.projectshop.domain.AnhSanPham;
import com.example.projectshop.domain.ChatLieuDeGiay;
import com.example.projectshop.domain.ChatLieuGiay;
import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.KichCo;
import com.example.projectshop.domain.MauSac;
import com.example.projectshop.domain.SanPham;
import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamRequest;
import com.example.projectshop.dto.chitietsanpham.ExportExcelCTSP;
import com.example.projectshop.dto.chitietsanpham.ImportExcelCTSP;
import com.example.projectshop.repository.AnhSanPhamRepository;
import com.example.projectshop.repository.ChiTietSanPhamRepository;
import com.example.projectshop.service.IChatLieuDeGiayService;
import com.example.projectshop.service.IChatLieuGiayService;
import com.example.projectshop.service.IChiTietSanPhamService;
import com.example.projectshop.service.IKichCoService;
import com.example.projectshop.service.IMauSacService;
import com.example.projectshop.service.ISanPhamService;
import com.example.projectshop.utils.QRCodeGenerator;
import com.example.projectshop.utils.URLDecode;
import com.example.projectshop.utils.utils;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChiTietSanPhamServiceImpl implements IChiTietSanPhamService {

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepo;

    @Autowired
    private AnhSanPhamRepository anhSanPhamRepo;

    @Autowired
    private IMauSacService mauSacService;

    @Autowired
    private IKichCoService kichCoService;

    @Autowired
    private IChatLieuGiayService chatLieuGiayService;

    @Autowired
    private IChatLieuDeGiayService chatLieuDeGiayService;

    @Autowired
    private ISanPhamService sanPhamService;

    // lấy ra ngày hiện tại
    private LocalDate curruntDate = LocalDate.now();


    @Override
    public Page<ChiTietSanPham> findAll(Integer page,
                                        Integer pageSize,
                                        String sortField,
                                        Boolean isSortDesc
    ) {
        Sort sort = Sort.by(isSortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, pageSize, sort);
        return chiTietSanPhamRepo.findAll(pageable);
    }


    @Override
    public Page<ChiTietSanPham> filter(String priceMin,
                                       String priceMax,
                                       String color,
                                       String shoe_material,
                                       String shoe_sole_material,
                                       String keyword,
                                       Boolean isSortDesc,
                                       String sortField,
                                       Integer page,
                                       Integer pageSize) {
        Sort sort = Sort.by(isSortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, pageSize, sort);

        BigDecimal priceMinOutput = priceMin == null ? BigDecimal.valueOf(0) : BigDecimal.valueOf(Long.valueOf(priceMin));
        BigDecimal priceMaxOutput = priceMax == null ? chiTietSanPhamRepo.getTop1ByPriceMax().getGiaBan() : BigDecimal.valueOf(Long.valueOf(priceMax));

        List<Integer> listMauSac = color == null ? null : Arrays.stream(URLDecode.getDecode(color).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        List<Integer> listChatLieuGiay = shoe_material == null ? null : Arrays.stream(URLDecode.getDecode(shoe_material).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        List<Integer> listChatLieuDeGiay = shoe_sole_material == null ? null : Arrays.stream(URLDecode.getDecode(shoe_sole_material).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        return chiTietSanPhamRepo.getAllByParam(
                priceMinOutput,
                priceMaxOutput,
                listMauSac,
                listChatLieuGiay,
                listChatLieuDeGiay,
                keyword,
                pageable);
    }


    @Override
    public Optional<ChiTietSanPham> findById(Integer id) {
        if (id == null) {
            return null;
        }
        return chiTietSanPhamRepo.findById(id);
    }

    @Override
    public ChiTietSanPham findByMa(String ma) {
        Optional<ChiTietSanPham> chiTietSanPham = chiTietSanPhamRepo.findByMa(ma);
        if (chiTietSanPham.isPresent()) {
            return chiTietSanPham.get();
        }
        return null;
    }

    @Override
    public List<ChiTietSanPham> findByIdSanPham(Integer idSanPham) {
        if (idSanPham == null) {
            return null;
        }
        return chiTietSanPhamRepo.findByIdSanPham(idSanPham);
    }

    @Override
    public List<ExportExcelCTSP> exportExcel() {
        Integer index = 1;
        List<ExportExcelCTSP> exportExcelCTSPS = new ArrayList<>();// convert từ Object[] sang ExportExcelCTSP
        for (Object[] x : chiTietSanPhamRepo.exportExcel()) {
            ExportExcelCTSP exportExcelCTSP = ExportExcelCTSP.builder()
                    .stt(index++)
                    .maSanPham(x[0].toString())
                    .tenSanPham(x[1].toString())
                    .soLuong(x[2].toString())
                    .giaBan(x[3].toString())
                    .ngayTao(x[4].toString())
                    .ngayCapNhat(String.valueOf(x[5]))
                    .trangThai(utils.trangThaiSanPham(Integer.valueOf(x[6].toString())))
                    .mauSac(x[7].toString())
                    .kichCo(x[8].toString())
                    .chatLieuGiay(x[9].toString())
                    .chatLieuDeGiay(x[10].toString())
                    .xuatXu(x[11].toString())
                    .thuongHieu(x[12].toString())
                    .build();
            exportExcelCTSPS.add(exportExcelCTSP);
        }
        return exportExcelCTSPS;
    }

    @Override
    public List<ImportExcelCTSP> importExcel(List<ImportExcelCTSP> importExcelCTSPS) throws IOException, WriterException {
        // tạo 1 list để chứa các sản phẩm lỗi không add được
        List<ImportExcelCTSP> errorImports = new ArrayList<>();

        // sử dụng for để lặp các đối tượng được truyền vào từ file excel
        for (ImportExcelCTSP x : importExcelCTSPS) {
            // start: lấy ra các đối tượng theo tên được truyền vào từ file excel
            MauSac mauSac = mauSacService.findByName(x.getMauSac());
            KichCo kichCo = kichCoService.findByName(x.getKichCo());
            ChatLieuGiay chatLieuGiay = chatLieuGiayService.findByName(x.getChatLieuGiay());
            ChatLieuDeGiay chatLieuDeGiay = chatLieuDeGiayService.findByName(x.getChatLieuDeGiay());
            SanPham sanPham = sanPhamService.findByName(x.getTenSanPham());
            // end:

            // start: kiểm tra nếu 1 trong các đối tượng là null thì add vào list sản phẩm lỗi
            if (mauSac == null || kichCo == null || chatLieuGiay == null || chatLieuDeGiay == null || sanPham == null) {
                errorImports.add(x);
                System.out.println("case1");
                continue;
            }
            // end:

            // start: kiểm tra nếu trangthai là null thì add vào list sản phẩm lỗi
            if (utils.getNumberByNameStatus(x.getTrangThai()) == null) {
                errorImports.add(x);
                System.out.println("case4");
                continue;
            }
            // end:

            // start: Tạo mã cho chitietsanpham
            String maMauSac = utils.tiengVietKhongDau(mauSac.getTen());
            String maChatLieuGiay = utils.tiengVietKhongDau(chatLieuGiay.getTen()).replaceAll("\\s", "");
            String maChatLieuDeGiay = utils.tiengVietKhongDau(chatLieuDeGiay.getTen());
            String maChiTietSanPham = sanPham.getMa() + "-" + maMauSac + "-" + maChatLieuGiay + "-" + maChatLieuDeGiay + "-" + kichCo.getSize();
            // end:

            // kiểm tra nếu mã của chitietsanpham không tồn tại thì thêm mới
            ChiTietSanPham chiTietSanPham = this.findByMa(maChiTietSanPham);
            if ( chiTietSanPham == null) {
                ChiTietSanPham chiTietSanPham1 = ChiTietSanPham.builder()
                        .id(null)
                        .ma(maChiTietSanPham)
                        .soLuong(Integer.valueOf(x.getSoLuong()))
                        .giaBan(new BigDecimal(x.getGiaBan()))
                        .ngayTao(Date.valueOf(curruntDate))
                        .ngayCapNhat(null)
                        .trangThai(utils.getNumberByNameStatus(x.getTrangThai()))
                        .mauSac(mauSac)
                        .kichCo(kichCo)
                        .chatLieuGiay(chatLieuGiay)
                        .chatLieuDeGiay(chatLieuDeGiay)
                        .sanPham(sanPham)
                        .build();
                ChiTietSanPham saveChiTietSanPham = chiTietSanPhamRepo.save(chiTietSanPham1);
                QRCodeGenerator.generateQRCodeCTSP(saveChiTietSanPham);// tạo mã qrcode
                System.out.println("case2");
                continue;
            }else{
                // nếu chitietsanpham đã tồn tại thì cập nhật lại số lượng, giá bán và ngày cập nhật
                ChiTietSanPham chiTietSanPham2 = ChiTietSanPham.builder()
                        .id(chiTietSanPham.getId())
                        .ma(chiTietSanPham.getMa())
                        .soLuong(Integer.valueOf(x.getSoLuong())+chiTietSanPham.getSoLuong())
                        .giaBan(new BigDecimal(x.getGiaBan()))
                        .ngayTao(chiTietSanPham.getNgayTao())
                        .ngayCapNhat(Date.valueOf(curruntDate))
                        .trangThai(utils.getNumberByNameStatus(x.getTrangThai()))
                        .mauSac(chiTietSanPham.getMauSac())
                        .kichCo(chiTietSanPham.getKichCo())
                        .chatLieuGiay(chiTietSanPham.getChatLieuGiay())
                        .chatLieuDeGiay(chiTietSanPham.getChatLieuDeGiay())
                        .sanPham(chiTietSanPham.getSanPham())
                        .build();
                chiTietSanPhamRepo.save(chiTietSanPham2);
                System.out.println("case3");
                continue;
            }
        }
        return errorImports;
    }

    @Override
    public ChiTietSanPham create(ChiTietSanPhamRequest chiTietSanPhamRequest) throws IOException, WriterException {
        MauSac mauSac = mauSacService.findById(chiTietSanPhamRequest.getMauSac());
        KichCo kichCo = kichCoService.findById(chiTietSanPhamRequest.getKichCo());
        ChatLieuGiay chatLieuGiay = chatLieuGiayService.findById(chiTietSanPhamRequest.getChatLieuGiay());
        ChatLieuDeGiay chatLieuDeGiay = chatLieuDeGiayService.findById(chiTietSanPhamRequest.getChatLieuDeGiay());
        SanPham sanPham = sanPhamService.findById(chiTietSanPhamRequest.getSanPham());
        String maMauSac = utils.tiengVietKhongDau(mauSac.getTen());
        String maChatLieuGiay = utils.tiengVietKhongDau(chatLieuGiay.getTen()).replaceAll("\\s", "");
        String maChatLieuDeGiay = utils.tiengVietKhongDau(chatLieuDeGiay.getTen());
        String maChiTietSanPham = sanPham.getMa() + "-" + maMauSac + "-" + maChatLieuGiay + "-" + maChatLieuDeGiay + "-" + kichCo.getSize();

        if (chiTietSanPhamRepo.findByMa(maChiTietSanPham) == null) {
            ChiTietSanPham chiTietSanPham = ChiTietSanPham.builder()
                    .id(null)
                    .ma(maChiTietSanPham)
                    .soLuong(chiTietSanPhamRequest.getSoLuong())
                    .giaBan(chiTietSanPhamRequest.getGiaBan())
                    .ngayTao(Date.valueOf(curruntDate))
                    .ngayCapNhat(null)
                    .trangThai(0)
                    .mauSac(mauSac)
                    .kichCo(kichCo)
                    .chatLieuGiay(chatLieuGiay)
                    .chatLieuDeGiay(chatLieuDeGiay)
                    .sanPham(sanPham)
                    .build();
            ChiTietSanPham saveChiTietSanPham = chiTietSanPhamRepo.save(chiTietSanPham);
            QRCodeGenerator.generateQRCodeCTSP(saveChiTietSanPham);// tạo mã qrcode
            if (!chiTietSanPhamRequest.getAnhSanPhams().isEmpty()) {
                for (String x : chiTietSanPhamRequest.getAnhSanPhams()) {
                    AnhSanPham anhSanPham = AnhSanPham.builder()
                            .id(null)
                            .chiTietSanPham(saveChiTietSanPham)
                            .ten(x)
                            .build();
                    anhSanPhamRepo.save(anhSanPham);
                }
            }
            return saveChiTietSanPham;
        } else {
            return null;
        }
    }

    @Override
    public ChiTietSanPham update(Integer id, ChiTietSanPhamRequest chiTietSanPhamRequest) {
        ChiTietSanPham chiTietSanPham = ChiTietSanPham.builder()
                .id(id)
                .ma(this.findById(id).get().getMa())
                .soLuong(chiTietSanPhamRequest.getSoLuong())
                .giaBan(chiTietSanPhamRequest.getGiaBan())
                .ngayTao(chiTietSanPhamRepo.findById(id).get().getNgayTao())
                .ngayCapNhat(Date.valueOf(curruntDate))
                .trangThai(chiTietSanPhamRequest.getTrangThai())
                .mauSac(mauSacService.findById(chiTietSanPhamRequest.getMauSac()))
                .kichCo(kichCoService.findById(chiTietSanPhamRequest.getKichCo()))
                .chatLieuGiay(chatLieuGiayService.findById(chiTietSanPhamRequest.getChatLieuGiay()))
                .chatLieuDeGiay(chatLieuDeGiayService.findById(chiTietSanPhamRequest.getChatLieuDeGiay()))
                .sanPham(sanPhamService.findById(chiTietSanPhamRequest.getSanPham()))
                .build();
        ChiTietSanPham saveChiTietSanPham = chiTietSanPhamRepo.save(chiTietSanPham);
        if (!chiTietSanPhamRequest.getAnhSanPhams().isEmpty()) { // check rỗng list ảnh
            for (String x : chiTietSanPhamRequest.getAnhSanPhams()) { // lặp list đường dẫn ảnh gửi từ client
                if (!anhSanPhamRepo.getByTen(x).isPresent()) { // check tên ảnh trong db nếu chưa có thì insert
                    AnhSanPham anhSanPham = AnhSanPham.builder()
                            .id(null)
                            .chiTietSanPham(saveChiTietSanPham)
                            .ten(x)
                            .build();
                    anhSanPhamRepo.save(anhSanPham);
                }
            }
        }

        return saveChiTietSanPham;
    }

    @Override
    public ChiTietSanPham delete(Integer id) {
        Optional<ChiTietSanPham> chiTietSanPham = this.findById(id);
        if (chiTietSanPham.isPresent()) {
            chiTietSanPham.get().setTrangThai(1);
            return chiTietSanPhamRepo.save(chiTietSanPham.get());
        }
        return null;
    }

    @Override
    @Transactional
    public ChiTietSanPham fetchctspWithgiohangchitiet(Integer id) {

        return null;
    }


}
