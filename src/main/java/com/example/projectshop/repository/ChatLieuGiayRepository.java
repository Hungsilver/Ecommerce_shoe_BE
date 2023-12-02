package com.example.projectshop.repository;

import com.example.projectshop.domain.ChatLieuDeGiay;
import com.example.projectshop.domain.ChatLieuGiay;
import com.example.projectshop.domain.SanPham;
import com.example.projectshop.domain.Xuatxu;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatLieuGiayRepository extends JpaRepository<ChatLieuGiay, Integer> {
    @Query("select x from ChatLieuGiay x where x.ten like :ten")
    Page<ChatLieuGiay> findAllByTen(@Param("ten") String ten, Pageable pageable);

    @Query(value = "select clg from ChatLieuGiay clg where clg.ten = :name")
    Optional<ChatLieuGiay> findByName(@Param("name")String name);

}
