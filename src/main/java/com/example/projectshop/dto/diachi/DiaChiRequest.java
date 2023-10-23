package com.example.projectshop.dto.diachi;

import com.example.projectshop.domain.KhachHang;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiaChiRequest {
    private Integer id;

    private String diaChi;

    private String phuongXa;

    private String quanHuyen;

    private String tinhThanh;

    private Integer trangThai;

    private KhachHang khachHang;
}
