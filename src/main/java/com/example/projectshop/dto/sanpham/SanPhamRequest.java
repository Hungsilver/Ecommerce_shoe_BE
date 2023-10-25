package com.example.projectshop.dto.sanpham;

import com.example.projectshop.domain.DanhMuc;
import com.example.projectshop.domain.ThuongHieu;
import com.example.projectshop.domain.Xuatxu;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SanPhamRequest {

    private Integer id;

    private String ma;

    private String ten;

    private String anhChinh;

    private String moTa;

    private Integer trangThai;

    private ThuongHieu thuongHieu;

    private Xuatxu xuatXu;

    private DanhMuc danhMuc;

}
