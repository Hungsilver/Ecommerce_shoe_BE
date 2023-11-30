package com.example.projectshop.dto.sanpham;


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
public class ImportExcelSanPham {
    private Integer stt;

    private String tenSanPham;

    private String anhChinh;

    private String moTa;

    private String trangThai;

    private String thuongHieu;

    private String xuatXu;

    private String danhMuc;
}
