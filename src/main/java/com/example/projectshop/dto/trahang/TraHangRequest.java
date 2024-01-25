package com.example.projectshop.dto.trahang;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TraHangRequest {
    private Integer id;
    private Integer idHoaDon;
    private String lyDo;
    private Date ngayTao;
    private Date ngayCapNhat;
    private BigDecimal tienTraKhach;
    private Integer trangThai;
    private List<TraHangChiTietRequest> traHangChiTietRequests;
}
