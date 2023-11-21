package com.example.projectshop.controller.rest;

import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamRequest;
import com.example.projectshop.service.IChiTietSanPhamService;
import com.google.zxing.WriterException;
import com.example.projectshop.service.impl.ExcelProductDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Map;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/api/product-detail")

public class ChiTietSanPhamRestController {
    @Autowired
    private ExcelProductDetailsService execlService;
    @Autowired
    private IChiTietSanPhamService chiTietSanPhamService;


    @GetMapping()//localhost:8080/api/product-detail
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sortField", required = false, defaultValue = "id") String sortField,
            @RequestParam(value = "isSortDesc", required = false, defaultValue = "false") Boolean isSortDesc
    ) {
        return ResponseEntity.ok(chiTietSanPhamService.findAll(page, pageSize, sortField, isSortDesc));
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
    public ResponseEntity<?> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(chiTietSanPhamService.findById(id));
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
            @PathVariable("id") Integer id,
            @RequestBody ChiTietSanPhamRequest chiTietSanPhamRequest
    ) {
        return ResponseEntity.ok(chiTietSanPhamService.update(id, chiTietSanPhamRequest));
    }

    @DeleteMapping("{id}")//localhost:8080/api/product-detail/1
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(chiTietSanPhamService.delete(id));
    }



    @GetMapping("/excel/download")
    public ResponseEntity<Resource> ExportExcel() {
        String fileName = "ChiTietSanPham.xlsx";
        ByteArrayInputStream data = execlService.load();
        InputStreamResource file = new InputStreamResource(data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename="+fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);

    }
    @PostMapping("/excel/upload")
    public  ResponseEntity<?> ImportExcel(@RequestParam("file") MultipartFile  file){
        execlService.saveChiTietSanPhamsToDatabase(file);
        return ResponseEntity.ok(Map.of("message"," Customers data uploaded and saved to database successfully"));
    }


}
