package com.example.projectshop.dto.sanpham;

import com.example.projectshop.domain.DanhMuc;
import com.example.projectshop.domain.ThuongHieu;
import com.example.projectshop.domain.Xuatxu;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    private Integer thuongHieu;

    private Integer xuatXu;

    private Integer danhMuc;

//    @JsonCreator
//    public SanPhamRequest(@JsonProperty("thuongHieu") ThuongHieu thuongHieu) {
//        this.thuongHieu = thuongHieu;
//    }
//
//    @JsonCreator
//    public SanPhamRequest(@JsonProperty("xuatXu") Xuatxu xuatXu) {
//        this.xuatXu = xuatXu;
//    }
//
//    @JsonCreator
//    public SanPhamRequest(@JsonProperty("danhMuc") DanhMuc danhMuc) {
//        this.danhMuc = danhMuc;
//    }

}
