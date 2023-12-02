package com.example.projectshop.dto.phieugiamgia;

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
public class ImportExcelPGG {
    private String stt;

    private String tieuDe;

    private String chietKhau;

    private String hinhThucGiamGia;

    private String thoiGianBatDau;

    private String thoiGianKetThuc;

    private String moTa;
}
