package com.example.projectshop.dto.chatlieudegiay;

import com.example.projectshop.domain.ChiTietSanPham;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatLieuDeGiayResponse {

    private Integer id;

    private String ten;

    private Integer trangThai;

    private List<ChiTietSanPham> listChiTietSanPham;
}
