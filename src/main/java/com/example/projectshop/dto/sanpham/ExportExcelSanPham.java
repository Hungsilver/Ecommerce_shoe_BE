package com.example.projectshop.dto.sanpham;

import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.DanhMuc;
import com.example.projectshop.domain.ThuongHieu;
import com.example.projectshop.domain.Xuatxu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExportExcelSanPham {

    private Integer stt;

    private String maSanPham;

    private String ten;

    private String anhChinh;

    private String moTa;

    private String trangThai;

    private String thuongHieu;

    private String xuatXu;

    private String danhMuc;
}
