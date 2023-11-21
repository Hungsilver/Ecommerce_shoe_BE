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

    @GetMapping("/invoice")//localhost:8080/api/statistic/invoice
    public ResponseEntity<?> thongKeHoaDon(){
        return ResponseEntity.ok(thongKeService.thongKeHoaDon());
    }

    // thống kê doanh thu 7 ngày trước
    //localhost:8080/api/statistic/revenue/last-seven-day
    @GetMapping("/revenue/last-seven-day")
    public ResponseEntity<?> thongKeDoanhThu7NgayTruoc(){
        return ResponseEntity.ok(thongKeService.thongKeDoanhThu7NgayTruoc());
    }

    // thống kê doanh thu 28 ngày trước
    //localhost:8080/api/statistic/revenue/last-one-month
    @GetMapping("/revenue/last-one-month")
    public ResponseEntity<?> thongKeDoanhThu28NgayTruoc(){
        return ResponseEntity.ok(thongKeService.thongKeDoanhThu28NgayTruoc());
    }

    // thống kê doanh thu 6 tháng trước
    //localhost:8080/api/statistic/invoice/last-six-month
    @GetMapping("/revenue/last-six-month")
    public ResponseEntity<?> thongKeDoanhThu6ThangTruoc(){
        return ResponseEntity.ok(thongKeService.thongKeDoanhThu6ThangTruoc());
    }

    // thống kê doanh thu 1 năm trước
    //localhost:8080/api/statistic/revenue/last-year
    @GetMapping("/revenue/last-year")
    public ResponseEntity<?> thongKeDoanhThu1NamTruoc(){
        return ResponseEntity.ok(thongKeService.thongKeDoanhThu1NamTruoc());
    }

    // thống kê doanh thu theo khoản thời gian
    //localhost:8080/api/statistic/revenue...
    @GetMapping("/revenue")
    public ResponseEntity<?> thongKeDoanhThuTheoKhoang(
            @RequestParam(value = "startDate",required = false)Date startDate,
            @RequestParam(value = "endDate",required = false)Date endDate
            ){
        return ResponseEntity.ok(thongKeService.thongKeDoanhThuTheoKhoang(startDate,endDate));
    }

    // thống kê sản phẩm theo khoản thời gian
    //localhost:8080/api/statistic/product...
    @GetMapping("/product")
    public ResponseEntity<?> thongKeSanPhamTheoKhoang(
            @RequestParam(value = "startDate",required = false)Date startDate,
            @RequestParam(value = "endDate",required = false)Date endDate
    ){
        return ResponseEntity.ok(thongKeService.thongKeSanPhamTheoKhoang(startDate,endDate));
    }

    // thống kê sản phẩm 7 ngày trước
    //localhost:8080/api/statistic/product/last-seven-day
    @GetMapping("/product/last-seven-day")
    public ResponseEntity<?> thongKeSanPham7NgayTruoc(){
        return ResponseEntity.ok(thongKeService.thongKeSanPham7NgayTruoc());
    }

    // thống kê sản phẩm 28 ngày trước
    //localhost:8080/api/statistic/product/last-one-month
    @GetMapping("/product/last-one-month")
    public ResponseEntity<?> thongKeSanPham28NgayTruoc(){
        return ResponseEntity.ok(thongKeService.thongKeSanPham28NgayTruoc());
    }

    // thống kê sản phẩm 6 tháng trước
    //localhost:8080/api/statistic/product/last-six-month
    @GetMapping("/product/last-six-month")
    public ResponseEntity<?> thongKeSanPham6ThangTruoc(){
        return ResponseEntity.ok(thongKeService.thongKeSanPham6ThangTruoc());
    }

    // thống kê sản phẩm 1 năm trước
    //localhost:8080/api/statistic/product/last-year
    @GetMapping("/product/last-year")
    public ResponseEntity<?> thongKeSanPham1NamTruoc(){
        return ResponseEntity.ok(thongKeService.thongKeSanPham1NamTruoc());
    }

    // thống kê danh mục theo khoảng thời gian
    //localhost:8080/api/statistic/category...
    @GetMapping("/category")
    public ResponseEntity<?> thongKeDanhMucTheoKhoang(
            @RequestParam(value = "startDate",required = false)Date startDate,
            @RequestParam(value = "endDate",required = false)Date endDate
    ){
        return ResponseEntity.ok(thongKeService.thongKeDanhMucTheoKhoang(startDate,endDate));
    }

    // thống kê danh mục 7 ngày trước
    //localhost:8080/api/statistic/category/last-seven-day
    @GetMapping("/category/last-seven-day")
    public ResponseEntity<?> thongKeDanhMuc7NgayTruoc(){
        return ResponseEntity.ok(thongKeService.thongKeDanhMuc7NgayTruoc());
    }

    // thống kê danh mục 1 tháng trước
    //localhost:8080/api/statistic/category/last-one-month
    @GetMapping("/category/last-one-month")
    public ResponseEntity<?> thongKeDanhMuc28NgayTruoc(){
        return ResponseEntity.ok(thongKeService.thongKeDanhMuc28NgayTruoc());
    }

    // thống kê danh mục 6 tháng trước
    //localhost:8080/api/statistic/category/last-six-month
    @GetMapping("/category/last-six-month")
    public ResponseEntity<?> thongKeDanhMuc6ThangTruoc(){
        return ResponseEntity.ok(thongKeService.thongKeDanhMuc6ThangTruoc());
    }

    // thống kê danh mục 1 năm trước
    //localhost:8080/api/statistic/category/last-year
    @GetMapping("/category/last-year")
    public ResponseEntity<?> thongKeDanhMuc1NamTruoc(){
        return ResponseEntity.ok(thongKeService.thongKeDanhMuc1NamTruoc());
    }
}
