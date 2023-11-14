package com.example.projectshop.controller.rest;

import com.example.projectshop.repository.ThongKeRepository;
import com.example.projectshop.service.IThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/statistic")
public class ThongKeRestController {
    @Autowired
    private IThongKeService thongKeService;

    @Autowired
    ThongKeRepository thongKeRepository;

    @GetMapping("/invoice")
    public ResponseEntity<?> thongKeHoaDon(){
        return ResponseEntity.ok(thongKeService.thongKeHoaDon());
    }

    @GetMapping("/revenue/last-seven-day")
    public ResponseEntity<?> thongKeDoanhThu7NgayTruoc(){
        return ResponseEntity.ok(thongKeService.thongKeDoanhThu7NgayTruoc());
    }

    @GetMapping("/revenue/last-one-month")
    public ResponseEntity<?> thongKeDoanhThu28NgayTruoc(){
        return ResponseEntity.ok(thongKeService.thongKeDoanhThu28NgayTruoc());
    }

    @GetMapping("/revenue/last-six-month")
    public ResponseEntity<?> thongKeDoanhThu6ThangTruoc(){
        return ResponseEntity.ok(thongKeService.thongKeDoanhThu6ThangTruoc());
    }

    @GetMapping("/revenue/last-year")
    public ResponseEntity<?> thongKeDoanhThu1NamTruoc(){
        return ResponseEntity.ok(thongKeService.thongKeDoanhThu1NamTruoc());
    }

    @GetMapping("/revenue")
    public ResponseEntity<?> thongKeDoanhThuTheoKhoang(
            @RequestParam(value = "startDate",required = false)Date startDate,
            @RequestParam(value = "endDate",required = false)Date endDate
            ){
        return ResponseEntity.ok(thongKeService.thongKeDoanhThuTheoKhoang(startDate,endDate));
    }
}
