package com.example.projectshop.controller.rest;

import com.example.projectshop.dto.thuonghieu.ExcelThuongHieu;
import com.example.projectshop.dto.thuonghieu.ThuongHieuRequest;
import com.example.projectshop.service.IThuongHieuService;
import com.google.zxing.WriterException;
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

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/brand")
public class ThuongHieuRestController {

    @Autowired
    private IThuongHieuService service;

    private String p_chu = "\\d+";

    @GetMapping()// localhost:8080/api/brand...
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") String pageSize,
            @RequestParam(value = "sortField", required = false, defaultValue = "id") String sortField,
            @RequestParam(value = "isSortDesc", required = false, defaultValue = "false") Boolean isSortDesc,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        if (!page.matches(p_chu) || !pageSize.matches(p_chu)) {
            return ResponseEntity.ok("*page || pageSize phải là số");
        }
        return ResponseEntity.ok(service.findAll(Integer.valueOf(page), Integer.valueOf(pageSize), sortField, isSortDesc, keyword));
    }

    @PostMapping()//localhost:8080/api/brand
    public ResponseEntity<?> create(
            @RequestBody ThuongHieuRequest thuongHieuRequest
    ) throws IOException, WriterException {
        return ResponseEntity.ok(service.create(thuongHieuRequest));
    }

    @GetMapping("{id}")//localhost:8080/api/brand/1
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        if (!id.matches(p_chu)) {
            return ResponseEntity.ok("*id thương hiệu phải là số");
        }
        return ResponseEntity.ok(service.findById(Integer.valueOf(id)));
    }


    @PutMapping("{id}")//localhost:8080/api/brand/1
    public ResponseEntity<?> update(
            @PathVariable("id") String id,
            @RequestBody ThuongHieuRequest thuongHieuRequest

    ) {
        if (!id.matches(p_chu)) {
            return ResponseEntity.ok("*id thương hiệu phải là số");
        }
        return ResponseEntity.ok(service.update(Integer.valueOf(id), thuongHieuRequest));
    }

    @DeleteMapping("{id}")//localhost:8080/api/brand/1
    public ResponseEntity<?> delete(@PathVariable("id") String id) throws IOException, WriterException {
        if (!id.matches(p_chu)) {
            return ResponseEntity.ok("*id thương hiệu phải là số");
        }
        return ResponseEntity.ok(service.delete(Integer.valueOf(id)));
    }

    @GetMapping("/excel/export")//localhost:8080/api/brand/excel/export
    public ResponseEntity<?> exportExcel() {
        return ResponseEntity.ok(service.exportExcel());
    }

    @PostMapping("/excel/import")//localhost:8080/api/brand/excel/import
    public ResponseEntity<?> importExcel(@RequestBody List<ExcelThuongHieu> excelThuongHieus) {
        return ResponseEntity.ok(service.importExcel(excelThuongHieus));
    }
}
