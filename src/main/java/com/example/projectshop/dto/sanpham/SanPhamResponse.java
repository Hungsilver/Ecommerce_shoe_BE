package com.example.projectshop.dto.sanpham;

import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.DanhMuc;
import com.example.projectshop.domain.ThuongHieu;
import com.example.projectshop.domain.Xuatxu;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class SanPhamResponse {

    private Integer id;

    private String ten;

    private String anhChinh;

    private String moTa;

    private Integer trangThai;

    private ThuongHieu thuongHieu;

    private Xuatxu xuatXu;

    private DanhMuc danhMuc;

    private List<ChiTietSanPham> listChiTietSanPham;
}
