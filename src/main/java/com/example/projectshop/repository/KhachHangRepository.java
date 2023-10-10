package com.example.projectshop.repository;

import com.example.projectshop.domain.Khachhang;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KhachHangRepository extends JpaRepository<Khachhang,Integer> {
}
