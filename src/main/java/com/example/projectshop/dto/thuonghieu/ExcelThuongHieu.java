package com.example.projectshop.dto.thuonghieu;

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
public class ExcelThuongHieu {
    private Integer stt;
    private String tenThuongHieu;
    private String trangThai;
}
