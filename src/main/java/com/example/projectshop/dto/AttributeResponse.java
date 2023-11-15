package com.example.projectshop.dto;

import com.example.projectshop.domain.ChatLieuDeGiay;
import com.example.projectshop.domain.ChatLieuGiay;
import com.example.projectshop.domain.DanhMuc;
import com.example.projectshop.domain.KichCo;
import com.example.projectshop.domain.MauSac;
import com.example.projectshop.domain.ThuongHieu;
import com.example.projectshop.domain.Xuatxu;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AttributeResponse {
    private List<DanhMuc> danhMucs;

    private List<ThuongHieu> thuongHieus;

    private List<Xuatxu> xuatxus;

    private List<MauSac> mauSacs;

    private List<KichCo> kichCos;

    private List<ChatLieuGiay> chatLieuGiays;

    private List<ChatLieuDeGiay> chatLieuDeGiays;
}
