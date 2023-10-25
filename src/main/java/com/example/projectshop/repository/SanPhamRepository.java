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
public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
    @Query(value = "select s from SanPham s " +
            "inner join ChiTietSanPham c on c.sanPham.id = s.id\n" +
            "inner join ThuongHieu t on t.id = s.thuongHieu.id\n" +
            "inner join Xuatxu x on x.id = s.xuatXu.id\n " +
            "inner join MauSac m on m.id = c.mauSac.id\n" +
            "inner join ChatLieuGiay clg on clg.id = c.chatLieuGiay.id\n" +
            "inner join ChatLieuDeGiay cldg on cldg.id = c.chatLieuDeGiay.id\n "+
            "where c.giaBan between :priceMin and :priceMax \n "+
            "and (t.id in :thuongHieu or :thuongHieu is null)\n "+
            "and (x.id in :xuatXu or :xuatXu is null)\n " +
            "and (m.id in :mauSac or :mauSac is null)\n " +
            "and (clg.id in :chatLieuGiay or :chatLieuGiay is null)\n " +
            "and (cldg.id in :chatLieuDeGiay or :chatLieuDeGiay is null)\n"
            )
    Page<SanPham> getAllByParam(
            @Param("priceMin") BigDecimal priceMin,
            @Param("priceMax") BigDecimal priceMax,
            @Param("thuongHieu") List<Integer> thuonghieu,
            @Param("xuatXu") List<Integer>  xuatXu,
            @Param("mauSac") List<Integer>  mauSac,
            @Param("chatLieuGiay") List<Integer>  chatLieuGiay,
            @Param("chatLieuDeGiay") List<Integer>  chatLieuDeGiay,
            Pageable pageable
    );


    @Query(value = "select s from SanPham s \n" +
            "inner join ThuongHieu t on t.id = s.thuongHieu.id\n" +
            "inner join Xuatxu x on x.id = s.xuatXu.id\n" +
            "inner join DanhMuc d on d.id = s.danhMuc.id\n" +
            "where s.ten like %:timKiem% \n" +
            "or t.ten like %:keyword% \n" +
            "or x.ten like %:keyword% \n" +
            "or d.ten like %:keyword% ")
    Page<SanPham> search(@Param("keyword")String keyword, Pageable pageable);


}
