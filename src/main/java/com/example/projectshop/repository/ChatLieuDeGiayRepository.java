package com.example.projectshop.repository;

import com.example.projectshop.domain.ChatLieuDeGiay;
import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.Xuatxu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatLieuDeGiayRepository extends JpaRepository<ChatLieuDeGiay, Integer> {
    @Query("select x from ChatLieuDeGiay x where x.ten like :ten")
    Page<ChatLieuDeGiay> findAllByTen(@Param("ten") String ten, Pageable pageable);

    @Query(value = "select cldg from ChatLieuDeGiay cldg where cldg.ten = :name")
    Optional<ChatLieuDeGiay> findByName(@Param("name")String name);

}
