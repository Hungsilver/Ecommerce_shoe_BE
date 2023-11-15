package com.example.projectshop.repository;

import com.example.projectshop.domain.HoaDon;
import com.example.projectshop.domain.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {
    @Query(value = "select h from HoaDon h " +
            "where (h.trangThai in :trangthai or :trangthai is null)\n "+
            "and (h.id = :id or :id is null)"
    )
    Page<HoaDon> findAll(@Param("trangthai")Integer trangThai, @Param("id")String id, Pageable pageable);

    @Query(value = "select * from hoadon order by id desc limit 1", nativeQuery = true)
    HoaDon getTop1ByIdMax();

}
