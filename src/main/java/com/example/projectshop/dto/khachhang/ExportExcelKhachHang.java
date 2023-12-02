package com.example.projectshop.dto.khachhang;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExportExcelKhachHang {
    private Integer stt;

    private String hoTen;

    private String email;

    private String soDienThoai;

    private String ngaySinh;

    private String trangThai;
}
