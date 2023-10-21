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

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
    @Query(value = "select distinct s.id,s.ma,s.ten,s.anhChinh,s.moTa,s.trangThai,s.id_thuongHieu,s.id_xuatXu from sanpham s\n" +
            "inner join chitietsanpham c on c.id_sanpham = s.id\n" +
            "inner join thuonghieu t on t.id = s.id_thuonghieu\n" +
            "inner join xuatxu x on x.id = s.id_xuatxu\n" +
            "inner join mausac m on m.id = c.id_mausac\n" +
            "inner join kichco k on k.id = c.id_kichco\n" +
            "inner join chatlieugiay cl on cl.id = c.id_chatlieugiay\n" +
            "inner join chatlieudegiay cld on cld.id = c.id_chatlieudegiay\n" +
            "where c.giaban between :priceMin and :priceMax\n" +
            "and t.id = :thuongHieu\n" +
            "and x.id = :xuatXu\n" +
            "and m.id = :mauSac\n" +
            "and cl.id = :chatLieuGiay\n" +
            "and cld.id = :chatLieuDeGiay", nativeQuery = true)
    Page<SanPham> getAllByParam(
            @Param("priceMin") BigDecimal priceMin,
            @Param("priceMax") BigDecimal priceMax,
            @Param("thuongHieu") Integer thuongHieu,
            @Param("xuatXu") Integer xuatXu,
            @Param("mauSac") Integer mauSac,
            @Param("chatLieuGiay") Integer chatLieuGiay,
            @Param("chatLieuDeGiay") Integer chatLieuDeGiay,
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

    @Query(value = "select c.id,c.soluong,c.giaban,c.ngaytao,c.trangthai,c.id_mausac,c.id_kichco,c.id_chatlieugiay,c.id_chatlieudegiay,c.id_sanpham from sanpham c \n" +
            "inner join thuonghieu t on t.id = c.id_thuonghieu\n" +
            "inner join xuatxu x on x.id = c.id_xuatxu\n" +
            "where c.ma like %:timKiem% \n" +
            "or c.ten like %:timKiem% \n" +
            "or t.ten like %:timKiem% \n" +
            "or x.ten like %:timKiem% \n",
            nativeQuery = true)
    Page<SanPham> timKiem(@Param("timKiem")String timKiem, Pageable pageable);


}
