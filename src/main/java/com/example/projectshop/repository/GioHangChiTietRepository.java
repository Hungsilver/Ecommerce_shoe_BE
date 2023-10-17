package com.example.projectshop.repository;

import com.example.projectshop.domain.GioHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet,Integer> {
}
