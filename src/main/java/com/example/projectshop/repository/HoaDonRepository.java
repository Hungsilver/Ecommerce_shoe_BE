package com.example.projectshop.repository;

import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.HoaDon;
import com.example.projectshop.domain.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {
    @Query(value = "select h from HoaDon h " +
            "where (h.trangThai in :trangthai or :trangthai is null)\n " +
            "and (h.id = :id or :id is null)"
    )
    Page<HoaDon> findAll(@Param("trangthai") Integer trangThai, @Param("id") String id, Pageable pageable);

    @Query(value = "select * from hoadon order by id desc limit 1", nativeQuery = true)
    HoaDon getTop1ByIdMax();

    @Query(value = "select h from HoaDon h where h.maHoaDon = :maHoaDon")
    Optional<HoaDon> findByMa(@Param("maHoaDon") String maHoaDon);

    @Query(value = "select h from HoaDon h where h.trangThai = :trangThai")
    List<HoaDon> findByTrangThai(@Param("trangThai") Integer trangThai);

    //    Optional<HoaDon> findTopByTrangThaiOrderByNgayTaoDesc(Integer trangThai);
    Optional<HoaDon> findTopByTrangThaiAndPhuongThucThanhToanOrderByNgayTaoDesc(Integer trangThai, Integer phuongThucThanhToan);


}
