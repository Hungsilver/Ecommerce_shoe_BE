package com.example.projectshop.repository;

import com.example.projectshop.domain.ChatLieuDeGiay;
import com.example.projectshop.domain.MauSac;
import com.example.projectshop.domain.NhanVien;
import com.example.projectshop.domain.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien,Integer>{
    @Query("select n from  NhanVien n where n.hoTen like :ten")
    Page<NhanVien> findAllByTen(@Param("ten") String ten, Pageable pageable);

    NhanVien findByEmail(String email);

    @Query(value = "select n from NhanVien n where n.hoTen = :name")
    Optional<NhanVien> findByName(@Param("name")String name);
}
