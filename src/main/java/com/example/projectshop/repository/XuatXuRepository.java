package com.example.projectshop.repository;

import com.example.projectshop.domain.Xuatxu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface XuatXuRepository extends JpaRepository<Xuatxu, Integer> {

    @Query("select x from Xuatxu x where x.ten like :ten")
    Page<Xuatxu> findAllByTen(@Param("ten") String ten, Pageable pageable);

}
