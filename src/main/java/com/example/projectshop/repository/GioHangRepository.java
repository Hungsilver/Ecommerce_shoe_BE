package com.example.projectshop.repository;

import com.example.projectshop.domain.Giohang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GioHangRepository extends JpaRepository<Giohang,Integer> {
}
