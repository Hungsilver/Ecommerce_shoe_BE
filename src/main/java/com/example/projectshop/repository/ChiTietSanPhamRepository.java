package com.example.projectshop.repository;

import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham, Integer> {
    @Query(value = "select * from chitietsanpham  order by giaban asc limit 1", nativeQuery = true)
    ChiTietSanPham getTop1ByPriceMin();

    @Query(value = "select * from chitietsanpham  order by giaban desc limit 1", nativeQuery = true)
    ChiTietSanPham getTop1ByPriceMax();

    @Query(value = "select c from ChiTietSanPham c " +
            "inner join MauSac m on m.id = c.mauSac.id\n" +
            "inner join ChatLieuGiay clg on clg.id = c.chatLieuGiay.id\n" +
            "inner join ChatLieuDeGiay cldg on cldg.id = c.chatLieuDeGiay.id\n "+
            "where c.giaBan between :priceMin and :priceMax \n " +
            "and (m.id in :mauSac or :mauSac is null)\n " +
            "and (clg.id in :chatLieuGiay or :chatLieuGiay is null)\n " +
            "and (cldg.id in :chatLieuDeGiay or :chatLieuDeGiay is null)" +
            "and (c.ma like %:ma% or :ma is null)\n"
    )
    Page<ChiTietSanPham> getAllByParam(
            @Param("priceMin") BigDecimal priceMin,
            @Param("priceMax") BigDecimal priceMax,
            @Param("mauSac") List<Integer>  mauSac,
            @Param("chatLieuGiay") List<Integer>  chatLieuGiay,
            @Param("chatLieuDeGiay") List<Integer>  chatLieuDeGiay,
            @Param("ma") String ma,
            Pageable pageable
    );

    @Query(value = "select c from ChiTietSanPham c where c.ma = :ma")
    List<ChiTietSanPham> findByMa(@Param("ma")String ma);

//    @Query("SELECT ctsp FROM ChiTietSanPham ctsp LEFT JOIN FETCH ctsp.listGioHangChiTiet WHERE ctsp.id = :id")
//    ChiTietSanPham fetchWithGioHangChiTiet(@Param("id") Integer id);




}
