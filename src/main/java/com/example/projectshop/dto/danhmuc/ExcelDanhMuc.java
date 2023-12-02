package com.example.projectshop.dto.danhmuc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExcelDanhMuc {
    private Integer stt;
    private String tenDanhMuc;
    private String trangThai;
}
