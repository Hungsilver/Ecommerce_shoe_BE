package com.example.projectshop.repository;

import com.example.projectshop.domain.PhieuGiamGia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PhieuGiamGiaRepository extends JpaRepository<PhieuGiamGia, Integer> {
    @Query("select p from PhieuGiamGia p where p.ma like %:ma%")
    Page<PhieuGiamGia> findAllByMa(@Param("ma")String ma, Pageable pageable);
}
