package com.example.projectshop.utilities;

import com.ibm.icu.text.Transliterator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class utility {

    public static String getNumberFromCode(String input) {
        // lấy ra các số từ mã
        Integer soHienTai = Integer.valueOf(input.substring(2));

        // số mới tăng lên 1
        Integer soMoi = soHienTai + 1;

        // Format số mới với độ dài cố định (vd: "00101")
        String soMoiChuoi = String.format("%05d", soMoi);

        return soMoiChuoi;
    }

    public static String renderCodeHoaDon() {
        // lấy ra ngày-tháng-năm hiện tại
        LocalDate ngayHienTai = LocalDate.now();
        // lấy ra giờ-giây-miligiay hiện tại
        LocalTime localTime = LocalTime.now();

        // bỏ dấu `-` trong ngày hiện tại
        String ngayHienTaiMoi = String.valueOf(ngayHienTai).replaceAll("-", "");

        LocalTime currentTimeWithNanos = LocalTime.now().truncatedTo(ChronoUnit.NANOS);
        String currentTimeWithNanosString = String.valueOf(currentTimeWithNanos.getNano());

        return "HD" + ngayHienTaiMoi + localTime.getHour() + localTime.getMinute() + localTime.getSecond() + currentTimeWithNanosString.substring(0, 2);

    }

    public static String tiengVietKhongDau(String input) {
        // Tạo một bộ dịch để chuyển đổi từ "NFD; [:Nonspacing Mark:] Remove; NFC"
        Transliterator transliterator = Transliterator.getInstance("NFD; [:Nonspacing Mark:] Remove; NFC");

        // Chuyển đổi
        String tiengVietKhongDau = transliterator.transliterate(input);

        // Bỏ các khoảng trắng
        String chuoiLienKet = tiengVietKhongDau.replaceAll("\\s", "");

        return tiengVietKhongDau;
    }

    public static String trangThaiDonHang(Integer input) {
        String trangThai;
        if (input == 0) {
            trangThai = "Chờ thanh toán";
        } else if (input == 1) {
            trangThai = "Hoàn thành";
        } else if (input == 2) {
            trangThai = "Chờ xác nhận";
        } else if (input == 3) {
            trangThai = "Chờ lấy hàng";
        } else if (input == 4) {
            trangThai = "Đang giao hàng";
        } else if (input == 5) {
            trangThai = "Đã giao hàng";
        } else if (input == 6) {
            trangThai = "Đã hủy";
        } else if (input == 7) {
            trangThai = "Trả hàng";
        } else {
            trangThai = null;
        }
        return trangThai;
    }

    public static String trangThaiThanhToan(Integer input) {
        String trangThai;
        if (input == 0) {
            trangThai = "Thanh toán tiền mặt";
        } else if (input == 1) {
            trangThai = "Thanh toán online";
        } else if (input == 2) {
            trangThai = "Trả sau";
        } else {
            trangThai = null;
        }
        return trangThai;
    }

    public static String trangThaiSanPham(Integer input) {
        String trangThai;
        if(input == null){
            trangThai = null;
        }
        else if (input == 0) {
            trangThai = "Hoạt Động";
        } else if (input == 1) {
            trangThai = "Không Hoạt Động";
        } else {
            trangThai = null;
        }
        return trangThai;
    }

    public static Integer getNumberByNameStatus(String input) {
        Integer trangThai;
        if (input == null) {
            trangThai = null;
        } else if (input.equalsIgnoreCase("Hoạt Động")) {
            trangThai = 0;
        } else if (input.equalsIgnoreCase("Không Hoạt Động")) {
            trangThai = 1;
        } else {
            trangThai = null;
        }
        return trangThai;
    }

}
