package com.example.projectshop.repository;

import com.example.projectshop.domain.ChatLieuDeGiay;
import com.example.projectshop.domain.Mausac;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MauSacRepository extends JpaRepository<Mausac, Integer> {
    @Query(value = "select * from mausac  limit 1",nativeQuery = true)
    ChatLieuDeGiay getTop1();

}
