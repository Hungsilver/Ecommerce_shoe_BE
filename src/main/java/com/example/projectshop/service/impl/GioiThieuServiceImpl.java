package com.example.projectshop.service.impl;

import com.example.projectshop.domain.GioiThieu;
import com.example.projectshop.dto.gioithieu.ExportExcelGioiThieu;
import com.example.projectshop.dto.gioithieu.ImportExcelGioiThieu;
import com.example.projectshop.dto.gioithieu.GioiThieuRequest;
import com.example.projectshop.repository.GioiThieuRepository;
import com.example.projectshop.service.IGioiThieuService;
import com.example.projectshop.service.INhanVienService;
import com.example.projectshop.utilities.utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GioiThieuServiceImpl implements IGioiThieuService {
    @Autowired
    private GioiThieuRepository gioiThieuRepo;

    @Autowired
    private INhanVienService nhanVienService;

    // lấy ra ngày hiện tại
    private LocalDate curruntDate = LocalDate.now();

    @Override
    public Page<GioiThieu> findAll(Integer page,
                                   Integer pageSize,
                                   String sortField,
                                   Boolean isSortDesc,
                                   String keyword) {
        Sort sort = Sort.by(isSortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, pageSize, sort);
        if (keyword != null && !keyword.isEmpty()) {
            return gioiThieuRepo.findAllByName(keyword, pageable);
        } else {
            return gioiThieuRepo.findAll(pageable);
        }
    }

    @Override
    public Optional<GioiThieu> findById(Integer id) {
        return gioiThieuRepo.findById(id);
    }

    @Override
    public GioiThieu findByName(String name) {
        Optional<GioiThieu> gioiThieu = gioiThieuRepo.findByName(name);
        if (gioiThieu.isPresent()) {
            return gioiThieu.get();
        }
        return null;
    }

    @Override
    public List<ImportExcelGioiThieu> importExcel(List<ImportExcelGioiThieu> importExcelGioiThieus) {
        // tạo list để chứa các đối tượng bị lỗi
        List<ImportExcelGioiThieu> errorImports = new ArrayList<>();
        // sử dụng for để lặp các đối tượng được truyền vào từ file excel
        for (ImportExcelGioiThieu x : importExcelGioiThieus) {
            // lấy ra đối tượng theo tên được truyền vào từ file excel
            GioiThieu gioiThieu = this.findByName(x.getTieuDe());

            // kiểm nếu tên truyền vào null || space add vào errorImports
            if (x.getTieuDe() == null || x.getTieuDe().isBlank()) {
                errorImports.add(x);
                System.out.println("case 1");
                continue;
            }

            // kiểm nếu nhân viên null  add vào errorImports
            if (nhanVienService.findByName(x.getNhanVien())== null) {
                errorImports.add(x);
                System.out.println("case 2");
                continue;
            }

            // kiểm tra nếu chưa có trong db thì thêm mới
            if (gioiThieu == null) {
                GioiThieu gioiThieu1 = GioiThieu.builder()
                        .id(null)
                        .tenGioiThieu(x.getTieuDe())
                        .noiDung(x.getNoiDung())
                        .logo(x.getLogo())
                        .banner(x.getBanner())
                        .moTa(x.getMoTa())
                        .ngayTao(Date.valueOf(curruntDate))
                        .ngayXoa(null)
                        .trangThai(1)
                        .nhanVien(nhanVienService.findByName(x.getNhanVien()))
                        .build();
                gioiThieuRepo.save(gioiThieu1);
            } else {// nếu đã có thì cập nhật lại thông tin
                GioiThieu gioiThieu2 = GioiThieu.builder()
                        .id(gioiThieu.getId())
                        .tenGioiThieu(x.getTieuDe())
                        .noiDung(x.getNoiDung())
                        .logo(x.getLogo())
                        .banner(x.getBanner())
                        .moTa(x.getMoTa())
                        .ngayTao(gioiThieu.getNgayTao())
                        .ngayXoa(null)
                        .trangThai(gioiThieu.getTrangThai())
                        .nhanVien(nhanVienService.findByName(x.getNhanVien()))
                        .build();
                gioiThieuRepo.save(gioiThieu2);
            }
        }
        return errorImports;
    }

    @Override
    public List<ExportExcelGioiThieu> exportExcel() {
        List<ExportExcelGioiThieu> exportExcelGioiThieus = new ArrayList<>();
        Integer index = 1;
        for (GioiThieu x : gioiThieuRepo.findAll()) {// convert từ GioiThieu sang ExportExcelGioiThieu
            ExportExcelGioiThieu exportExcelGioiThieu = ExportExcelGioiThieu.builder()
                    .stt(index++)
                    .tieuDe(x.getTenGioiThieu())
                    .noiDung(x.getNoiDung())
                    .logo(x.getLogo())
                    .banner(x.getBanner())
                    .moTa(x.getMoTa())
                    .ngayTao(x.getNgayTao())
                    .ngayXoa(x.getNgayXoa())
                    .trangThai(utility.trangThaiSanPham(x.getTrangThai()))
                    .nhanVien(x.getNhanVien().getHoTen())
                    .build();
            exportExcelGioiThieus.add(exportExcelGioiThieu);
        }
        return exportExcelGioiThieus;
    }

    @Override
    public GioiThieu create(GioiThieuRequest gioiThieuRequest) {
        GioiThieu gioiThieu = GioiThieu.builder()
                .id(null)
                .tenGioiThieu(gioiThieuRequest.getTenGioiThieu())
                .noiDung(gioiThieuRequest.getNoiDung())
                .logo(gioiThieuRequest.getLogo())
                .banner(gioiThieuRequest.getBanner())
                .moTa(gioiThieuRequest.getMoTa())
                .ngayTao(Date.valueOf(curruntDate))
                .ngayXoa(null)
                .trangThai(1)
                .nhanVien(nhanVienService.findById(gioiThieuRequest.getNhanVien()))
                .build();
        return gioiThieuRepo.save(gioiThieu);
    }

    @Override
    public GioiThieu update(Integer id, GioiThieuRequest gioiThieuRequest) {
        GioiThieu gioiThieu = GioiThieu.builder()
                .id(id)
                .tenGioiThieu(gioiThieuRequest.getTenGioiThieu())
                .noiDung(gioiThieuRequest.getNoiDung())
                .logo(gioiThieuRequest.getLogo())
                .banner(gioiThieuRequest.getBanner())
                .moTa(gioiThieuRequest.getMoTa())
                .ngayTao(Date.valueOf(curruntDate))
                .ngayXoa(null)
                .trangThai(gioiThieuRequest.getTrangThai())
                .nhanVien(nhanVienService.findById(gioiThieuRequest.getNhanVien()))
                .build();
        return gioiThieuRepo.save(gioiThieu);
    }

    @Override
    public GioiThieu delete(Integer id) {
        Optional<GioiThieu> gioiThieu = this.findById(id);
        if (gioiThieu.isPresent()) {
            gioiThieu.get().setTrangThai(0);
            return gioiThieuRepo.save(gioiThieu.get());
        }
        return null;
    }
}
