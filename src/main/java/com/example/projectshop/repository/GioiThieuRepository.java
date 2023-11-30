package com.example.projectshop.repository;

import com.example.projectshop.domain.GioiThieu;
import com.example.projectshop.domain.PhieuGiamGia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GioiThieuRepository extends JpaRepository<GioiThieu,Integer> {
    @Query("select p from PhieuGiamGia p where p.ma like %:ma%")
    Page<GioiThieu> findAllByName(@Param("ma")String ma, Pageable pageable);

    @Query(value = "select g from GioiThieu g where g.tenGioiThieu = :name")
    Optional<GioiThieu> findByName(@Param("name")String name);
}
