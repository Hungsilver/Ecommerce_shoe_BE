package com.example.projectshop.repository;

import com.example.projectshop.domain.Nhanvien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NhanVienRepository extends JpaRepository<Nhanvien, Integer> {
}
