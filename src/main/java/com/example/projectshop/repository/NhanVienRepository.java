package com.example.projectshop.repository;

import com.example.projectshop.domain.ChatLieuDeGiay;
import com.example.projectshop.domain.MauSac;
import com.example.projectshop.domain.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien,Integer>{


    @Query("select x from  NhanVien x where x.hoTen like :ten")
    Page<NhanVien> findAllByTen(@Param("ten") String ten, Pageable pageable);

    NhanVien findByEmail(String email);
}
