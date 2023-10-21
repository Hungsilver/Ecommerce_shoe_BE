package com.example.projectshop.dto.thuonghieu;

import com.example.projectshop.domain.SanPham;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ThuongHieuResponse {
    private Integer id;
    private String ten;
    private Integer trangThai;
    private List<SanPham> listSanPham;
}
