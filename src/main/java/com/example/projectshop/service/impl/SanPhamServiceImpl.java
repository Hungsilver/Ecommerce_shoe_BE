package com.example.projectshop.service.impl;


import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.DanhMuc;
import com.example.projectshop.domain.SanPham;
import com.example.projectshop.domain.ThuongHieu;
import com.example.projectshop.domain.Xuatxu;
import com.example.projectshop.dto.sanpham.ExportExcelSanPham;
import com.example.projectshop.dto.sanpham.ImportExcelSanPham;
import com.example.projectshop.dto.sanpham.SanPhamRequest;
import com.example.projectshop.repository.ChiTietSanPhamRepository;
import com.example.projectshop.repository.DanhMucRepository;
import com.example.projectshop.repository.SanPhamRepository;
import com.example.projectshop.repository.ThuongHieuRepository;
import com.example.projectshop.repository.XuatXuRepository;
import com.example.projectshop.service.IDanhMucSevice;
import com.example.projectshop.service.ISanPhamService;
import com.example.projectshop.service.IThuongHieuService;
import com.example.projectshop.utilities.URLDecode;
import com.example.projectshop.utilities.utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SanPhamServiceImpl implements ISanPhamService {

    @Autowired
    private SanPhamRepository sanPhamrepo;

    @Autowired
    ThuongHieuRepository thuongHieuRepository;

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepo;

    @Autowired
    private IThuongHieuService thuongHieuService;

    @Autowired
    private IDanhMucSevice danhMucSevice;

    @Autowired
    private XuatXuServiceImpl xuatXuService;

    // lấy ra ngày hiện tại
    private LocalDate curruntDate = LocalDate.now();


    @Override
    public Page<SanPham> findAll( String brand,
                                  String origin,
                                  String category,
                                  String keyword,
                                  Integer status,
                                  Integer page,
                                  Integer pageSize,
                                  String sortField,
                                  Boolean isSortDesc
    ) {
        Sort sort = Sort.by(isSortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, pageSize, sort);

        List<Integer> listThuongHieu = brand == null ? null : Arrays.stream(URLDecode.getDecode(brand).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        List<Integer> listXuatXu = origin == null ? null : Arrays.stream(URLDecode.getDecode(origin).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        List<Integer> listDanhMuc = origin == null ? null : Arrays.stream(URLDecode.getDecode(category).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        return sanPhamrepo.getAll(
                listThuongHieu,
                listXuatXu,
                listDanhMuc,
                keyword,
                status,
                pageable
        );
    }

    @Override
    public Page<SanPham> filter(String priceMin,
                                String priceMax,
                                String brand,
                                String origin,
                                String color,
                                String size,
                                String shoe_material,
                                String shoe_sole_material,
                                Integer status,
                                String keyword,
                                Boolean isSortAsc,
                                Integer page,
                                Integer pageSize) {

        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, pageSize);
        System.out.println(priceMin);
        System.out.println(priceMax);

        BigDecimal priceMinOutput = priceMin == null ? BigDecimal.valueOf(0) : BigDecimal.valueOf(Long.valueOf(priceMin));
        BigDecimal priceMaxOutput = priceMax == null ? chiTietSanPhamRepo.getTop1ByPriceMax().getGiaBan() : BigDecimal.valueOf(Long.valueOf(priceMax));

        List<Integer> listThuongHieu = brand == null ? null : Arrays.stream(URLDecode.getDecode(brand).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        List<Integer> listXuatXu = origin == null ? null : Arrays.stream(URLDecode.getDecode(origin).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        List<Integer> listMauSac = color == null ? null : Arrays.stream(URLDecode.getDecode(color).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        List<Integer> listKichCo = size == null ? null : Arrays.stream(URLDecode.getDecode(size).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        List<Integer> listChatLieuGiay = shoe_material == null ? null : Arrays.stream(URLDecode.getDecode(shoe_material).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        List<Integer> listChatLieuDeGiay = shoe_sole_material == null ? null : Arrays.stream(URLDecode.getDecode(shoe_sole_material).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        // sắp xếp list chitietsanpham theo giá
        Page<SanPham> listSanPham = sanPhamrepo.getAllByParam(
                priceMinOutput,
                priceMaxOutput,
                listThuongHieu,
                listXuatXu,
                listMauSac,
                listKichCo,
                listChatLieuGiay,
                listChatLieuDeGiay,
                keyword,
                status,
                pageable);
        for (SanPham sanPham : listSanPham.getContent()) {
            List<ChiTietSanPham> chiTietSanPhamList = sanPham.getListChiTietSanPham();
            if (isSortAsc == false) {
                // sort asc
                Collections.sort(chiTietSanPhamList, (ctsp1, ctsp2) -> ctsp1.getGiaBan().compareTo(ctsp2.getGiaBan()));
            } else {
                // sort desc
                Collections.sort(chiTietSanPhamList, (ctsp1, ctsp2) -> ctsp2.getGiaBan().compareTo(ctsp1.getGiaBan()));
            }
        }
        return listSanPham;
    }


    @Override
    public SanPham findById(Integer id) {
        if (id != null) {
            return sanPhamrepo.findById(id).get();
        }
        return null;
    }

    @Override
    public SanPham findByName(String name) {
        Optional<SanPham> sanPham = sanPhamrepo.findByName(name);
        if (sanPham.isPresent()) {
            return sanPham.get();
        }
        return null;
    }

    @Override
    public List<ImportExcelSanPham> importExcel(List<ImportExcelSanPham> importExcelSanPhams) {
        // tạo 1 list để chứa các sản phẩm lỗi không add được
        List<ImportExcelSanPham> errorImports = new ArrayList<>();
        // sử dụng for để lặp các đối tượng được truyền vào từ file excel
        for (ImportExcelSanPham x : importExcelSanPhams) {
            // start: lấy ra các đối tượng theo tên được truyền vào từ file excel
            DanhMuc danhMuc = danhMucSevice.findByName(x.getDanhMuc());
            ThuongHieu thuongHieu = thuongHieuService.findByName(x.getThuongHieu());
            Xuatxu xuatxu = xuatXuService.findByName(x.getXuatXu());
            // end:

            // start: kiểm tra nếu 1 trong các đối tượng là null thì add vào list sản phẩm lỗi
            if (danhMuc == null || thuongHieu == null || xuatxu == null) {
                errorImports.add(x);
                System.out.println("case1");
                continue;
            }
            // end:

            // start: kiểm tra nếu trangthai là null thì add vào list sản phẩm lỗi
            if (utility.getNumberByNameStatus(x.getTrangThai()) == null) {
                errorImports.add(x);
                System.out.println("case4");
                continue;
            }
            // end:

            // start: Tạo mã cho sanpham
            String maSanPham = "SP" + (sanPhamrepo.findAll().size()+1);
            // end:

            // kiểm tra nếu tên sản phẩm không tồn tại thì thêm mới
            SanPham sanPham = this.findByName(x.getTenSanPham());
            if (sanPham == null) {
                SanPham sanPham1 = SanPham.builder()
                        .id(null)
                        .ma(maSanPham)
                        .ten(x.getTenSanPham())
                        .anhChinh(x.getAnhChinh())
                        .moTa(x.getMoTa())
                        .ngayTao(Date.valueOf(curruntDate))
                        .ngayCapNhat(null)
                        .trangThai(1)
                        .danhMuc(danhMuc)
                        .xuatXu(xuatxu)
                        .thuongHieu(thuongHieu)
                        .build();
                sanPhamrepo.save(sanPham1);
                System.out.println("case2");
                continue;
            }else{
                // nếu sanpham đã tồn tại thì cập nhật lại mota,danhmuc,xuatxu,thuonghieu
                SanPham sanPham2 = SanPham.builder()
                        .id(sanPham.getId())
                        .ma(sanPham.getMa())
                        .ten(x.getTenSanPham())
                        .anhChinh(sanPham.getAnhChinh())
                        .moTa(x.getMoTa())
                        .ngayTao(sanPham.getNgayTao())
                        .ngayCapNhat(Date.valueOf(curruntDate))
                        .trangThai(1)
                        .danhMuc(danhMuc)
                        .xuatXu(xuatxu)
                        .thuongHieu(thuongHieu)
                        .build();
                sanPhamrepo.save(sanPham2);
                System.out.println("case3");
                continue;
            }
        }
        return errorImports;
    }

    @Override
    public List<ExportExcelSanPham> exportExcel() {
        Integer index = 1;
        List<ExportExcelSanPham> exportExcelSanPhams = new ArrayList<>();// convert từ sanpham sang ExportExcelSanPham
        for (SanPham x: sanPhamrepo.findAll()){
            ExportExcelSanPham exportExcelSanPham = ExportExcelSanPham.builder()
                    .stt(index++)
                    .maSanPham(x.getMa())
                    .ten(x.getTen())
                    .anhChinh(x.getAnhChinh())
                    .moTa(x.getMoTa())
                    .trangThai(utility.trangThaiSanPham(x.getTrangThai()))
                    .thuongHieu(x.getThuongHieu().getTen())
                    .xuatXu(x.getXuatXu().getTen())
                    .danhMuc(x.getDanhMuc().getTen())
                    .build();
            exportExcelSanPhams.add(exportExcelSanPham);
        }
        return exportExcelSanPhams;
    }

    @Override
    public SanPham create(SanPhamRequest sanPhamRequest) {
        String maSanPham = "SP" + sanPhamrepo.findAll().size()+1;
        SanPham sanPham = SanPham.builder()
                .id(null)
                .ma(maSanPham)
                .ten(sanPhamRequest.getTen())
                .anhChinh(sanPhamRequest.getAnhChinh())
                .moTa(sanPhamRequest.getMoTa())
                .ngayTao(Date.valueOf(curruntDate))
                .ngayCapNhat(null)
                .trangThai(1)
                .thuongHieu(thuongHieuService.findById(sanPhamRequest.getThuongHieu()))
                .danhMuc(danhMucSevice.findById(sanPhamRequest.getDanhMuc()))
                .xuatXu(xuatXuService.findById(sanPhamRequest.getXuatXu()))
                .build();
        return sanPhamrepo.save(sanPham);

    }

    @Override
    public SanPham update(Integer id, SanPhamRequest sanPhamRequest) {

        SanPham sanPham = SanPham.builder()
                .id(id)
                .ma(this.findById(id).getMa())
                .ten(sanPhamRequest.getTen())
                .anhChinh(sanPhamRequest.getAnhChinh())
                .moTa(sanPhamRequest.getMoTa())
                .ngayTao(sanPhamrepo.findById(id).get().getNgayTao())
                .ngayCapNhat(Date.valueOf(curruntDate))
                .trangThai(sanPhamRequest.getTrangThai())
                .thuongHieu(thuongHieuService.findById(sanPhamRequest.getThuongHieu()))
                .danhMuc(danhMucSevice.findById(sanPhamRequest.getDanhMuc()))
                .xuatXu(xuatXuService.findById(sanPhamRequest.getXuatXu()))
                .build();
        return sanPhamrepo.save(sanPham);
    }

    @Override
    public SanPham delete(Integer id) {
        SanPham sanPham = this.findById(id);
        sanPham.setTrangThai(0);
        return sanPhamrepo.save(sanPham);
    }

}
