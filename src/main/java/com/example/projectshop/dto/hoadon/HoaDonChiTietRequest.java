package com.example.projectshop.dto.hoadon;

import com.example.projectshop.domain.HoaDon;
import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamRequest;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class HoaDonChiTietRequest {
    private Integer idHoaDon;
    private BigDecimal donGia;
    private Integer soLuong;
    private Integer idChiTietSanPham;
}
