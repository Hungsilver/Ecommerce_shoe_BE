package com.example.projectshop.auth;

import com.example.projectshop.domain.KhachHang;
import com.example.projectshop.repository.KhachHangRepository;
import com.example.projectshop.repository.NhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class AuthService {
    @Autowired
    private NhanVienRepository nvRepo;
    @Autowired
    private KhachHangRepository khRepo;

    public String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();

        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }
}
