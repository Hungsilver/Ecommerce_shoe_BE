package com.example.projectshop.repository;

import com.example.projectshop.domain.ChatLieuDeGiay;
import com.example.projectshop.domain.Kichco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface KichCoRepository extends JpaRepository<Kichco, Integer> {
    @Query(value = "select * from kichco  limit 1",nativeQuery = true)
    ChatLieuDeGiay getTop1();
}
