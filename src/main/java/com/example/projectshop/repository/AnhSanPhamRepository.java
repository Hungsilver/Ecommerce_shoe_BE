package com.example.projectshop.repository;

import com.example.projectshop.domain.AnhSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnhSanPhamRepository extends JpaRepository<AnhSanPham,Integer> {
}
