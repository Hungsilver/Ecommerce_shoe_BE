package com.example.projectshop.repository;

import com.example.projectshop.domain.AnhSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnhSanPhamRepository extends JpaRepository<AnhSanPham,String> {
    @Query(value = "select a from AnhSanPham a where a.ten = :ten")
    Optional<AnhSanPham> getByTen(String ten);
}
