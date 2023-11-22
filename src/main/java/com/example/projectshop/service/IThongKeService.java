package com.example.projectshop.service;

import com.example.projectshop.dto.thongke.ThongKeDanhMuc;
import com.example.projectshop.dto.thongke.ThongKeDoanhThu;
import com.example.projectshop.dto.thongke.ThongKeHoaDon;
import com.example.projectshop.dto.thongke.ThongKeSanPham;

import java.sql.Date;
import java.util.List;

public interface IThongKeService {

    ThongKeHoaDon thongKeHoaDon();

    List<ThongKeDoanhThu> thongKeDoanhThu7NgayTruoc();

    List<ThongKeDoanhThu> thongKeDoanhThu28NgayTruoc();

    List<ThongKeDoanhThu> thongKeDoanhThu1NamTruoc();

    List<ThongKeDoanhThu> thongKeDoanhThu6ThangTruoc();

    List<ThongKeDoanhThu> thongKeDoanhThuTheoKhoang(Date startDate, Date endDate);

    List<ThongKeSanPham> thongKeSanPham7NgayTruoc();

    List<ThongKeSanPham> thongKeSanPham28NgayTruoc();

    List<ThongKeSanPham> thongKeSanPham1NamTruoc();

    List<ThongKeSanPham> thongKeSanPham6ThangTruoc();

    List<ThongKeSanPham> thongKeSanPhamTheoKhoang(Date startDate, Date endDate);

    List<ThongKeDanhMuc> thongKeDanhMuc7NgayTruoc();

    List<ThongKeDanhMuc> thongKeDanhMuc28NgayTruoc();

    List<ThongKeDanhMuc> thongKeDanhMuc1NamTruoc();

    List<ThongKeDanhMuc> thongKeDanhMuc6ThangTruoc();

    List<ThongKeDanhMuc> thongKeDanhMucTheoKhoang(Date startDate, Date endDate);
}
