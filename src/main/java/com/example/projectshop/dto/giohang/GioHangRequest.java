package com.example.projectshop.dto.giohang;

import com.example.projectshop.domain.GioHangChiTiet;
import com.example.projectshop.domain.KhachHang;
import lombok.Getter;
import lombok.Setter;


import java.util.List;
@Getter
@Setter
public class GioHangRequest {
    private Integer id;

    private KhachHang khachHang;

    private List<GioHangChiTiet> listGioHangChiTiet;
}
