package com.example.projectshop.dto.trahang;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TraHangChiTietRequest {
    private Integer id;
    private Integer idTraHang;
    private Integer idHoaDonChiTiet;
    private Integer soLuong;
    private BigDecimal donGia;
}
