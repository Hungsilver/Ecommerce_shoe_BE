package com.example.projectshop.dto.danhmuc;

import com.example.projectshop.domain.SanPham;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DanhMucRequest {
    private Integer id;
    private String ten;
    private Integer trangThai;
    private SanPham sanPham;

}
