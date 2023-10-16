package com.example.projectshop.repository;

import com.example.projectshop.domain.ChatLieuDeGiay;
import com.example.projectshop.domain.Thuonghieu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ThuongHieuRepository extends JpaRepository<Thuonghieu,Integer> {
    @Query(value = "select * from thuonghieu  limit 1",nativeQuery = true)
    Thuonghieu getTop1();
}
