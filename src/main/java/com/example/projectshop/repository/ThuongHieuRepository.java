package com.example.projectshop.repository;

import com.example.projectshop.domain.ThuongHieu;
import com.example.projectshop.domain.Xuatxu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThuongHieuRepository extends JpaRepository<ThuongHieu,Integer> {
    @Query("select t from ThuongHieu t where t.ten like %:ten%")
    Page<ThuongHieu> findAllByName(@Param("ten") String ten, Pageable pageable);

    @Query(value = "select t from ThuongHieu t where t.ten = :name")
    Optional<ThuongHieu> findByName(@Param("name")String name);
}
