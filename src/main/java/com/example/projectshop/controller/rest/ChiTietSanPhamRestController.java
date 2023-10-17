package com.example.projectshop.controller.rest;

import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamRequest;
import com.example.projectshop.service.IChiTietSanPhamService;
import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/chi-tiet-san-pham")
public class ChiTietSanPhamRestController {
    @Autowired
    private IChiTietSanPhamService service;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/find-all")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(){
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        return ResponseEntity.ok(service.getAll(page,limit));
    }

    @GetMapping("/get-one/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id")Integer id){
        return ResponseEntity.ok(service.getOne(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ChiTietSanPhamRequest chiTietSanPhamRequest){
        return ResponseEntity.ok(service.create(chiTietSanPhamRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable("id")Integer id,
            @RequestBody ChiTietSanPhamRequest chiTietSanPhamRequest
    ){
        return ResponseEntity.ok(service.update(id,chiTietSanPhamRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")Integer id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<?> timKiem(){
        String timKiem = request.getParameter("timKiem");
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        return ResponseEntity.ok(service.timKiem(timKiem,page,limit));
    }
}
