package com.example.projectshop.repository;

import com.example.projectshop.domain.Thuonghieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThuongHieuRepository extends JpaRepository<Thuonghieu,Integer> {
}
