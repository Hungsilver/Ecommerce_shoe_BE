package com.example.projectshop.controller.rest;

import com.example.projectshop.service.INhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class NhanVienRestController {
//    @Autowired
//    private INhanVienService nhanVienService;
//
//    @GetMapping("/nhanvien")
//    private ResponseEntity<?> getAll(Pageable pageable) {
//        return ResponseEntity.ok(nhanVienService.getAll(pageable));
//    }
}

