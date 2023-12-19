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
import java.util.Optional;

@Repository
public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham, Integer> {
    @Query(value = "select * from chitietsanpham  order by giaban asc limit 1", nativeQuery = true)
    ChiTietSanPham getTop1ByPriceMin();

    @Query(value = "select * from chitietsanpham  order by giaban desc limit 1", nativeQuery = true)
    ChiTietSanPham getTop1ByPriceMax();

    @Query(value = "select c from ChiTietSanPham c " +
            "inner join MauSac m on m.id = c.mauSac.id\n" +
            "inner join KichCo k on k.id = c.kichCo.id\n" +
            "inner join ChatLieuGiay clg on clg.id = c.chatLieuGiay.id\n" +
            "inner join ChatLieuDeGiay cldg on cldg.id = c.chatLieuDeGiay.id\n "+
            "inner join SanPham sp on sp.id = c.sanPham.id\n "+
            "where c.giaBan between :priceMin and :priceMax \n " +
            "and (m.id in :mauSac or :mauSac is null)\n " +
            "and (k.id in :kichCo or :kichCo is null)" +
            "and (clg.id in :chatLieuGiay or :chatLieuGiay is null)\n " +
            "and (cldg.id in :chatLieuDeGiay or :chatLieuDeGiay is null)" +
            "and (sp.id = :sanPham or :sanPham is null)" +
            "and (c.ma like %:ma% or :ma is null)\n"
    )
    Page<ChiTietSanPham> filter(
            @Param("priceMin") BigDecimal priceMin,
            @Param("priceMax") BigDecimal priceMax,
            @Param("mauSac") List<Integer>  mauSac,
            @Param("kichCo") List<Integer>  kichCo,
            @Param("chatLieuGiay") List<Integer>  chatLieuGiay,
            @Param("chatLieuDeGiay") List<Integer>  chatLieuDeGiay,
            @Param("sanPham") Integer sanPham,
            @Param("ma") String ma,
            Pageable pageable
    );


    @Query(value = "select c from ChiTietSanPham c where c.ma = :ma")
    Optional<ChiTietSanPham> findByMa(@Param("ma")String ma);

    @Query(value = "select c from ChiTietSanPham c where c.sanPham.id = :idSanPham")
    List<ChiTietSanPham> findByIdSanPham(@Param("idSanPham")Integer idSanPham);

    @Query(value = "select c.ma," +
            "c.sanPham.ten," +
            "c.soLuong," +
            "c.giaBan," +
            "c.ngayTao," +
            "c.ngayCapNhat," +
            "c.trangThai," +
            "c.mauSac.ten," +
            "c.kichCo.size," +
            "c.chatLieuGiay.ten," +
            "c.chatLieuDeGiay.ten," +
            "c.sanPham.xuatXu.ten," +
            "c.sanPham.thuongHieu.ten" +
            " from ChiTietSanPham c")
    List<Object[]> exportExcel();

//    @Query("SELECT ctsp FROM ChiTietSanPham ctsp LEFT JOIN FETCH ctsp.listGioHangChiTiet WHERE ctsp.id = :id")
//    ChiTietSanPham fetchWithGioHangChiTiet(@Param("id") Integer id);




}
