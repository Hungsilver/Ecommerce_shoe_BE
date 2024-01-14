package com.example.projectshop.dto.chitietsanpham;

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
public class ImportExcelCTSP {
    private String stt;
    private String tenSanPham;
    private String soLuong;
    private String giaBan;
    private String mauSac;
    private String kichCo;
    private String chatLieuGiay;
    private String chatLieuDeGiay;
    private String trangThai;
    private String []listAnh;

}
