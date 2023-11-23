package com.example.projectshop.dto.thongke;

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
public class ThongKeDanhMuc {
    private String tenDanhMuc;
    private Integer soLuong;
    private BigDecimal doanhThu;
}
