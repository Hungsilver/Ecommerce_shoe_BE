package com.example.projectshop.dto.kichco;

import com.example.projectshop.domain.ChiTietSanPham;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KichCoResponse {
    private Integer id;
    private Integer size;
    private Integer trangThai;
    private List<ChiTietSanPham> listChiTietSanPham;
}
