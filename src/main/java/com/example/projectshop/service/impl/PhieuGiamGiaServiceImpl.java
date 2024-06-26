package com.example.projectshop.service.impl;

import com.example.projectshop.domain.HoaDon;
import com.example.projectshop.domain.PhieuGiamGia;
import com.example.projectshop.dto.phieugiamgia.ExportExcelPGG;
import com.example.projectshop.dto.phieugiamgia.ImportExcelPGG;
import com.example.projectshop.dto.phieugiamgia.PhieuGiamGiaRequest;
import com.example.projectshop.repository.PhieuGiamGiaRepository;
import com.example.projectshop.service.IPhieuGiamGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

@Service
public class PhieuGiamGiaServiceImpl implements IPhieuGiamGiaService {

    @Autowired
    private PhieuGiamGiaRepository phieuGiamGiaRepository;

    // lấy ra ngày hiện tại
    private LocalDateTime curruntDate = LocalDateTime.now();
    private  LocalDate datenow = LocalDate.now();
    private LocalTime timenow = LocalTime.now();

    // update thoi gian bat dau
//    @Scheduled(fixedRate = 20000,initialDelay = 10000) // sau 20s
//    @Scheduled(cron = "0 */10 * * * ?") // 10m chay 1 lan
//    public void UpdateVoucherStatus(){
//        List<PhieuGiamGia> phieuGiamGiaList = phieuGiamGiaRepository.findAll();
//        for (PhieuGiamGia x:phieuGiamGiaList) {

//            if (x.getThoiGianBatDau().before(Date.valueOf(curruntDate))){
//              continue;
//            } else if (x.getThoiGianBatDau().equals(curruntDate)){
//                x.setTrangThai(1);
//                phieuGiamGiaRepository.save(x);
//            }
//        }
//    }


    @Override
    public Page<PhieuGiamGia> findAll(Integer page,
                                      Integer pageSize,
                                      String sortField,
                                      Boolean isSortDesc,
                                      String keyword) {

        System.out.println("time now:"+ curruntDate);
        for (PhieuGiamGia x : phieuGiamGiaRepository.findAll()) {
            LocalDateTime startTime = x.getThoiGianBatDau().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();  // Chuyển đổi Date sang ZonedDateTime
//            LocalDate startdate = x.getThoiGianBatDau().toInstant().atZone()
            LocalDateTime endTime = x.getThoiGianKetThuc().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            // mặc định là sắp diễn ra
            // update ngày bắt đầu đến ngày hiện tại, tự update
            // TH dang diễn ra
            if( ( startTime.isBefore(curruntDate) || startTime.equals(curruntDate)) && endTime.isAfter(curruntDate)){
                System.out.println("time start");
                            x.setTrangThai(1);
                            phieuGiamGiaRepository.save(x);
                            continue;

            } else if (endTime.isBefore((curruntDate)) && startTime.isBefore(curruntDate)) {
                System.out.println("if ket thuc");
                // thời gian kết thúc nhỏ hơn ngày hiện tại => cập nhật lại
                PhieuGiamGia phieuGiamGia = PhieuGiamGia.builder()
                        .id(x.getId())
                        .ma(x.getMa())
                        .ten(x.getTen())
                        .chietKhau(x.getChietKhau())
                        .hinhThucGiamGia(x.getHinhThucGiamGia())
                        .thoiGianBatDau(x.getThoiGianBatDau())
                        .thoiGianKetThuc(x.getThoiGianKetThuc())
                        .moTa(x.getMoTa())
                        .trangThai(0)
                        .build();
                phieuGiamGiaRepository.save(phieuGiamGia);
                System.out.println("if 2");
//            continue;
            } else {
                System.out.println("if 5");
                // thời gian kết thúc trùng với ngày hiện tại
                continue;
            }

        }

        Sort sort = Sort.by(isSortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, pageSize, sort);
        if (keyword != null && !keyword.isEmpty()) {
            return phieuGiamGiaRepository.findAllByMa(keyword, pageable);
        } else {
            return phieuGiamGiaRepository.findAll(pageable);
        }


    }

