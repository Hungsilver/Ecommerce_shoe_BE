package com.example.projectshop.controller.rest;

import com.example.projectshop.service.IGhiChuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/ghi-chu")
public class GhiChuRestController {

    @Autowired
    private IGhiChuService ghiChuService;

    @GetMapping()
    public ResponseEntity<?> findByIdHoaDon(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "idHoaDon", required = false) Integer idHoaDon
    ) {
        return ResponseEntity.ok(ghiChuService.findByIdHoaDon(page, pageSize, idHoaDon));
    }

    @GetMapping("/add")
    public ResponseEntity<?> add(
            @RequestParam(name = "ghiChu", required = false) String ghiChu,
            @RequestParam(value = "idHoaDon", required = false) Integer idHoaDon,
            @RequestParam(value = "trangThai", required = false) Integer trangThai) {
        return ResponseEntity.ok(ghiChuService.add(idHoaDon, ghiChu,trangThai));
    }
}
