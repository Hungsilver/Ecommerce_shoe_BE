package com.example.projectshop.controller;

import com.example.projectshop.dto.BaseResponse;
import com.example.projectshop.dto.nhanvien.NhanVienRequest;
import com.example.projectshop.dto.nhanvien.NhanVienResponse;
import com.example.projectshop.service.impl.NhanVienServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/auth/admin")
public class AuthRestController {

    @Autowired
    private NhanVienServiceImpl nhanVienService;

    @PostMapping("/register-account")
    public ResponseEntity<BaseResponse> registerAccount(@RequestBody NhanVienRequest nhanVienRequest) {
        return ResponseEntity.ok(nhanVienService.registerAccount(nhanVienRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<NhanVienResponse> login(@RequestBody NhanVienRequest nhanVienRequest) {
        try {
            NhanVienResponse nhanVienResponse = nhanVienService.authenticateUser(nhanVienRequest);
            return ResponseEntity.ok(nhanVienResponse);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

}
