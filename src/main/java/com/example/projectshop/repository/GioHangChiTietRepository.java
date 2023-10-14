package com.example.projectshop.repository;

import com.example.projectshop.domain.Giohangchitiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GioHangChiTietRepository extends JpaRepository<Giohangchitiet,Integer> {
}
