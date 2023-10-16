package com.example.projectshop.controller.rest;


import com.example.projectshop.service.INguoiDungService;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/nguoi-dung")
public class NguoiDungRestController {

    @Autowired
    private INguoiDungService service;

    @GetMapping("/hien-thi")
    public ResponseEntity<?> load() {
        return ResponseEntity.ok(service.getall());
    }


}
