package com.example.projectshop.repository;

import com.example.projectshop.domain.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, Integer> {
//    @Query("select sum(h.donGia*h.soLuong) from HoaDonChiTiet h where h.hoaDon.id = :id")
    @Query("select sum(h.donGia*h.soLuong) from HoaDonChiTiet h where h.hoaDon.id = :id")
    BigDecimal tongTienByIdHoaDon(@Param("id") Integer id);

    @Query("select h from HoaDonChiTiet h where h.hoaDon.id = :idHoaDon and h.chiTietSanPham.id = :idCTSP")
    HoaDonChiTiet findByIdHoaDonAndIdCTSP(@Param("idHoaDon") Integer idHoaDon,
                                        @Param("idCTSP") Integer idCTSP);

    @Query("select h from HoaDonChiTiet h where h.hoaDon.id = :idHoaDon")
    List<HoaDonChiTiet> findByIdHoaDon(@Param("idHoaDon") Integer idHoaDon);
}
