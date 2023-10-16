package com.example.projectshop.repository;

import com.example.projectshop.domain.ChatLieuDeGiay;
import com.example.projectshop.domain.Xuatxu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface XuatXuRepository extends JpaRepository<Xuatxu, Integer> {
    @Query(value = "select * from xuatxu  limit 1",nativeQuery = true)
    Xuatxu getTop1();
}
