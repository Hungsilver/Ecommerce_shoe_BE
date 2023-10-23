package com.example.projectshop;

import com.example.projectshop.repository.NhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class ProjectShopApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ProjectShopApplication.class, args);
    }

    @Autowired
    NhanVienRepository nv;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        nv.findAll().forEach(System.out::println);
    }
}
