package com.example.projectshop.controller.rest;

import com.example.projectshop.dto.anhsanpham.ExcelASP;
import com.example.projectshop.service.IAnhSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/image-product")
public class AnhSanPhamRestController {
    @Autowired
    private IAnhSanPhamService anhSanPhamService;

    @PostMapping("/excel/import")//localhost:8080/api/image-product/excel/import
    public ResponseEntity<?> importExcel(@RequestBody List<ExcelASP> excelASPS) {
        return ResponseEntity.ok(anhSanPhamService.importExcel(excelASPS));
    }

    @GetMapping("/excel/export")//localhost:8080/api/image-product/excel/export
    public ResponseEntity<?> exportExcel() {
        return ResponseEntity.ok(anhSanPhamService.exportExcel());
    }
}
