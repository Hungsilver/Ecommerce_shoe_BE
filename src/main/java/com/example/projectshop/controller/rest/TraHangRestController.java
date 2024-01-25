package com.example.projectshop.controller.rest;

import com.example.projectshop.dto.trahang.TraHangChiTietRequest;
import com.example.projectshop.dto.trahang.TraHangRequest;
import com.example.projectshop.dto.trahang.UpdateCTSP;
import com.example.projectshop.service.ITraHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/api/return-product")
public class TraHangRestController {
    @Autowired
    private ITraHangService traHangService;

    @GetMapping()//localhost:8080/api/return-product
    public ResponseEntity<?> findAll(
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "sortField", required = false, defaultValue = "id") String sortField,
            @RequestParam(value = "isSortDesc", required = false, defaultValue = "false") Boolean isSortDesc,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        return ResponseEntity.ok(traHangService.findAll(status, keyword, sortField, isSortDesc, page, pageSize));
    }

    @GetMapping("/{id}")//localhost:8080/api/return-product/1
    public ResponseEntity<?> findById(@PathVariable("id")Integer id) {
        return ResponseEntity.ok(traHangService.findById(id));
    }

    @GetMapping("/id-invoice/{idHoaDon}")//localhost:8080/api/return-product/id-invoice/1
    public ResponseEntity<?> findByIdHoaDon(@PathVariable("idHoaDon")Integer idHoaDon) {
        return ResponseEntity.ok(traHangService.findByIdHoaDon(idHoaDon));
    }

    @GetMapping("/status/{trangThai}")//localhost:8080/api/return-product/status/1
    public ResponseEntity<?> findAllByTrangThai(@PathVariable("trangThai")Integer trangThai) {
        return ResponseEntity.ok(traHangService.findAllByTrangThai(trangThai));
    }

    @GetMapping("/id-customer")//localhost:8080/api/return-product/id-customer
    public ResponseEntity<?> findByIdKhachHang() {
        return ResponseEntity.ok(traHangService.findByIdKhachHang());
    }

    @PostMapping()//localhost:8080/api/return-product
    public ResponseEntity<?> add(@RequestBody TraHangRequest traHangRequest) {
        return ResponseEntity.ok(traHangService.add(traHangRequest));
    }

    @PostMapping("/shop")//localhost:8080/api/return-product/shop
    public ResponseEntity<?> shopAdd(@RequestBody TraHangRequest traHangRequest) {
        return ResponseEntity.ok(traHangService.shopAdd(traHangRequest));
    }

    @PostMapping("/detail")//localhost:8080/api/return-product/detail
    public ResponseEntity<?> addTHCT(@RequestBody TraHangChiTietRequest traHangChiTietRequest) {
        return ResponseEntity.ok(traHangService.addTHCT(traHangChiTietRequest));
    }

    @PutMapping("/update-quantity")//localhost:8080/api/return-product/update-quantity
    public ResponseEntity<?> updateCTSP(@RequestBody List<UpdateCTSP> updateCTSPs) {
        traHangService.updateQuantity(updateCTSPs);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/update-total-payments/{id}/{tongTien}")//localhost:8080/api/return-product/update-total-payments/1/133
    public ResponseEntity<?> updatePayments(@PathVariable("tongTien")BigDecimal tongTien,
                                            @PathVariable("id")Integer id) {
        return ResponseEntity.ok(traHangService.updatePayment(id,tongTien));
    }

    @GetMapping("/update-ghi-chu/{id}/{ghiChu}")//localhost:8080/api/return-product/update-ghi-chu/1/abc
    public ResponseEntity<?> updateGhiChu(@PathVariable("ghiChu")String ghiChu,
                                            @PathVariable("id")Integer id) {
        return ResponseEntity.ok(traHangService.updateGhiChu(id,ghiChu));
    }

    @GetMapping("/update-status/{id}/{trangThai}")//localhost:8080/api/return-product/update-status/1/1
    public ResponseEntity<?> updateStatus(@PathVariable("id")Integer id,
                                          @PathVariable("trangThai")Integer trangThai) {
        return ResponseEntity.ok(traHangService.updateStatus(id,trangThai));
    }

    @DeleteMapping("/detail/{id}")//localhost:8080/api/return-product/detail/1
    public ResponseEntity<?> delelteTHCT(@PathVariable("id")Integer id) {
        return ResponseEntity.ok(traHangService.deleteTHCT(id));
    }

    @GetMapping("/detail/{id}/{soLuong}")//localhost:8080/api/return-product/detail/1/1
    public ResponseEntity<?> updateQuantityTHCT(@PathVariable("id")Integer id,
                                         @PathVariable("soLuong")Integer soLuong) {
        return ResponseEntity.ok(traHangService.updateQuantityTHCT(id,soLuong));
    }

    @GetMapping("/detail/{idHdct}")//localhost:8080/api/return-product/detail/1/1
    public ResponseEntity<?> findThctByIdHdct(@PathVariable("id")Integer id) {
        return ResponseEntity.ok(traHangService.findByIdTHCT(id));
    }

    @GetMapping("/ma-hoa-don")//localhost:8080/api/return-product/detail/1/1
    public ResponseEntity<?> findThctByIdHdct(@RequestParam(value = "maHoaDon",required = false)String maHoadon) {
        return ResponseEntity.ok(traHangService.findByMaHoaDon(maHoadon));
    }
}
