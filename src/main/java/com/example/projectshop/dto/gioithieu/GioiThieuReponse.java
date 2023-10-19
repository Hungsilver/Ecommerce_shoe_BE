package com.example.projectshop.dto.gioithieu;


import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class GioiThieuReponse {
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
