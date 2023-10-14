package com.example.projectshop.repository;

import com.example.projectshop.domain.Chitietsanpham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietSanPhamRepository extends JpaRepository<Chitietsanpham, Integer> {
}
