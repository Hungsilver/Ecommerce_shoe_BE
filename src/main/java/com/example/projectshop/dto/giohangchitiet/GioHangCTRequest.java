package com.example.projectshop.dto.giohangchitiet;

import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.GioHang;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class GioHangCTRequest {

    private Integer id;

    private GioHang gioHang;

    private ChiTietSanPham chiTietSanPham;

    private Integer soLuong;

    private BigDecimal giaBan;
}
