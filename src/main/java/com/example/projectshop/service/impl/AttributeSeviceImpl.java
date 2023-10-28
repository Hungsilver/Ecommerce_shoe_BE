package com.example.projectshop.service.impl;

import com.example.projectshop.domain.ChatLieuGiay;
import com.example.projectshop.domain.KichCo;
import com.example.projectshop.dto.AttributeResponse;
import com.example.projectshop.repository.ChatLieuDeGiayRepository;
import com.example.projectshop.repository.ChatLieuGiayRepository;
import com.example.projectshop.repository.DanhMucRepository;
import com.example.projectshop.repository.KichCoRepository;
import com.example.projectshop.repository.MauSacRepository;
import com.example.projectshop.repository.ThuongHieuRepository;
import com.example.projectshop.repository.XuatXuRepository;
import com.example.projectshop.service.IAttributeSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.model.IAttribute;

@Service
public class AttributeSeviceImpl implements IAttributeSevice {
    @Autowired
    private DanhMucRepository danhMucRepository;

    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    @Autowired
    private XuatXuRepository xuatXuRepository;

    @Autowired
    private MauSacRepository mauSacRepository;

    @Autowired
    private KichCoRepository kichCoRepository;

    @Autowired
    private ChatLieuGiayRepository chatLieuGiayRepository;

    @Autowired
    private ChatLieuDeGiayRepository chatLieuDeGiayRepository;


    @Override
    public AttributeResponse findAll() {
        AttributeResponse attributeResponse = new AttributeResponse();
        attributeResponse.setDanhMucs(danhMucRepository.findAll());
        attributeResponse.setThuongHieus(thuongHieuRepository.findAll());
        attributeResponse.setXuatxus(xuatXuRepository.findAll());
        attributeResponse.setMauSacs(mauSacRepository.findAll());
        attributeResponse.setKichCos(kichCoRepository.findAll());
        attributeResponse.setChatLieuGiays(chatLieuGiayRepository.findAll());
        attributeResponse.setChatLieuDeGiays(chatLieuDeGiayRepository.findAll());
        return attributeResponse;
    }
}
