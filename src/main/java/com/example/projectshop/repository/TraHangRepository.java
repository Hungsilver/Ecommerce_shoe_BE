package com.example.projectshop.repository;

import com.example.projectshop.domain.HoaDon;
import com.example.projectshop.domain.TraHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TraHangRepository extends JpaRepository<TraHang,Integer> {
    @Query(value = "select t from TraHang t " +
            "where (t.trangThai in :trangthai or :trangthai is null)\n "+
            "and (t.id = :id or :id is null)"
    )
    Page<TraHang> findAll(@Param("trangthai")Integer trangThai, @Param("id")String id, Pageable pageable);

    @Query(value = "select t from TraHang t where t.trangThai = :trangThai")
    List<TraHang> findAllByTrangThai(@Param("trangThai")Integer trangThai);

    @Query(value = "select t from TraHang t where t.hoaDon.id = :idHoaDon")
    List<TraHang> findByIdHoaDon(@Param("idHoaDon")Integer idHoaDon);

    @Query(value = "select t from TraHang t where t.hoaDon.khachHang.id = :idKhachHang order by t.ngayTao desc")
    List<TraHang> findByIdKhachHang(@Param("idKhachHang")Integer idKhachHang);


}
