package com.example.projectshop.dto.mausac;

import com.example.projectshop.domain.ChiTietSanPham;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MauSacResponse {
    private Integer id;

    private String ten;

    private Integer trangThai;

    private List<ChiTietSanPham> listChiTietSanPham;
}
