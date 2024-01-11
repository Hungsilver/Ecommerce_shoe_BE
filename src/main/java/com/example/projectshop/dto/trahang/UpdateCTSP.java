package com.example.projectshop.dto.trahang;

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
public class UpdateCTSP {
    private Integer idChiTietSanPham;
    private Integer soLuong;
}
