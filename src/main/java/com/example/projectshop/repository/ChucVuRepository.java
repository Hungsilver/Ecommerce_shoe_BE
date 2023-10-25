package com.example.projectshop.repository;

import com.example.projectshop.domain.ChucVu;
import com.example.projectshop.domain.MauSac;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChucVuRepository extends JpaRepository<ChucVu, Integer> {
    @Query("select x from  ChucVu x where x.tenChucVu like :ten")
    Page<ChucVu> findAllByTen(@Param("ten") String ten, Pageable pageable);

}