    @Override
    public PhieuGiamGia findById(Integer id) {

        if (id != null) {
            return phieuGiamGiaRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public PhieuGiamGia findByCode(String ma) {
        Optional<PhieuGiamGia> phieuGiamGia = phieuGiamGiaRepository.findByMa(ma);
        if (phieuGiamGia.isPresent()){
            return phieuGiamGia.get();
        }
        return null;
    }

    @Override
    public PhieuGiamGia findByName(String name) {
        Optional<PhieuGiamGia> phieuGiamGia = phieuGiamGiaRepository.findByName(name);
        if (phieuGiamGia.isPresent()) {
            return phieuGiamGia.get();
        }
        return null;
    }

    @Override
    public List<ImportExcelPGG> importExcel(List<ImportExcelPGG> importExcelPGGS) {
        // tạo list để chứa các đối tượng bị lỗi
        List<ImportExcelPGG> errorImports = new ArrayList<>();

        // tạo mã phiếu giảm giá
        String maPhieuGiamGia = "SP" + phieuGiamGiaRepository.findAll().size() + 1;

        Boolean hinhThucGiamGia;
        // sử dụng for để lặp các đối tượng được truyền vào từ file excel
        for (ImportExcelPGG x : importExcelPGGS) {
            // chuyển đổi hinhthucgiamgia từ string => number
            if (x.getHinhThucGiamGia().equalsIgnoreCase("Phần Trăm")) {
                hinhThucGiamGia = true;
            } else {
                hinhThucGiamGia = false;
            }

            // kiểm nếu tên truyền vào null || space add vào errorImports
            if (x.getTieuDe() == null || x.getTieuDe().isBlank()) {
                errorImports.add(x);
                System.out.println("case 1");
                continue;
            }


            // lấy ra đối tượng theo tên được truyền vào từ file excel
            PhieuGiamGia phieuGiamGia = this.findByName(x.getTieuDe());
            // kiểm tra nếu chưa có trong db thì thêm mới
            if (phieuGiamGia == null) {
                PhieuGiamGia phieuGiamGia1 = PhieuGiamGia.builder()
                        .id(null)
                        .ma(maPhieuGiamGia)
                        .ten(x.getTieuDe())
                        .chietKhau(new BigDecimal(x.getChietKhau()))
                        .hinhThucGiamGia(hinhThucGiamGia)
                        .thoiGianBatDau(Date.valueOf(x.getThoiGianBatDau()))
                        .thoiGianKetThuc(Date.valueOf(x.getThoiGianKetThuc()))
                        .moTa(x.getMoTa())
                        .trangThai(1)
                        .build();
                phieuGiamGiaRepository.save(phieuGiamGia1);
            } else {// nếu đã có thì cập nhật lại thông tin
                PhieuGiamGia phieuGiamGia2 = PhieuGiamGia.builder()
                        .id(phieuGiamGia.getId())
                        .ma(phieuGiamGia.getMa())
                        .ten(x.getTieuDe())
                        .chietKhau(new BigDecimal(x.getChietKhau()))
                        .hinhThucGiamGia(hinhThucGiamGia)
                        .thoiGianBatDau(Date.valueOf(x.getThoiGianBatDau()))
                        .thoiGianKetThuc(Date.valueOf(x.getThoiGianKetThuc()))
                        .moTa(x.getMoTa())
                        .trangThai(1)
                        .build();
                phieuGiamGiaRepository.save(phieuGiamGia2);
            }
        }
        return errorImports;
    }

    @Override
    public List<ExportExcelPGG> exportExcel() {
        List<ExportExcelPGG> exportExcelPGGS = new ArrayList<>();
        Integer index = 1;
        String hinhThucGiamGia;
        String trangThai;
        for (PhieuGiamGia x : phieuGiamGiaRepository.findAll()) {// convert từ PhieuGiamGia sang ExportExcelPGG
            if (x.getHinhThucGiamGia() == null) {
                hinhThucGiamGia = null;
            } else if (x.getHinhThucGiamGia() == true) {
                hinhThucGiamGia = "Phần Trăm";
            } else {
                hinhThucGiamGia = "Tiền";
            }

            if (x.getTrangThai() == 0) {
                trangThai = "Hoạt Động";
            } else {
                trangThai = "Không Hoạt Động";
            }
            ExportExcelPGG exportExcelPGG = ExportExcelPGG.builder()
                    .stt(index++)
                    .maPhieuGiamGia(x.getMa())
                    .tieuDe(x.getTen())
                    .chietKhau(x.getChietKhau())
                    .hinhThucGiamGia(hinhThucGiamGia)
                    .thoiGianBatDau(x.getThoiGianBatDau())
                    .thoiGianKetThuc(x.getThoiGianKetThuc())
                    .moTa(x.getMoTa())
                    .trangThai(trangThai)
                    .build();
            exportExcelPGGS.add(exportExcelPGG);
        }
        return exportExcelPGGS;
    }

    @Override
    public PhieuGiamGia create(PhieuGiamGiaRequest phieuGiamGiaRequest) {
//        String ma = "PGG" + (phieuGiamGiaRepository.findAll().size() + 1);
        String manew = "PGG" + UUID.randomUUID().toString().substring(0,4);
        System.out.println("ket thuc:"+phieuGiamGiaRequest.getThoiGianKetThuc());
        PhieuGiamGia phieuGiamGia = PhieuGiamGia.builder()
                .id(null)
                .ma(manew)
                .ten(phieuGiamGiaRequest.getTen())
                .chietKhau(phieuGiamGiaRequest.getChietKhau())
                .hinhThucGiamGia(phieuGiamGiaRequest.getHinhThucGiamGia())
                .thoiGianBatDau(phieuGiamGiaRequest.getThoiGianBatDau())
                .thoiGianKetThuc(phieuGiamGiaRequest.getThoiGianKetThuc())
                .moTa(phieuGiamGiaRequest.getMoTa())
                .trangThai(2)
                .build();
        return phieuGiamGiaRepository.save(phieuGiamGia);
    }

    @Override
    public PhieuGiamGia update(Integer id, PhieuGiamGiaRequest phieuGiamGiaRequest) {
        PhieuGiamGia phieuGiamGia = PhieuGiamGia.builder()
                .id(id)
                .ma(this.findById(id).getMa())
                .ten(phieuGiamGiaRequest.getTen())
                .chietKhau(phieuGiamGiaRequest.getChietKhau())
                .hinhThucGiamGia(phieuGiamGiaRequest.getHinhThucGiamGia())
                .thoiGianBatDau(phieuGiamGiaRequest.getThoiGianBatDau())
                .thoiGianKetThuc(phieuGiamGiaRequest.getThoiGianKetThuc())
                .moTa(phieuGiamGiaRequest.getMoTa())
                .trangThai(phieuGiamGiaRequest.getTrangThai())
                .build();
        return phieuGiamGiaRepository.save(phieuGiamGia);
    }

    @Override
    public PhieuGiamGia delete(Integer id) {
        Optional<PhieuGiamGia> phieuGiamGia = phieuGiamGiaRepository.findById(id);
        if (phieuGiamGia.isPresent()) {
            phieuGiamGia.get().setTrangThai(0);
            return phieuGiamGiaRepository.save(phieuGiamGia.get());
        }
        return null;
    }



}
