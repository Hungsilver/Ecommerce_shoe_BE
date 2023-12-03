package com.example.projectshop;

//import com.example.projectshop.repository.NhanVienRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;

import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.KhachHang;
import com.example.projectshop.domain.NhanVien;
import com.example.projectshop.repository.ChiTietSanPhamRepository;
import com.example.projectshop.repository.KhachHangRepository;
import com.example.projectshop.repository.NhanVienRepository;
import com.example.projectshop.repository.SanPhamRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ProjectShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectShopApplication.class, args);
    }


    //    @Bean
//    BCryptPasswordEncoder bCryptPasswordEncoder () {
//        return new BCryptPasswordEncoder();
//    }
    @Autowired
    NhanVienRepository nvRepo;
    @Autowired
    KhachHangRepository khRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @PostConstruct
//    public void postcontruc() {
//        KhachHang nv = khRepo.findById(1).get();
//        nv.setEmail("customer@gmail.com");
//        nv.setMatKhau(passwordEncoder.encode("123"));
//        khRepo.save(nv);
//    }
}
