package com.example.projectshop.repository;

import com.example.projectshop.domain.HoaDon;
import com.example.projectshop.dto.thongke.ThongKeDoanhThu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ThongKeRepository extends JpaRepository<HoaDon, Integer> {

    // start thống kê hóa đơn
    @Query(value = "select count(h) from HoaDon h where h.trangThai = :trangThai or :trangThai is null")
    Integer demHoaDonByTrangThai(@Param("trangThai") Integer trangThai);
    // end thống kê hóa đơn

    @Query(value = "SELECT DATE_FORMAT(ngayTao, '%Y-%m-%d') AS ngayThang,SUM(tongTien) tongDoanhThu ,count(ngayTao) as tongDonHang\n" +
            "FROM hoadon\n" +
            "WHERE  ngayTao = CURDATE() and trangThai = 5\n" +
            "group by ngayTao", nativeQuery = true)
    List<Object[]> thongKeDoanhThuNgayHienTai();

    @Query(value = "SELECT DATE_FORMAT(h.ngayTao, '%Y-%m-%d') AS ngayThang, IFNULL(SUM(h.tongTien), 0) AS tongDoanhThu, COUNT(h.trangThai) AS tongDonHang " +
            "FROM HoaDon h " +
            "WHERE DATE_FORMAT(h.ngayTao, '%Y-%m-%d') >= DATE_FORMAT(CURDATE() - INTERVAL 7 DAY, '%Y-%m-%d')" +
            "AND h.trangThai = 5 " +
            "GROUP BY ngayThang,trangThai", nativeQuery = true)
    List<Object[]> thongKeDoanhThu7NgayTruoc();

    @Query(value = "SELECT DATE_FORMAT(h.ngayTao, '%Y-%m-%d') AS ngayThang, IFNULL(SUM(h.tongTien), 0) AS tongDoanhThu, COUNT(h.trangThai) AS tongDonHang " +
            "FROM HoaDon h " +
            "WHERE DATE_FORMAT(h.ngayTao, '%Y-%m-%d') >= DATE_FORMAT(CURDATE() - INTERVAL 28 DAY, '%Y-%m-%d')" +
            "AND h.trangThai = 5 " +
            "GROUP BY ngayThang,trangThai", nativeQuery = true)
    List<Object[]> thongKeDoanhThu28NgayTruoc();


    @Query(value = "SELECT DATE_FORMAT(h.ngayTao, '%Y-%m') AS ngayThang, IFNULL(SUM(h.tongTien), 0) AS tongDoanhThu, COUNT(h.trangThai) AS tongDonHang " +
            "FROM HoaDon h " +
            "WHERE DATE_FORMAT(h.ngayTao, '%Y-%m') >= DATE_FORMAT(CURDATE() - INTERVAL 12 month, '%Y-%m')" +
            "AND h.trangThai = 5 " +
            "GROUP BY ngayThang,trangThai", nativeQuery = true)
    List<Object[]> thongKeDoanhThu1NamTruoc();

    @Query(value = "SELECT DATE_FORMAT(h.ngayTao, '%Y-%m') AS ngayThang, IFNULL(SUM(h.tongTien), 0) AS tongDoanhThu, COUNT(h.trangThai) AS tongDonHang " +
            "FROM HoaDon h " +
            "WHERE DATE_FORMAT(h.ngayTao, '%Y-%m') >= DATE_FORMAT(CURDATE() - INTERVAL 6 month, '%Y-%m')" +
            "AND h.trangThai = 5 " +
            "GROUP BY ngayThang,trangThai", nativeQuery = true)
    List<Object[]> thongKeDoanhThu6ThangTruoc();

    @Query(value = "SELECT DATE_FORMAT(h.ngayTao, '%Y-%m-%d') AS ngayThang, IFNULL(SUM(h.tongTien), 0) AS tongDoanhThu, COUNT(h.trangThai) AS tongDonHang \n" +
            "            FROM HoaDon h \n" +
            "            WHERE h.ngayTao between :startDate and :endDate  " +
            "            AND h.trangThai = 5\n" +
            "            GROUP BY ngayThang,trangThai", nativeQuery = true)
    List<Object[]> thongKeDoanhThuTheoKhoang(@Param("startDate") Date startDate, @Param("endDate")Date endDate);
}
