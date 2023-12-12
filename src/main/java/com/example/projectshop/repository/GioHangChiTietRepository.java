package com.example.projectshop.repository;

import com.example.projectshop.domain.GioHangChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet,Integer> {

//    @Query(value = "select sp.ten,ctsp.mauSac.ten as mausacten,ctsp.kichCo.size as  kichcoTen,ctsp.kichCo.size, gio.giaBan,gio.soLuong from GioHangChiTiet  gio join gio.chiTietSanPham ctsp join  ctsp.sanPham sp")
//    Page<GioHangChiTiet> findGioHangChiTietInfo(Pageable pageable);

   @Query("SELECT giohangchiTiet FROM GioHang gioHang JOIN gioHang.listGioHangChiTiet giohangchiTiet WHERE gioHang.id = :id")
    List<GioHangChiTiet> findGioHangChiTietByGioHangId(Integer id);

    @Query("SELECT ghct FROM GioHangChiTiet ghct WHERE ghct.gioHang.id = :idGH and ghct.chiTietSanPham.id = :idCTSP")
    GioHangChiTiet findByIdGioHangAndIdCTSP(@Param("idGH")Integer idGh,@Param("idCTSP")Integer idCTSP);

}
