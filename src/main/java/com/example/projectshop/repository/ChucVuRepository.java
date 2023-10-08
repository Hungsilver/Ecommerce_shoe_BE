package com.example.projectshop.repository;

import com.example.projectshop.domain.Chucvu;
import com.example.projectshop.domain.Nhanvien;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChucVuRepository extends JpaRepository<Chucvu,Integer> {
}
