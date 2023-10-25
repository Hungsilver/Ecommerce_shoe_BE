package com.example.projectshop.repository;

import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            "and (cldg.id in :chatLieuDeGiay or :chatLieuDeGiay is null)\n"
    )
    Page<ChiTietSanPham> getAllByParam(
            @Param("priceMin") BigDecimal priceMin,
            @Param("priceMax") BigDecimal priceMax,
            @Param("mauSac") List<Integer>  mauSac,
            @Param("chatLieuGiay") List<Integer>  chatLieuGiay,
            @Param("chatLieuDeGiay") List<Integer>  chatLieuDeGiay,
            Pageable pageable
    );

    @Query(value = "select c from ChiTietSanPham c \n" +
            "inner join MauSac m on m.id = c.mauSac.id\n" +
            "inner join ChatLieuGiay cl on cl.id = c.chatLieuGiay.id\n" +
            "inner join ChatLieuDeGiay cld on cld.id = c.chatLieuDeGiay.id\n" +
            "inner join SanPham s on s.id = c.sanPham.id\n" +
            "where c.ma like %:keyword% \n" +
            "or m.ten like %:keyword% \n" +
            "or cl.ten like %:keyword% \n" +
            "or cld.ten like %:keyword% \n" +
            "or s.ten like%:keyword% \n" )
    Page<ChiTietSanPham> search(@Param("keyword") String keyword, Pageable pageable);



}
