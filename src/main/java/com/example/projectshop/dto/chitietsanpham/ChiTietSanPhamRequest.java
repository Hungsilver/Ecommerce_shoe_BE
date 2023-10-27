package com.example.projectshop.dto.chitietsanpham;

import com.example.projectshop.domain.AnhSanPham;
import com.example.projectshop.domain.ChatLieuDeGiay;
import com.example.projectshop.domain.ChatLieuGiay;
import com.example.projectshop.domain.KichCo;
import com.example.projectshop.domain.MauSac;
import com.example.projectshop.domain.SanPham;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class ChiTietSanPhamRequest {
    private Integer id;

    private String ma;

    private Integer soLuong;

    private BigDecimal giaBan;

    private Date ngayTao;

    private Date ngayCapNhat;

    private Integer trangThai;

    private MauSac mausac;

    private KichCo kichco;

    private ChatLieuGiay chatLieuGiay;

    private ChatLieuDeGiay chatLieuDeGiay;

    private SanPham sanpham;


}
