package com.example.projectshop.controller.rest;

import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamRequest;
import com.example.projectshop.dto.chitietsanpham.ImportExcelCTSP;
import com.example.projectshop.service.IChiTietSanPhamService;
import com.google.zxing.WriterException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
import java.util.Map;

import java.io.IOException;
import java.util.HashMap;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/api/product-detail")

public class ChiTietSanPhamRestController {

    @Autowired
    private IChiTietSanPhamService chiTietSanPhamService;

    private String p_chu = "\\d+";


    @GetMapping()//localhost:8080/api/product-detail
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") String pageSize,
            @RequestParam(value = "sortField", required = false, defaultValue = "id") String sortField,
            @RequestParam(value = "isSortDesc", required = false, defaultValue = "false") Boolean isSortDesc
    ) {
        if (!page.matches(p_chu)|| !pageSize.matches(p_chu)){
            return ResponseEntity.ok("*page || pageSize phải là số");
        }
        return ResponseEntity.ok(chiTietSanPhamService.findAll(Integer.valueOf(page), Integer.valueOf(pageSize), sortField, isSortDesc));
    }

    @GetMapping("filter")//localhost:8080/api/product-detail/filter
    public ResponseEntity<?> filter(
            @RequestParam(value = "pricemin", required = false) String pricemin,
            @RequestParam(value = "pricemax", required = false) String pricemax,
            @RequestParam(value = "color", required = false) String color,
            @RequestParam(value = "shoe-material", required = false) String shoe_material,
            @RequestParam(value = "shoe-sole-material", required = false) String shoe_sole_material,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "isSortDesc", required = false, defaultValue = "false") Boolean isSortDesc,
            @RequestParam(value = "sortField", required = false, defaultValue = "id") String sortField,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        return ResponseEntity.ok(chiTietSanPhamService.filter(pricemin, pricemax, color, shoe_material, shoe_sole_material, keyword, isSortDesc, sortField, page, pageSize));
    }

    @GetMapping("{id}")//localhost:8080/api/product-detail/1
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id chi tiết sản phẩm phải là số");
        }
        return ResponseEntity.ok(chiTietSanPhamService.findById(Integer.valueOf(id)));
    }

    @GetMapping("/code/{ma}")//localhost:8080/api/product-detail/code/abc
    public ResponseEntity<?> findByMa(@PathVariable("ma") String ma) {
        return ResponseEntity.ok(chiTietSanPhamService.findByMa(ma));
    }

    @GetMapping("/findByMa")
    public ResponseEntity<?> findByMa1(@RequestParam String ma) {
        return ResponseEntity.ok(chiTietSanPhamService.findByMa_ProductDetail(ma));
    }

    @GetMapping("/id-product/{idSanPham}")//localhost:8080/api/product-detail/id-product/1
    public ResponseEntity<?> findByIdSanPham(@PathVariable("idSanPham") String idSanPham) {
        if (!idSanPham.matches(p_chu)){
            return ResponseEntity.ok("*idSanPham chi tiết sản phẩm phải là số");
        }
        return ResponseEntity.ok(chiTietSanPhamService.findById(Integer.valueOf(idSanPham)));
    }

    @PostMapping("")//localhost:8080/api/product-detail
    public ResponseEntity<?> create(@RequestBody @Valid ChiTietSanPhamRequest chiTietSanPhamRequest,
                                    BindingResult result) throws IOException, WriterException {
        if (result.hasErrors()) {
            Map<String, String> mapError = new HashMap<>();
            result.getAllErrors().stream().forEach(
                    x -> mapError.put(((FieldError) x).getField(), x.getDefaultMessage())
            );
            return ResponseEntity.ok(mapError);
        }
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.create(chiTietSanPhamRequest);
        if (chiTietSanPham == null) {
            return ResponseEntity.ok("Sản phẩm chi tiết đã tồn tại");
        } else {
            return ResponseEntity.ok(chiTietSanPham);
        }
    }

    @PutMapping("{id}")//localhost:8080/api/product-detail/1
    public ResponseEntity<?> update(
            @PathVariable("id") String id,
            @RequestBody ChiTietSanPhamRequest chiTietSanPhamRequest
    ) {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id chi tiết sản phẩm phải là số");
        }
        return ResponseEntity.ok(chiTietSanPhamService.update(Integer.valueOf(id), chiTietSanPhamRequest));
    }

    @DeleteMapping("{id}")//localhost:8080/api/product-detail/1
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id chi tiết sản phẩm phải là số");
        }
        return ResponseEntity.ok(chiTietSanPhamService.delete(Integer.valueOf(id)));
    }

    @GetMapping("/excel/export")//localhost:8080/api/product-detail/excel/export
    public  ResponseEntity<?> exportExcel() {
        return ResponseEntity.ok(chiTietSanPhamService.exportExcel());
    }

    @PostMapping("/excel/import")//localhost:8080/api/product-detail/excel/import
    public  ResponseEntity<?> exportExcel(@RequestBody List<ImportExcelCTSP> importExcelCTSPS) throws IOException, WriterException {
        return ResponseEntity.ok(chiTietSanPhamService.importExcel(importExcelCTSPS));
    }

}
