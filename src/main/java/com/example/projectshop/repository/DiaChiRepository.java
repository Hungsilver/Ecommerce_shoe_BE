package com.example.projectshop.repository;

import com.example.projectshop.domain.DiaChi;
import com.example.projectshop.domain.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaChiRepository extends JpaRepository<DiaChi,Integer> {
    @Query(value = "select d from DiaChi d where d.phuongXa like %:input% " +
            "or d.quanHuyen like %:input% " +
            "or d.tinhThanh like %:input%")
    Page<DiaChi> findAllByDiaChi(@Param("input") String input, Pageable pageable);
}
