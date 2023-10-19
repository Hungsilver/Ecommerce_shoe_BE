package com.example.projectshop.dto.gioithieu;

import jakarta.persistence.Column;
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

}
