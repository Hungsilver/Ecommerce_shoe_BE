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
@CrossOrigin(origins = "*")
@RequestMapping("/api/staff")
public class NhanVienRestController {
    @Autowired
    private INhanVienService nhanVienService;

    private String p_chu = "\\d+";

    @GetMapping//localhost:8080/api/staff
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
        Sort sort = Sort.by(isSortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(Integer.valueOf(page) > 0 ? Integer.valueOf(page) - 1 : Integer.valueOf(page), Integer.valueOf(pageSize), sort);
        Page<NhanVien> nhanViens;
        if (keyword != null && !keyword.isEmpty()) {
            nhanViens = nhanVienService.findAllByName(keyword, pageable);
        } else {
            nhanViens = nhanVienService.getAll(pageable);
        }
        return ResponseEntity.ok(nhanViens);
    }

    @GetMapping("{id}")//localhost:8080/api/staff/1
    public ResponseEntity<?> findById(@PathVariable String id) {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id nhân viên phải là số");
        }
        return ResponseEntity.ok(nhanVienService.findById(Integer.valueOf(id)));
    }

    @PostMapping//localhost:8080/api/staff
    public ResponseEntity<?> create(@RequestBody NhanVienRequest request) {
//        NhanVien xx = ObjectMapperUtils.map(request, NhanVien.class);
//        if(request ==null){
//
//        }
        return ResponseEntity.ok(nhanVienService.insertNhanVien(request));
    }

    @PutMapping("{id}")//localhost:8080/api/staff/1
    public ResponseEntity<?> update(@RequestBody NhanVienRequest request,
                                    @PathVariable("id") String id)
    {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id nhân viên phải là số");
        } 
        NhanVien xx = ObjectMapperUtils.map(request, NhanVien.class);
        return ResponseEntity.ok(nhanVienService.update(xx, Integer.valueOf(id)));
    }

    @DeleteMapping("{id}")//localhost:8080/api/staff/1
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id nhân viên phải là số");
        }
        return ResponseEntity.ok(nhanVienService.delete(Integer.valueOf(id)));
    }


}

