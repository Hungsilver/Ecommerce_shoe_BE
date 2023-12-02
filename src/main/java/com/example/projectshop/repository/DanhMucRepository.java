package com.example.projectshop.repository;

import com.example.projectshop.domain.ChatLieuGiay;
import com.example.projectshop.domain.DanhMuc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DanhMucRepository extends JpaRepository<DanhMuc,Integer> {
    @Query(value = "select d from DanhMuc d where d.ten like %:ten% ")
    Page<DanhMuc> findAllByName(@Param("ten") String ten, Pageable pageable);

    @Query(value = "select d from DanhMuc d where d.ten = :name")
    Optional<DanhMuc> findByName(@Param("name")String name);
}
