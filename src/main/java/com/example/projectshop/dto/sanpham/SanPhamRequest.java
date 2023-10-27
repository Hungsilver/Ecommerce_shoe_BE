package com.example.projectshop.dto.sanpham;

import com.example.projectshop.domain.DanhMuc;
import com.example.projectshop.domain.ThuongHieu;
import com.example.projectshop.domain.Xuatxu;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class SanPhamRequest {

    private Integer id;

    private String ten;

    private String anhChinh;

    private String moTa;

    private String ngayTao;

    private String ngayCapNhat;

    private Integer trangThai;

    private ThuongHieu thuongHieu;

    private Xuatxu xuatXu;

    private DanhMuc danhMuc;

}
