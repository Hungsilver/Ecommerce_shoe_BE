package com.example.projectshop.repository;

import com.example.projectshop.domain.GhiChu;
import com.example.projectshop.domain.ThuongHieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GhiChuRepository extends JpaRepository<GhiChu,Integer> {

    @Query("select t from GhiChu t where t.hoaDon.id = :idHoaDon order by t.ngayTao desc ")
    Page<GhiChu> findByIdHoaDon(@Param("idHoaDon") Integer idHoaDon, Pageable pageable);
}
