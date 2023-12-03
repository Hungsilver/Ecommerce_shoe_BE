package com.example.projectshop.dto.gioithieu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ImportExcelGioiThieu {
    private Integer stt;

    private String tieuDe;

    private String noiDung;

    private String logo;

    private String banner;

    private String moTa;

    private String nhanVien;
}
