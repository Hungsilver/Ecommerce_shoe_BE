package com.example.projectshop.controller.rest;

import com.example.projectshop.dto.danhmuc.ExcelDanhMuc;
import com.example.projectshop.dto.phieugiamgia.ImportExcelPGG;
import com.example.projectshop.dto.phieugiamgia.PhieuGiamGiaRequest;
import com.example.projectshop.service.IPhieuGiamGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/voucher")
public class PhieuGiamGiaRestController {

    @Autowired
    private IPhieuGiamGiaService phieuGiamGiaService;

    private String p_chu = "\\d+";

    @GetMapping()//localhost:8080/api/voucher
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") String pageSize,
            @RequestParam(value = "sortField", required = false, defaultValue = "id") String sortField,
            @RequestParam(value = "isSortDesc", required = false, defaultValue = "false") Boolean isSortDesc,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        if (!page.matches(p_chu)|| !pageSize.matches(p_chu)){
            return ResponseEntity.ok("*page || pageSize phải là số");
        }
        return ResponseEntity.ok(phieuGiamGiaService.findAll(Integer.valueOf(page),Integer.valueOf(pageSize),sortField,isSortDesc,keyword));
    }

    @PostMapping()//localhost:8080/api/voucher
    public ResponseEntity<?> create(
            @RequestBody PhieuGiamGiaRequest phieuGiamGiaRequest
    ) {
        return ResponseEntity.ok(phieuGiamGiaService.create(phieuGiamGiaRequest));
    }

    @GetMapping("{id}")//localhost:8080/api/voucher/1
    public ResponseEntity<?> findById(@PathVariable String id) {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id phiếu giảm giá phải là số");
        }
        return ResponseEntity.ok(phieuGiamGiaService.findById(Integer.valueOf(id)));
    }


    @PutMapping("{id}")//localhost:8080/api/voucher/1
    public ResponseEntity<?> update(
            @RequestBody PhieuGiamGiaRequest phieuGiamGiaRequest,
            @PathVariable("id") String id
    ){
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id phiếu giảm giá phải là số");
        }
        return ResponseEntity.ok(phieuGiamGiaService.update(Integer.valueOf(id),phieuGiamGiaRequest));
    }

    @DeleteMapping("{id}")//localhost:8080/api/voucher/1
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id phiếu giảm giá phải là số");
        }
        return ResponseEntity.ok(phieuGiamGiaService.delete(Integer.valueOf(id)));
    }

    @GetMapping("/excel/export")//localhost:8080/api/voucher/excel/export
    public  ResponseEntity<?> exportExcel() {
        return ResponseEntity.ok(phieuGiamGiaService.exportExcel());
    }

    @PostMapping("/excel/import")//localhost:8080/api/voucher/excel/import
    public  ResponseEntity<?> importExcel(@RequestBody List<ImportExcelPGG> importExcelPGGS){
        return ResponseEntity.ok(phieuGiamGiaService.importExcel(importExcelPGGS));
    }
}
