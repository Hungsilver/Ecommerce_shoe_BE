package com.example.projectshop.controller.rest;

import com.example.projectshop.service.IChiTietSanPhamService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

}
