package com.example.projectshop.repository;

import com.example.projectshop.domain.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GioHangRepository extends JpaRepository<GioHang,Integer> {
}
