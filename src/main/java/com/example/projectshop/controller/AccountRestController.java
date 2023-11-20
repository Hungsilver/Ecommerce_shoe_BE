package com.example.projectshop.controller;

import com.example.projectshop.dto.BaseResponse;
import com.example.projectshop.dto.nhanvien.NhanVienRequest;
import com.example.projectshop.service.INhanVienService;
import com.example.projectshop.service.impl.NhanVienServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")

public class AccountRestController {
//    @Autowired
//    private NhanVienServiceImpl service;
//
//    @PostMapping("/register-account")
//    public ResponseEntity<BaseResponse> registerAccount(@RequestBody NhanVienRequest nhanVienRequest) {
//        return ResponseEntity.ok(service.registerAccount(nhanVienRequest));
//    }

}
