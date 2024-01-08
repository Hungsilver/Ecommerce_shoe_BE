package com.example.projectshop.repository;

import com.example.projectshop.domain.MauSac;
import com.example.projectshop.domain.PhieuGiamGia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhieuGiamGiaRepository extends JpaRepository<PhieuGiamGia, Integer> {
    @Query("select p from PhieuGiamGia p where p.ma like %:ma%")
    Page<PhieuGiamGia> findAllByMa(@Param("ma")String ma, Pageable pageable);

    @Query(value = "select p from PhieuGiamGia p where p.ten = :name")
    Optional<PhieuGiamGia> findByName(@Param("name")String name);

    @Query(value = "select p from PhieuGiamGia p where p.ma = :ma")
    Optional<PhieuGiamGia> findByMa(@Param("ma")String ma);
}
