package com.example.projectshop.controller.rest;

import com.example.projectshop.domain.MauSac;
import com.example.projectshop.domain.NhanVien;
import com.example.projectshop.dto.mausac.MauSacRequest;
import com.example.projectshop.dto.nhanvien.NhanVienRequest;
import com.example.projectshop.service.INhanVienService;
import com.example.projectshop.service.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nhan-vien")
public class NhanVienRestController {
    @Autowired
    private INhanVienService nhanVienService;


    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sortField", required = false, defaultValue = "id") String sortField,
            @RequestParam(value = "isSortDesc", required = false, defaultValue = "false") Boolean isSortDesc,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        Sort sort = Sort.by(isSortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, pageSize, sort);
        Page<NhanVien> nhanViens;
        if (keyword != null && !keyword.isEmpty()) {
            nhanViens = nhanVienService.findAllByName(keyword, pageable);
        } else {
            nhanViens = nhanVienService.getAll(pageable);
        }
        return ResponseEntity.ok(nhanViens);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(nhanVienService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody NhanVienRequest request) {
        NhanVien xx = ObjectMapperUtils.map(request, NhanVien.class);
        return ResponseEntity.ok(nhanVienService.create(xx));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody NhanVienRequest request, @PathVariable("id") Integer id) {
        NhanVien xx = ObjectMapperUtils.map(request, NhanVien.class);
        return ResponseEntity.ok(nhanVienService.update(xx, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(nhanVienService.delete(id));
    }


}

