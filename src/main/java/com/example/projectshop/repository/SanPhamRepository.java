package com.example.projectshop.repository;

import com.example.projectshop.domain.Sanpham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanPhamRepository extends JpaRepository<Sanpham, Integer> {
}
