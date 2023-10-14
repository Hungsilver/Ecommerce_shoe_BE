package com.example.projectshop.repository;

import com.example.projectshop.domain.Hoadonchitiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaDonChiTietRepository extends JpaRepository<Hoadonchitiet,Integer> {
}
