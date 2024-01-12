package com.example.projectshop.repository;

import com.example.projectshop.domain.TraHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraHangChiTietRepository extends JpaRepository<TraHangChiTiet,Integer> {
}
