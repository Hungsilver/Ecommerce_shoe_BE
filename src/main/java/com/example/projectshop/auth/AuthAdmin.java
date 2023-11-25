package com.example.projectshop.auth;

import com.example.projectshop.domain.NhanVien;
import com.example.projectshop.dto.BaseResponse;
import com.example.projectshop.dto.nhanvien.NhanVienRequest;
import com.example.projectshop.dto.nhanvien.NhanVienResponse;
import com.example.projectshop.service.impl.NhanVienServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/auth/admin")
public class AuthAdmin {

    @Autowired
    private NhanVienServiceImpl nhanVienService;

    //TODO role: ADMIN và STAFF
    @PostMapping("/register-account")
    public ResponseEntity<NhanVien> registerAccount(@RequestBody NhanVienRequest nhanVienRequest) {
        return ResponseEntity.ok(nhanVienService.insertNhanVien(nhanVienRequest));
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
