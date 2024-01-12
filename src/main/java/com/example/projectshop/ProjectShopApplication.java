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
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.SessionScope;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
//@EnableScheduling
public class ProjectShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectShopApplication.class, args);
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        System.out.println(passwordEncoder.encode("2003"));

    }

    //    @Autowired
//    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    NhanVienRepository nvRepo;


    @Autowired
    KhachHangRepository khRepo;

//
    @PostConstruct
    public void postcontruc() {
        KhachHang kh = khRepo.findById(1).get();
        kh.setEmail("customer@gmail.com");
        kh.setMatKhau(bCryptPasswordEncoder.encode("Admin@123"));
        khRepo.save(kh);

        NhanVien nv = nvRepo.findById(1).get();
        nv.setEmail("admin@gmail.com");
        nv.setMatKhau(bCryptPasswordEncoder.encode("Admin@123"));
        nvRepo.save(nv);
    }


}
