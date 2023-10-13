package com.example.projectshop.repository;

import com.example.projectshop.domain.Sanpham;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SanPhamRepository extends JpaRepository<Sanpham, Integer> {
}
