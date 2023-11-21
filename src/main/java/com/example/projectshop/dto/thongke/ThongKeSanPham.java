package com.example.projectshop.dto.thongke;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ThongKeSanPham {
    private String tenSanPham;
    private Integer soLuong;
    private BigDecimal doanhThu;
}
