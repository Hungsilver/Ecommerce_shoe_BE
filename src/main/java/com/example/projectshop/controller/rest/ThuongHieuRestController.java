package com.example.projectshop.controller.rest;

import com.example.projectshop.dto.thuonghieu.ThuongHieuRequest;
import com.example.projectshop.dto.thuonghieu.ThuongHieuResponse;
import com.example.projectshop.service.IThuongHieuService;
import com.example.projectshop.utils.QRCodeGenerator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
        if (!page.matches(p_chu) || !pageSize.matches(p_chu)){
            return ResponseEntity.ok("*page || pageSize phải là số");
        }
        return ResponseEntity.ok(service.findAll(Integer.valueOf(page),Integer.valueOf(pageSize),sortField,isSortDesc,keyword));
    }

    @PostMapping()//localhost:8080/api/brand
    public ResponseEntity<?> create(
            @RequestBody ThuongHieuRequest thuongHieuRequest
    ) throws IOException {
        return ResponseEntity.ok(service.create(thuongHieuRequest));
    }

    @GetMapping("{id}")//localhost:8080/api/brand/1
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id thương hiệu phải là số");
        }
        return ResponseEntity.ok(service.findById(Integer.valueOf(id)));
    }


    @PutMapping("{id}")//localhost:8080/api/brand/1
    public ResponseEntity<?> update(
            @PathVariable("id") String id,
            @RequestBody ThuongHieuRequest thuongHieuRequest

    ){
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id thương hiệu phải là số");
        }
        return ResponseEntity.ok(service.update(Integer.valueOf(id),thuongHieuRequest));
    }

    @DeleteMapping("{id}")//localhost:8080/api/brand/1
    public ResponseEntity<?> delete(@PathVariable("id") String id) throws IOException {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id thương hiệu phải là số");
        }
        return ResponseEntity.ok(service.delete(Integer.valueOf(id)));
    }
}
