package com.example.projectshop.repository;

import com.example.projectshop.domain.TraHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface TraHangChiTietRepository extends JpaRepository<TraHangChiTiet,Integer> {

    @Query("select sum(h.donGia*h.soLuong) from TraHangChiTiet h where h.traHang.id= :id")
    BigDecimal tongTienByIdTraHang(@Param("id") Integer id);

}
