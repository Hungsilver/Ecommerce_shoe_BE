package com.example.projectshop.controller.rest;

import com.example.projectshop.dto.thuonghieu.ThuongHieuRequest;
import com.example.projectshop.dto.thuonghieu.ThuongHieuResponse;
import com.example.projectshop.service.IThuongHieuService;
import com.example.projectshop.utils.QRCodeGenerator;
import com.google.zxing.WriterException;
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

    @GetMapping()// localhost:8080/api/brand...
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sortField", required = false, defaultValue = "id") String sortField,
            @RequestParam(value = "isSortDesc", required = false, defaultValue = "false") Boolean isSortDesc,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        return ResponseEntity.ok(service.findAll(page,pageSize,sortField,isSortDesc,keyword));
    }

    @PostMapping()//localhost:8080/api/brand
    public ResponseEntity<?> create(
            @RequestBody ThuongHieuRequest thuongHieuRequest
    ) throws IOException, WriterException {
        return ResponseEntity.ok(service.create(thuongHieuRequest));
    }

    @GetMapping("{id}")//localhost:8080/api/brand/1
    public ResponseEntity<?> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }


    @PutMapping("{id}")//localhost:8080/api/brand/1
    public ResponseEntity<?> update(
            @PathVariable("id") Integer id,
            @RequestBody ThuongHieuRequest thuongHieuRequest

    ){
        return ResponseEntity.ok(service.update(id,thuongHieuRequest));
    }

    @DeleteMapping("{id}")//localhost:8080/api/brand/1
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) throws IOException, WriterException {
        return ResponseEntity.ok(service.delete(id));
    }
}
