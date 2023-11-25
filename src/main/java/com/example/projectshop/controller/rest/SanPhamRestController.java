package com.example.projectshop.controller.rest;


import com.example.projectshop.dto.sanpham.SanPhamRequest;
import com.example.projectshop.repository.ChatLieuDeGiayRepository;
import com.example.projectshop.repository.SanPhamRepository;
import com.example.projectshop.service.IAttributeSevice;
import com.example.projectshop.service.ISanPhamService;
import com.example.projectshop.service.impl.ExcelProductsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Map;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/api/product")
public class SanPhamRestController {

    @Autowired
    private ExcelProductsService excelProductsService;

    @Autowired
    private ISanPhamService service;

    @Autowired
    private IAttributeSevice attributeSevice;

    private String p_chu = "\\d+";

    @GetMapping()//localhost:8080/api/product
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") String pageSize,
            @RequestParam(value = "sortField", required = false, defaultValue = "id") String sortField,
            @RequestParam(value = "isSortDesc", required = false, defaultValue = "false") Boolean isSortDesc
    ) {
        if (!page.matches(p_chu) || !pageSize.matches(p_chu)) {
            return ResponseEntity.ok("*page || pageSize phải là số");
        }
        return ResponseEntity.ok(service.findAll(Integer.valueOf(page), Integer.valueOf(pageSize), sortField, isSortDesc));
    }

    @GetMapping("/filter")//localhost:8080/api/product/filter
    public ResponseEntity<?> filter(
            @RequestParam(value = "pricemin", required = false) String pricemin,
            @RequestParam(value = "pricemax", required = false) String pricemax,
            @RequestParam(value = "brand", required = false) String brand,
            @RequestParam(value = "origin", required = false) String origin,
            @RequestParam(value = "color", required = false) String color,
            @RequestParam(value = "size", required = false) String size,
            @RequestParam(value = "shoe_material", required = false) String shoe_material,
            @RequestParam(value = "shoe_sole_material", required = false) String shoe_sole_material,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "isSortAsc", required = false, defaultValue = "false") Boolean isSortAsc,
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") String pageSize
    ) {
        if (!page.matches(p_chu) || pageSize.matches(p_chu)) {
            return ResponseEntity.ok("*page || pageSize phải là số");
        }
        return ResponseEntity.ok(
                service.filter(
                        pricemin,
                        pricemax,
                        brand,
                        origin,
                        color,
                        size,
                        shoe_material,
                        shoe_sole_material,
                        keyword,
                        isSortAsc,
                        Integer.valueOf(page),
                        Integer.valueOf(pageSize))
        );
    }

    @GetMapping("/{id}")//localhost:8080/api/product/1
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id sản phẩm phải là số");
        }
        return ResponseEntity.ok(service.findById(Integer.valueOf(id)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)//localhost:8080/api/product
    public ResponseEntity<?> create(@RequestBody SanPhamRequest sanPhamRequest) {
        return ResponseEntity.ok(service.create(sanPhamRequest));
    }


    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)//localhost:8080/api/product/1
    public ResponseEntity<?> update(
            @PathVariable("id") String id,
            @RequestBody SanPhamRequest sanPhamRequest
    ) {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id sản phẩm phải là số");
        }
        return ResponseEntity.ok(service.update(Integer.valueOf(id), sanPhamRequest));
    }

    @DeleteMapping("{id}")//localhost:8080/api/product/1
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id sản phẩm phải là số");
        }
        service.delete(Integer.valueOf(id));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/attribute")//localhost:8080/api/product/attribute
    public ResponseEntity<?> attribute() {
        return ResponseEntity.ok(attributeSevice.findAll());
    }


//    @GetMapping("/excel/download")
//    public ResponseEntity<Resource> ExportExcel() {
//        String filename = "SanPham.xlsx";
//        ByteArrayInputStream data = excelProductsService.loadProducts();
//        InputStreamResource file = new InputStreamResource(data);
//
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
//                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
//                .body(file);
//    }

//    @PostMapping("/excel/upload")
//    public ResponseEntity<?> ImportExcel(@RequestParam("fileProduct") MultipartFile file) {
//        excelProductsService.saveProductsToDatabase(file);
//        return ResponseEntity.ok(Map.of("message", "success"));
//    }

}
