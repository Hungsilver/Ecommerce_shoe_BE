package com.example.projectshop.controller.rest;

import com.example.projectshop.domain.AnhSanPham;
import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamRequest;
import com.example.projectshop.dto.chitietsanpham.ImportExcelCTSP;
import com.example.projectshop.service.IChiTietSanPhamService;
import com.google.zxing.WriterException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sortField", required = false, defaultValue = "id") String sortField,
            @RequestParam(value = "isSortDesc", required = false, defaultValue = "false") Boolean isSortDesc
    ) {
//        if (!page.matches(p_chu)|| !pageSize.matches(p_chu)){
//            return ResponseEntity.ok("*page || pageSize phải là số");
//        }
        return ResponseEntity.ok(chiTietSanPhamService.findAll(page, pageSize, sortField, isSortDesc));
    }

    @GetMapping("filter")//localhost:8080/api/product-detail/filter
    public ResponseEntity<?> filter(
            @RequestParam(value = "pricemin", required = false) String pricemin,
            @RequestParam(value = "pricemax", required = false) String pricemax,
            @RequestParam(value = "color", required = false) String color,
            @RequestParam(value = "size", required = false) String size,
            @RequestParam(value = "shoe_material", required = false) String shoe_material,
            @RequestParam(value = "shoe_sole_material", required = false) String shoe_sole_material,
            @RequestParam(value = "product", required = false) Integer product,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "isSortAsc", required = false, defaultValue = "false") Boolean isSortAsc,
            @RequestParam(value = "sortField", required = false, defaultValue = "id") String sortField,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        return ResponseEntity.ok(chiTietSanPhamService.filter(pricemin, pricemax, color,size, shoe_material, shoe_sole_material,product, keyword, isSortAsc, sortField, page, pageSize));
    }
//    @GetMapping
    public  ResponseEntity<?> findAnhByIdchiTietSanPham(@PathVariable("id") Integer id){
        List<AnhSanPham> listanh = chiTietSanPhamService.findAnhByChiTietSanPhamId(id);
        return ResponseEntity.ok(listanh);
    }


    @GetMapping("{id}")//localhost:8080/api/product-detail/1
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id chi tiết sản phẩm phải là số");
        }
        return ResponseEntity.ok(chiTietSanPhamService.findById(Integer.valueOf(id)));
    }

    @GetMapping("/pricemax")//localhost:8080/api/product-detail/pricemax
    public ResponseEntity<?> getTop1ByPrice() {
        return ResponseEntity.ok(chiTietSanPhamService.getTop1ByPrice());
    }

    @GetMapping("/code/{ma}")//localhost:8080/api/product-detail/code/abc
    public ResponseEntity<?> findByMa(@PathVariable("ma") String ma) {
        return ResponseEntity.ok(chiTietSanPhamService.findByMa_ProductDetail(ma,1));
    }

    @GetMapping("/findByMa")
    public ResponseEntity<?> findByMa1(@RequestParam String ma) {
//        return ResponseEntity.ok(chiTietSanPhamService.findByMa_ProductDetail(ma));
        if (ma==null){
            return ResponseEntity.notFound().build();
        }
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findByMa_ProductDetail(ma,1);
        if (chiTietSanPham != null) {
            return ResponseEntity.ok(chiTietSanPham);
        } else {
            return ResponseEntity.notFound().build();
        }
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
//            return ResponseEntity.ok("Sản phẩm chi tiết đã tồn tại");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Sản phẩm chi tiết đã tồn tại");

        } else {
            return ResponseEntity.ok(chiTietSanPham);
        }
    }


//    @PutMapping("{id}")//localhost:8080/api/product-detail/1
//    public ResponseEntity<?> update(
//            @PathVariable("id") Integer id,
//            @RequestBody ChiTietSanPhamRequest chiTietSanPhamRequest
//    ) {
//        return ResponseEntity.ok(chiTietSanPhamService.update(Integer.valueOf(id), chiTietSanPhamRequest));
//    }

    @PutMapping("{id}") // localhost:8080/api/product-detail/1
    public ResponseEntity<?> update(
            @PathVariable("id") Integer id,
            @RequestBody ChiTietSanPhamRequest chiTietSanPhamRequest
    ) {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.update(id,chiTietSanPhamRequest);
        if (chiTietSanPham == null) {
//            return ResponseEntity.ok("Sản phẩm chi tiết đã tồn tại");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Lỗi trùng mã");

        } else {
            return ResponseEntity.ok(chiTietSanPham);
        }


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
