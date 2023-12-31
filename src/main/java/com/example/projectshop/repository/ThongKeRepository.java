package com.example.projectshop.repository;

import com.example.projectshop.domain.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ThongKeRepository extends JpaRepository<HoaDon, Integer> {

    // start thống kê hóa đơn
    @Query(value = "select count(h) from HoaDon h where h.trangThai = :trangThai or :trangThai is null")
    Integer demHoaDonByTrangThai(@Param("trangThai") Integer trangThai);
    // end thống kê hóa đơn

    // start thống kế doanh thu
    @Query(value = "SELECT DATE_FORMAT(ngayTao, '%Y-%m-%d') AS ngayThang,SUM(tongTien) tongDoanhThu ,count(ngayTao) as tongDonHang\n" +
            "FROM hoadon\n" +
            "WHERE  ngayTao = CURDATE() AND (h.trangThai = 5 OR h.trangThai = 1)\n" +
            "group by ngayTao", nativeQuery = true)
    List<Object[]> thongKeDoanhThuNgayHienTai();

    @Query(value = "SELECT DATE_FORMAT(h.ngayTao, '%Y-%m-%d') AS ngayThang, IFNULL(SUM(h.tongTien), 0) AS tongDoanhThu, COUNT(h.trangThai) AS tongDonHang " +
            "FROM HoaDon h " +
            "WHERE DATE_FORMAT(h.ngayTao, '%Y-%m-%d') >= DATE_FORMAT(CURDATE() - INTERVAL 7 DAY, '%Y-%m-%d')" +
            "AND (h.trangThai = 5 OR h.trangThai = 1)" +
            "GROUP BY ngayThang,trangThai", nativeQuery = true)
    List<Object[]> thongKeDoanhThu7NgayTruoc();

    @Query(value = "SELECT DATE_FORMAT(h.ngayTao, '%Y-%m-%d') AS ngayThang, IFNULL(SUM(h.tongTien), 0) AS tongDoanhThu, COUNT(h.trangThai) AS tongDonHang " +
            "FROM HoaDon h " +
            "WHERE DATE_FORMAT(h.ngayTao, '%Y-%m-%d') >= DATE_FORMAT(CURDATE() - INTERVAL 28 DAY, '%Y-%m-%d')" +
            "AND (h.trangThai = 5 OR h.trangThai = 1)" +
            "GROUP BY ngayThang,trangThai", nativeQuery = true)
    List<Object[]> thongKeDoanhThu28NgayTruoc();


    @Query(value = "SELECT DATE_FORMAT(h.ngayTao, '%Y-%m') AS ngayThang, IFNULL(SUM(h.tongTien), 0) AS tongDoanhThu, COUNT(h.trangThai) AS tongDonHang " +
            "FROM HoaDon h " +
            "WHERE DATE_FORMAT(h.ngayTao, '%Y-%m') >= DATE_FORMAT(CURDATE() - INTERVAL 12 month, '%Y-%m')" +
            "AND (h.trangThai = 5 OR h.trangThai = 1)" +
            "GROUP BY ngayThang,trangThai", nativeQuery = true)
    List<Object[]> thongKeDoanhThu1NamTruoc();

    @Query(value = "SELECT DATE_FORMAT(h.ngayTao, '%Y-%m') AS ngayThang, IFNULL(SUM(h.tongTien), 0) AS tongDoanhThu, COUNT(h.trangThai) AS tongDonHang " +
            "FROM HoaDon h " +
            "WHERE DATE_FORMAT(h.ngayTao, '%Y-%m') >= DATE_FORMAT(CURDATE() - INTERVAL 6 month, '%Y-%m')" +
            "AND (h.trangThai = 5 OR h.trangThai = 1)" +
            "GROUP BY ngayThang,trangThai", nativeQuery = true)
    List<Object[]> thongKeDoanhThu6ThangTruoc();

    @Query(value = "SELECT DATE_FORMAT(h.ngayTao, '%Y-%m-%d') AS ngayThang, IFNULL(SUM(h.tongTien), 0) AS tongDoanhThu, COUNT(h.trangThai) AS tongDonHang \n" +
            "            FROM HoaDon h \n" +
            "            WHERE h.ngayTao between :startDate and :endDate  " +
            "            AND (h.trangThai = 5 OR h.trangThai = 1)\n" +
            "            GROUP BY ngayThang,trangThai", nativeQuery = true)
    List<Object[]> thongKeDoanhThuTheoKhoang(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
    // end thống kê doanh thu

    // start thống kê sản phẩm
    @Query(value = "select s.ten as tenSanPham,\n" +
            "       sum(hdct.soLuong) as soluong,\n" +
            "       sum(hdct.soLuong*hdct.donGia) as doanhthu\n" +
            "from hoadon h\n" +
            "inner join hoadonchitiet hdct on hdct.id_HoaDon = h.id\n" +
            "inner join chitietsanpham c on hdct.id_ChiTietSanPham = c.id\n" +
            "inner join sanpham s on c.id_SanPham = s.id" +
            "WHERE AND (h.trangThai = 5 OR h.trangThai = 1)\n" +
            "group by s.id, s.ten\n", nativeQuery = true)
    List<Object[]> tongThongKeSanPham();

    @Query(value = "select s.ten as tenSanPham,\n" +
            "       sum(hdct.soLuong) as soluong,\n" +
            "       sum(hdct.soLuong*hdct.donGia) as doanhthu\n" +
            "from hoadon h\n" +
            "inner join hoadonchitiet hdct on hdct.id_HoaDon = h.id\n" +
            "inner join chitietsanpham c on hdct.id_ChiTietSanPham = c.id\n" +
            "inner join sanpham s on c.id_SanPham = s.id\n" +
            "WHERE DATE_FORMAT(h.ngayTao, '%Y-%m-%d') >= DATE_FORMAT(CURDATE() - INTERVAL 7 DAY, '%Y-%m-%d') AND (h.trangThai = 5 OR h.trangThai = 1)\n" +
            "group by s.id, s.ten\n", nativeQuery = true)
    List<Object[]> thongKeSanPham7NgayTruoc();

    @Query(value = "select s.ten as tenSanPham,\n" +
            "       sum(hdct.soLuong) as soluong,\n" +
            "       sum(hdct.soLuong*hdct.donGia) as doanhthu\n" +
            "from hoadon h\n" +
            "inner join hoadonchitiet hdct on hdct.id_HoaDon = h.id\n" +
            "inner join chitietsanpham c on hdct.id_ChiTietSanPham = c.id\n" +
            "inner join sanpham s on c.id_SanPham = s.id\n" +
            "WHERE DATE_FORMAT(h.ngayTao, '%Y-%m-%d') >= DATE_FORMAT(CURDATE() - INTERVAL 28 DAY, '%Y-%m-%d') AND (h.trangThai = 5 OR h.trangThai = 1)\n" +
            "group by s.id, s.ten\n", nativeQuery = true)
    List<Object[]> thongKeSanPham28NgayTruoc();

    @Query(value = "select s.ten as tenSanPham,\n" +
            "       sum(hdct.soLuong) as soluong,\n" +
            "       sum(hdct.soLuong*hdct.donGia) as doanhthu\n" +
            "from hoadon h\n" +
            "inner join hoadonchitiet hdct on hdct.id_HoaDon = h.id\n" +
            "inner join chitietsanpham c on hdct.id_ChiTietSanPham = c.id\n" +
            "inner join sanpham s on c.id_SanPham = s.id\n" +
            "WHERE DATE_FORMAT(h.ngayTao, '%Y-%m-%d') >= DATE_FORMAT(CURDATE() - INTERVAL 6 MONTH , '%Y-%m-%d') AND (h.trangThai = 5 OR h.trangThai = 1)\n" +
            "group by s.id, s.ten\n", nativeQuery = true)
    List<Object[]> thongKeSanPham6ThangTruoc();

    @Query(value = "select s.ten as tenSanPham,\n" +
            "       sum(hdct.soLuong) as soluong,\n" +
            "       sum(hdct.soLuong*hdct.donGia) as doanhthu\n" +
            "from hoadon h\n" +
            "inner join hoadonchitiet hdct on hdct.id_HoaDon = h.id\n" +
            "inner join chitietsanpham c on hdct.id_ChiTietSanPham = c.id\n" +
            "inner join sanpham s on c.id_SanPham = s.id\n" +
            "WHERE DATE_FORMAT(h.ngayTao, '%Y-%m-%d') >= DATE_FORMAT(CURDATE() - INTERVAL 12 MONTH , '%Y-%m-%d') AND (h.trangThai = 5 OR h.trangThai = 1)\n" +
            "group by s.id, s.ten\n", nativeQuery = true)
    List<Object[]> thongKeSanPham1NamTruoc();

    @Query(value = "select s.ten as tenSanPham,\n" +
            "       sum(hdct.soLuong) as soluong,\n" +
            "       sum(hdct.soLuong*hdct.donGia) as doanhthu\n" +
            "from hoadon h\n" +
            "inner join hoadonchitiet hdct on hdct.id_HoaDon = h.id\n" +
            "inner join chitietsanpham c on hdct.id_ChiTietSanPham = c.id\n" +
            "inner join sanpham s on c.id_SanPham = s.id\n" +
            "WHERE h.ngayTao between :startDate and :endDate" +
            "AND (h.trangThai = 5 OR h.trangThai = 1)\n" +
            "group by s.id, s.ten", nativeQuery = true)
    List<Object[]> thongKeSanPhamTheoKhoang(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    // end thống kê sản phẩm

    // start thống kê danh mục
    @Query(value = "select  d.ten as tenDoanhMuc,\n" +
            "       sum(hdct.soLuong) as soluong,\n" +
            "       sum(hdct.soLuong*hdct.donGia) as doanhthu\n" +
            "from hoadon h\n" +
            "inner join hoadonchitiet hdct on hdct.id_HoaDon = h.id\n" +
            "inner join chitietsanpham c on hdct.id_ChiTietSanPham = c.id\n" +
            "inner join sanpham s on c.id_SanPham = s.id\n" +
            "inner join danhmuc d on s.id_DanhMuc = d.id\n" +
            "where (h.trangThai = 5 OR h.trangThai = 1)" +
            "group by d.ten\n", nativeQuery = true)
    List<Object[]> tongThongKeDanhMuc();

    @Query(value = "select  d.ten as tenDoanhMuc,\n" +
            "       sum(hdct.soLuong) as soluong,\n" +
            "       sum(hdct.soLuong*hdct.donGia) as doanhthu\n" +
            "from hoadon h\n" +
            "inner join hoadonchitiet hdct on hdct.id_HoaDon = h.id\n" +
            "inner join chitietsanpham c on hdct.id_ChiTietSanPham = c.id\n" +
            "inner join sanpham s on c.id_SanPham = s.id\n" +
            "inner join danhmuc d on s.id_DanhMuc = d.id\n" +
            "WHERE DATE_FORMAT(h.ngayTao, '%Y-%m-%d') >= DATE_FORMAT(CURDATE() - INTERVAL 7 DAY, '%Y-%m-%d') AND (h.trangThai = 5 OR h.trangThai = 1)\n" +
            "group by d.ten", nativeQuery = true)
    List<Object[]> thongKeDanhMuc7NgayTruoc();

    @Query(value = "select  d.ten as tenDoanhMuc,\n" +
            "       sum(hdct.soLuong) as soluong,\n" +
            "       sum(hdct.soLuong*hdct.donGia) as doanhthu\n" +
            "from hoadon h\n" +
            "inner join hoadonchitiet hdct on hdct.id_HoaDon = h.id\n" +
            "inner join chitietsanpham c on hdct.id_ChiTietSanPham = c.id\n" +
            "inner join sanpham s on c.id_SanPham = s.id\n" +
            "inner join danhmuc d on s.id_DanhMuc = d.id\n" +
            "WHERE DATE_FORMAT(h.ngayTao, '%Y-%m-%d') >= DATE_FORMAT(CURDATE() - INTERVAL 28 DAY, '%Y-%m-%d') AND (h.trangThai = 5 OR h.trangThai = 1)\n" +
            "group by d.ten", nativeQuery = true)
    List<Object[]> thongKeDanhMuc28NgayTruoc();

    @Query(value = "select  d.ten as tenDoanhMuc,\n" +
            "       sum(hdct.soLuong) as soluong,\n" +
            "       sum(hdct.soLuong*hdct.donGia) as doanhthu\n" +
            "from hoadon h\n" +
            "inner join hoadonchitiet hdct on hdct.id_HoaDon = h.id\n" +
            "inner join chitietsanpham c on hdct.id_ChiTietSanPham = c.id\n" +
            "inner join sanpham s on c.id_SanPham = s.id\n" +
            "inner join danhmuc d on s.id_DanhMuc = d.id\n" +
            "WHERE DATE_FORMAT(h.ngayTao, '%Y-%m-%d') >= DATE_FORMAT(CURDATE() - INTERVAL 6 MONTH , '%Y-%m-%d') AND (h.trangThai = 5 OR h.trangThai = 1)\n" +
            "group by d.ten", nativeQuery = true)
    List<Object[]> thongKeDanhMuc6ThangTruoc();

    @Query(value = "select  d.ten as tenDoanhMuc,\n" +
            "       sum(hdct.soLuong) as soluong,\n" +
            "       sum(hdct.soLuong*hdct.donGia) as doanhthu\n" +
            "from hoadon h\n" +
            "inner join hoadonchitiet hdct on hdct.id_HoaDon = h.id\n" +
            "inner join chitietsanpham c on hdct.id_ChiTietSanPham = c.id\n" +
            "inner join sanpham s on c.id_SanPham = s.id\n" +
            "inner join danhmuc d on s.id_DanhMuc = d.id\n" +
            "WHERE DATE_FORMAT(h.ngayTao, '%Y-%m-%d') >= DATE_FORMAT(CURDATE() - INTERVAL 12 MONTH , '%Y-%m-%d') AND (h.trangThai = 5 OR h.trangThai = 1)\n" +
            "group by d.ten", nativeQuery = true)
    List<Object[]> thongKeDanhMuc12ThangTruoc();

    @Query(value = "select  d.ten as tenDoanhMuc,\n" +
            "       sum(hdct.soLuong) as soluong,\n" +
            "       sum(hdct.soLuong*hdct.donGia) as doanhthu\n" +
            "from hoadon h\n" +
            "inner join hoadonchitiet hdct on hdct.id_HoaDon = h.id\n" +
            "inner join chitietsanpham c on hdct.id_ChiTietSanPham = c.id\n" +
            "inner join sanpham s on c.id_SanPham = s.id\n" +
            "inner join danhmuc d on s.id_DanhMuc = d.id\n" +
            "WHERE h.ngayTao between :startDate and :endDate AND (h.trangThai = 5 OR h.trangThai = 1)\n" +
            "group by d.ten", nativeQuery = true)
    List<Object[]> thongKeDanhMucTheoKhoang(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    // end thống kê danh mục


}

