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
            "inner join Thuonghieu t on t.id = s.thuongHieu.id\n" +
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


    @Query(value = "select distinct s.id,s.ma,s.ten,s.anhChinh,s.moTa,s.trangThai,s.id_thuongHieu,s.id_xuatXu from sanpham s\n" +
            "inner join chitietsanpham c on c.id_sanpham = s.id\n" +
            "where c.giaban between :priceMin and :priceMax\n", nativeQuery = true)
    Page<SanPham> getAllByBetweenPrice(
            @Param("priceMin") BigDecimal priceMin,
            @Param("priceMax") BigDecimal priceMax,
            Pageable pageable
    );

    @Query(value = "select distinct * from sanpham", nativeQuery = true)
    Page<SanPham> getAll(Pageable pageable);

    @Query(value = "select * from sanpham  limit 1", nativeQuery = true)
    SanPham getTop1();

    @Query(value = "select s.id,s.soluong,s.giaban,s.ngaytao,s.trangthai,s.id_mausas,s.id_kichco,s.id_chatlieugiay,s.id_chatlieudegiay,s.id_sanpham from sanpham s \n" +
            "inner join thuonghieu t on t.id = s.id_thuonghieu\n" +
            "inner join xuatxu x on x.id = s.id_xuatxu\n" +
            "where s.ma like %:timKiem% \n" +
            "or s.ten like %:timKiem% \n" +
            "or t.ten like %:timKiem% \n" +
            "or x.ten like %:timKiem% \n",
            nativeQuery = true)
    Page<SanPham> timKiem(@Param("timKiem")String timKiem, Pageable pageable);

//    SanPham findByThuongHieu(Integer id);
//
//    SanPham findByXuatXu(Integer id);

}
