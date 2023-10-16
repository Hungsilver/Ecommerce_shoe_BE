package com.example.projectshop.dto.sanpham;

import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.Thuonghieu;
import com.example.projectshop.domain.Xuatxu;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SanPhamRequest {

    private Integer id;

    private String ma;

    private String ten;

    private String anhChinh;

    private String moTa;

    private Integer trangThai;

    private Thuonghieu thuonghieu;

    private Xuatxu xuatxu;

    private List<ChiTietSanPham> listChiTietSanPham;
}
