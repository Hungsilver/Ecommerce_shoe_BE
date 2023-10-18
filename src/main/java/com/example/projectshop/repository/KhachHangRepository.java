package com.example.projectshop.repository;

import com.example.projectshop.domain.ChatLieuDeGiay;
import com.example.projectshop.domain.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface KhachHangRepository extends JpaRepository<KhachHang,Integer> {
    @Query(value = "select * from khachhang k where k.email like %:input% " +
            "or k.sodienthoai like %:input%"
            ,nativeQuery = true)
    Page<KhachHang> timKiem(@Param("input") String input, Pageable pageable);
}
