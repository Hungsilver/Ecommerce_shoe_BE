package com.example.projectshop.dto.gioithieu;

import com.example.projectshop.domain.NhanVien;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class GioiThieuRequest {
    private Integer id;

    private String tenGioiThieu;

    private String noiDung;

    private String logo;

    private String banner;

    private String moTa;

    private Date ngayTao;

    private Date ngayXoa;

    private Integer trangThai;

    private Integer nhanVien;
}
