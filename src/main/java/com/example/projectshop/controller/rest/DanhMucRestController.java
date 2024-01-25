package com.example.projectshop.controller.rest;

import com.example.projectshop.domain.DanhMuc;
import com.example.projectshop.dto.chatlieudegiay.ExcelCLDG;
import com.example.projectshop.dto.danhmuc.DanhMucRequest;
import com.example.projectshop.dto.danhmuc.ExcelDanhMuc;
import com.example.projectshop.service.IDanhMucSevice;
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
@RequestMapping("/api/category")
public class DanhMucRestController {

    @Autowired
    private IDanhMucSevice danhMucSevice;

    private String p_chu = "\\d+";

    @GetMapping()//localhost:8080/api/category
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") String pageSize,
            @RequestParam(value = "sortField", required = false, defaultValue = "id") String sortField,
            @RequestParam(value = "isSortDesc", required = false, defaultValue = "false") Boolean isSortDesc,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        if (!page.matches(p_chu)|| !pageSize.matches(p_chu)){
            return ResponseEntity.ok("*page || pageSize phải danh mục là số");
        }
        return ResponseEntity.ok(danhMucSevice.findAll(Integer.valueOf(page), Integer.valueOf(pageSize), sortField, isSortDesc, keyword));
    }

    @PostMapping()//localhost:8080/api/category
    public ResponseEntity<?> create(
            @RequestBody DanhMucRequest danhMucRequest
    ) {
        return ResponseEntity.ok(danhMucSevice.create(danhMucRequest));
    }

    @GetMapping("{id}")//localhost:8080/api/category/1
    public ResponseEntity<?> findById(@PathVariable String id) {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id danh mục phải là số");
        }
        return ResponseEntity.ok(danhMucSevice.findById(Integer.valueOf(id)));
    }


    @PutMapping("{id}")//localhost:8080/api/category/1
    public ResponseEntity<?> update(
            @RequestBody DanhMucRequest danhMucRequest,
            @PathVariable("id") String id
    ) {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id danh mục phải là số");
        }
        return ResponseEntity.ok(danhMucSevice.update(Integer.valueOf(id), danhMucRequest));
    }

    @DeleteMapping("{id}")//localhost:8080/api/category/1
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id danh mục phải là số");
        }
        return ResponseEntity.ok(danhMucSevice.delete(Integer.valueOf(id)));
    }

    @GetMapping("/excel/export")//localhost:8080/api/category/excel/export
    public  ResponseEntity<?> exportExcel() {
        return ResponseEntity.ok(danhMucSevice.exportExcel());
    }

    @PostMapping("/excel/import")//localhost:8080/api/category/excel/import
    public  ResponseEntity<?> importExcel(@RequestBody List<ExcelDanhMuc> excelDanhMucs){
        return ResponseEntity.ok(danhMucSevice.importExcel(excelDanhMucs));
    }
}
