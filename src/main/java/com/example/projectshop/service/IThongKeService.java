package com.example.projectshop.service;

import com.example.projectshop.dto.thongke.ThongKeDoanhThu;
import com.example.projectshop.dto.thongke.ThongKeHoaDon;

import java.sql.Date;
import java.util.List;

public interface IThongKeService {

    ThongKeHoaDon thongKeHoaDon();

    List<ThongKeDoanhThu> thongKeDoanhThu7NgayTruoc();

    List<ThongKeDoanhThu> thongKeDoanhThu28NgayTruoc();

    List<ThongKeDoanhThu> thongKeDoanhThu1NamTruoc();

    List<ThongKeDoanhThu> thongKeDoanhThu6ThangTruoc();

    List<ThongKeDoanhThu> thongKeDoanhThuTheoKhoang(Date startDate, Date endDate);
}
