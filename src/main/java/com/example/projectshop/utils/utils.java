package com.example.projectshop.utils;

import com.ibm.icu.text.Transliterator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class utils {

    public static String getNumberFromCode(String input) {
        // lấy ra các số từ mã
        Integer soHienTai = Integer.valueOf(input.substring(2));

        // số mới tăng lên 1
        Integer soMoi = soHienTai + 1;

        // Format số mới với độ dài cố định (vd: "00101")
        String soMoiChuoi = String.format("%05d", soMoi);

        return soMoiChuoi;
    }

    public static String renderCodeHoaDon(String input) {
        // lấy ra ngày hiện tại
        LocalDate ngayHienTai = LocalDate.now();

        // bỏ dấu `-` trong ngày hiện tại
        String ngayHienTaiMoi = String.valueOf(ngayHienTai).replaceAll("-","");

        if (input != null){
            // lấy ra các ký còn lại bắt đầu từ ký tự số 8
            Integer soHienTai = Integer.valueOf(input.substring(8));

            // lấy soHienTai +1
            Integer soMoi = soHienTai + 1;

            // Format số mới với độ dài cố định (vd: "00101")
            String soMoiChuoi = String.format("%04d", soMoi);

            return ngayHienTaiMoi+soMoiChuoi;
        }

        return ngayHienTaiMoi+"0001";

    }

    public static String tiengVietKhongDau(String input) {
        // Tạo một bộ dịch để chuyển đổi từ "NFD; [:Nonspacing Mark:] Remove; NFC"
        Transliterator transliterator = Transliterator.getInstance("NFD; [:Nonspacing Mark:] Remove; NFC");

        // Chuyển đổi
        String tiengVietKhongDau = transliterator.transliterate(input);

        // Bỏ các khoảng trắng
        String chuoiLienKet = tiengVietKhongDau.replaceAll("\\s","");

        return tiengVietKhongDau;
    }
}
