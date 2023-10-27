package com.example.projectshop.controller.rest;

import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamRequest;
import com.example.projectshop.service.IChiTietSanPhamService;
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

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/api/product-details")
public class ChiTietSanPhamRestController {
    @Autowired
    private IChiTietSanPhamService chiTietSanPhamService;

    @GetMapping()
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sortField", required = false, defaultValue = "id") String sortField,
            @RequestParam(value = "isSortDesc", required = false, defaultValue = "false") Boolean isSortDesc
    ) {
        return ResponseEntity.ok(chiTietSanPhamService.findAll(page,pageSize,sortField,isSortDesc));
    }

    @GetMapping("filter")
    public ResponseEntity<?> filter(
            @RequestParam(value = "pricemin", required = false) String pricemin,
            @RequestParam(value = "pricemax", required = false) String pricemax,
            @RequestParam(value = "color", required = false) String color,
            @RequestParam(value = "shoe_material", required = false) String shoe_material,
            @RequestParam(value = "shoe_sole_material", required = false) String shoe_sole_material,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        return ResponseEntity.ok(chiTietSanPhamService.filter(pricemin, pricemax, color, shoe_material, shoe_sole_material, page, pageSize));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(chiTietSanPhamService.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody ChiTietSanPhamRequest chiTietSanPhamRequest) {
        return ResponseEntity.ok(chiTietSanPhamService.create(chiTietSanPhamRequest));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(
            @PathVariable("id") Integer id,
            @RequestBody ChiTietSanPhamRequest chiTietSanPhamRequest
    ) {
        return ResponseEntity.ok(chiTietSanPhamService.update(id, chiTietSanPhamRequest));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(chiTietSanPhamService.delete(id));
    }

    @GetMapping("/search")
    public ResponseEntity<?> timKiem(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        return ResponseEntity.ok(chiTietSanPhamService.search(keyword, page, pageSize));
    }
}
