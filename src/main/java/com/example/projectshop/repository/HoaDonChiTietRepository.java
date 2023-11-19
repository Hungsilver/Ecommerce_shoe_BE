package com.example.projectshop.repository;

import com.example.projectshop.domain.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet,Integer> {
    @Query("select sum(h.donGia*h.soLuong) from HoaDonChiTiet h where h.hoaDon.id = :id")
    BigDecimal tongTienByIdHoaDon(@Param("id")Integer id);
}
