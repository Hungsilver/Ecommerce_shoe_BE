package com.example.projectshop.controller.rest;

import com.example.projectshop.dto.hoadon.HoaDonRequest;
import com.example.projectshop.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/api/invoice")
public class HoaDonRestController {
    @Autowired
    private HoaDonService hoaDonService;

    @GetMapping()
    public ResponseEntity<?> findAll(
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "sortField", required = false, defaultValue = "id") String sortField,
            @RequestParam(value = "isSortDesc", required = false, defaultValue = "false") Boolean isSortDesc,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        return ResponseEntity.ok(hoaDonService.findAll(status, keyword, sortField, isSortDesc, page, pageSize));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable("id")Integer id) {
        return ResponseEntity.ok(hoaDonService.findById(id));
    }

    @PostMapping("/shop/checkout")
    public ResponseEntity<?> shopCheckout(@RequestBody HoaDonRequest hoaDonRequest) {
        return ResponseEntity.ok(hoaDonService.shopCheckout(hoaDonRequest));
    }

    @PostMapping("/online/checkout")
    public ResponseEntity<?> onlineCheckout(@RequestBody HoaDonRequest hoaDonRequest) {
        return ResponseEntity.ok(hoaDonService.onlineCheckout(hoaDonRequest));
    }

    @PostMapping("/cho-van-chuyen/{id}")
    public ResponseEntity<?> choVanChuyen(@PathVariable("id")Integer id) {
        return ResponseEntity.ok(hoaDonService.choVanChuyen(id));
    }

    @PostMapping("/dang-giao/{id}")
    public ResponseEntity<?> dangGiao(@PathVariable("id")Integer id) {
        return ResponseEntity.ok(hoaDonService.dangGiao(id));
    }

    @PostMapping("/da-giao/{id}")
    public ResponseEntity<?> daGiao(@PathVariable("id")Integer id) {
        return ResponseEntity.ok(hoaDonService.daGiao(id));
    }

    @PostMapping("/da-huy/{id}")
    public ResponseEntity<?> daHuy(@PathVariable("id")Integer id) {
        return ResponseEntity.ok(hoaDonService.daHuy(id));
    }

    @PostMapping("/tra-hang/{id}")
    public ResponseEntity<?> traHang(@PathVariable("id")Integer id) {
        return ResponseEntity.ok(hoaDonService.traHang(id));
    }


}
