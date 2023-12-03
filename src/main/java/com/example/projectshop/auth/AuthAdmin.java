package com.example.projectshop.auth;

import com.example.projectshop.domain.NhanVien;
import com.example.projectshop.dto.BaseResponse;
import com.example.projectshop.dto.auth.LoginRequest;
import com.example.projectshop.dto.auth.RegisterRequest;
import com.example.projectshop.dto.nhanvien.NhanVienRequest;
import com.example.projectshop.dto.nhanvien.NhanVienResponse;
import com.example.projectshop.service.impl.NhanVienServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth/admin")
public class AuthAdmin {

    @Autowired
    private NhanVienServiceImpl nhanVienService;

    //TODO role: ADMIN và STAFF
    @PostMapping("/register-account")
    public ResponseEntity<NhanVien> registerAccount(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(nhanVienService.registerAccount(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<NhanVien> login(@RequestBody LoginRequest staffRequest) {
        try {
            NhanVien nv = nhanVienService.authenticateUser(staffRequest);
            return ResponseEntity.ok(nv);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}
