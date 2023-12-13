package com.example.projectshop.repository;

import com.example.projectshop.domain.GioHang;
import com.example.projectshop.domain.GioHangChiTiet;
import com.example.projectshop.domain.GioiThieu;
import com.example.projectshop.domain.PhieuGiamGia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GioHangRepository extends JpaRepository<GioHang,Integer> {
//    @Query("select p from PhieuGiamGia p where p.ma like %:ma%")
//    Page<GioiThieu> findAllByMa(@Param("ma")String ma, Pageable pageable);

    @Query("select g from GioHang g where g.khachHang.id = :id")
    GioHang findByIdKhachHang(@Param("id")Integer id);

}
