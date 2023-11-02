package com.example.projectshop.service.impl;

import com.example.projectshop.domain.ChatLieuGiay;
import com.example.projectshop.domain.Xuatxu;
import com.example.projectshop.dto.chatlieudegiay.ChatLieuDeGiayRequest;
import com.example.projectshop.dto.chatlieugiay.ChatLieuGiayRequest;
import com.example.projectshop.dto.chatlieugiay.ChatLieuGiayResponse;
import com.example.projectshop.repository.ChatLieuGiayRepository;
import com.example.projectshop.repository.ChiTietSanPhamRepository;
import com.example.projectshop.repository.HoaDonChiTietRepository;
import com.example.projectshop.service.IChatLieuGiayService;
import com.example.projectshop.service.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatLieuGiaySeviceImpl implements IChatLieuGiayService {
    @Autowired
    private ChatLieuGiayRepository chatLieuGiayRepository;

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepo;


    @Override
    public Page<ChatLieuGiay> getAll(Pageable pageable) {
        return chatLieuGiayRepository.findAll(pageable);
    }

    @Override
    public Optional<ChatLieuGiay> findById(Integer id) {
        return chatLieuGiayRepository.findById(id);
    }

    @Override
    public Page<ChatLieuGiay> findAllByName(String name, Pageable pageable) {
        return chatLieuGiayRepository.findAllByTen("%"+name+"%", pageable);
    }

    @Override
    public ChatLieuGiay create(ChatLieuGiay chatLieuGiay) {
        return chatLieuGiayRepository.save(chatLieuGiay);
    }

    @Override
    public ChatLieuGiay update(ChatLieuGiay chatLieuGiay, Integer id) {
        chatLieuGiay.setId(id);
        return chatLieuGiayRepository.save(chatLieuGiay);
    }

    @Override
    public ChatLieuGiay delete(Integer id) {
        Optional<ChatLieuGiay> xx = chatLieuGiayRepository.findById(id);
        if (xx.isPresent()) {
            xx.get().setTrangThai(0);
            return chatLieuGiayRepository.save(xx.get());
        }
        return null;
    }
}
