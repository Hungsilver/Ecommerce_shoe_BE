package com.example.projectshop.dto.chitietsanpham;

import com.example.projectshop.domain.AnhSanPham;
import com.example.projectshop.domain.ChatLieuDeGiay;
import com.example.projectshop.domain.ChatLieuGiay;
import com.example.projectshop.domain.KichCo;
import com.example.projectshop.domain.MauSac;
import com.example.projectshop.domain.SanPham;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    private Integer soLuong;

    private BigDecimal giaBan;

    private Date ngayTao;

    private Date ngayCapNhat;

    private Integer trangThai;

    @NotNull(message = "Vui lòng không để trống")
    private Integer mauSac;

    @NotNull(message = "Vui lòng không để trống")
    private Integer kichCo;

    @NotNull(message = "Vui lòng không để trống")
    private Integer chatLieuGiay;

    @NotNull(message = "Vui lòng không để trống")
    private Integer chatLieuDeGiay;

    @NotNull(message = "Vui lòng không để trống")
    private Integer sanPham;

    @NotEmpty(message = "Vui lòng không để trống")
    private List<String> anhSanPhams;


}
