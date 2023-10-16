package com.example.projectshop.repository;

import com.example.projectshop.domain.ChiTietSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham, Integer> {
    @Query(value = "select * from chitietsanpham  order by giaban asc limit 1", nativeQuery = true)
    ChiTietSanPham getTop1ByPriceMin();

    @Query(value = "select * from chitietsanpham  order by giaban desc limit 1", nativeQuery = true)
    ChiTietSanPham getTop1ByPriceMax();
}
