package com.example.projectshop.utilities;

import com.ibm.icu.text.Transliterator;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

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
        if (input == null) {
            trangThai = null;
        } else if (input == 1) {
            trangThai = "Hoạt Động";
        } else if (input == 0) {
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

    public static int generateOTP() {
        Random rand = new Random();

        int num1 = rand.nextInt(10) + 1;
        int num2 = rand.nextInt(10);
        int num3 = rand.nextInt(10);
        int num4 = rand.nextInt(10);

        return Integer.parseInt(num1 + "" + num2 + "" + num3 + "" + num4);
    }

    public static String generateRandomString(Integer n) {
        // Tạo mảng chứa tất cả các chữ cái trong bảng chữ cái
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        // Tạo đối tượng Random
        Random random = new Random();

        // Tạo chuỗi chứa 6 chữ cái ngẫu nhiên
        String randomString = "";
        for (int i = 0; i < n; i++) {
            // Lấy ngẫu nhiên một chữ cái từ mảng alphabet
            char randomCharacter = alphabet.charAt(random.nextInt(alphabet.length()));

            // Thêm chữ cái đó vào chuỗi
            randomString += randomCharacter;
        }

        return randomString;
    }

}
