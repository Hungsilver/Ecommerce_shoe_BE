package com.example.projectshop.controller.rest;

import com.example.projectshop.dto.danhmuc.ExcelDanhMuc;
import com.example.projectshop.dto.gioithieu.GioiThieuRequest;
import com.example.projectshop.dto.gioithieu.ImportExcelGioiThieu;
import com.example.projectshop.dto.thuonghieu.ThuongHieuRequest;
import com.example.projectshop.service.IGioiThieuService;
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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/blog")
public class GioiThieuRestController {

    @Autowired
    private IGioiThieuService gioiThieuService;

    private String p_chu = "\\d+";

    @GetMapping()//localhost:8080/api/blog
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
        return ResponseEntity.ok(gioiThieuService.findAll(Integer.valueOf(page),Integer.valueOf(pageSize),sortField,isSortDesc,keyword));
    }

    @PostMapping()//localhost:8080/api/blog
    public ResponseEntity<?> create(
            @RequestBody GioiThieuRequest gioiThieuRequest
    ) {
        return ResponseEntity.ok(gioiThieuService.create(gioiThieuRequest));
    }

    @GetMapping("{id}")//localhost:8080/api/blog/1
    public ResponseEntity<?> findById(@PathVariable String id) {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id giới thiệu phải là số");
        }
        return ResponseEntity.ok(gioiThieuService.findById(Integer.valueOf(id)));
    }


    @PutMapping("{id}")//localhost:8080/api/blog/1
    public ResponseEntity<?> update(
            @RequestBody GioiThieuRequest gioiThieuRequest,
            @PathVariable("id") String id
    ){
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id giới thiệu phải là số");
        }
        return ResponseEntity.ok(gioiThieuService.update(Integer.valueOf(id),gioiThieuRequest));
    }

    @DeleteMapping("{id}")//localhost:8080/api/blog/1
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id giới thiệu phải là số");
        }
        return ResponseEntity.ok(gioiThieuService.delete(Integer.valueOf(id)));
    }

    @GetMapping("/excel/export")//localhost:8080/api/blog/excel/export
    public  ResponseEntity<?> exportExcel() {
        return ResponseEntity.ok(gioiThieuService.exportExcel());
    }

    @PostMapping("/excel/import")//localhost:8080/api/blog/excel/import
    public  ResponseEntity<?> importExcel(@RequestBody List<ImportExcelGioiThieu> importExcelGioiThieus){
        return ResponseEntity.ok(gioiThieuService.importExcel(importExcelGioiThieus));
    }
}
