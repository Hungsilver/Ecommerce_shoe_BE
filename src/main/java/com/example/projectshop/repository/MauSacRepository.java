package com.example.projectshop.repository;

import com.example.projectshop.domain.ChatLieuDeGiay;
import com.example.projectshop.domain.MauSac;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MauSacRepository extends JpaRepository<MauSac, Integer> {
    @Query("select x from  MauSac x where x.ten like :ten")
    Page<MauSac> findAllByTen(@Param("ten") String ten, Pageable pageable);

    @Query(value = "select m from MauSac m where m.ten = :name")
    Optional<MauSac> findByName(@Param("name")String name);
}
