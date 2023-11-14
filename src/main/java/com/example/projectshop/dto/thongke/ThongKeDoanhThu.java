package com.example.projectshop.dto.thongke;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ThongKeDoanhThu {
    private String ngayThang;
    private BigDecimal tongDoanhThu;
    private Integer tongDonHang;
}
