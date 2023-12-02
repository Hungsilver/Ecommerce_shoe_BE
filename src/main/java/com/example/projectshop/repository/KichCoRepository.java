package com.example.projectshop.repository;

import com.example.projectshop.domain.ChatLieuDeGiay;
import com.example.projectshop.domain.KichCo;
import com.example.projectshop.domain.Xuatxu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KichCoRepository extends JpaRepository<KichCo, Integer> {
    @Query(value = "select k from KichCo k where k.size = :size")
    Page<KichCo> findAllBySize(@Param("size") String size, Pageable pageable);

    @Query(value = "select k from KichCo k where k.size = :name")
    Optional<KichCo> findByName(@Param("name")String name);
}
