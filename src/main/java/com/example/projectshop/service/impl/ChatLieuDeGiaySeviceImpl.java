package com.example.projectshop.service.impl;

import com.example.projectshop.domain.ChatLieuDeGiay;
import com.example.projectshop.domain.Xuatxu;
import com.example.projectshop.dto.chatlieudegiay.ChatLieuDeGiayRequest;
import com.example.projectshop.dto.chatlieudegiay.ChatLieuDeGiayResponse;
import com.example.projectshop.dto.chatlieugiay.ChatLieuGiayRequest;
import com.example.projectshop.repository.ChatLieuDeGiayRepository;
import com.example.projectshop.repository.ChiTietSanPhamRepository;
import com.example.projectshop.service.IChatLieuDeGiayService;
import com.example.projectshop.service.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatLieuDeGiaySeviceImpl implements IChatLieuDeGiayService {
    @Autowired
    private ChatLieuDeGiayRepository chatLieuDeGiayRepository;

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepo;


    @Override
    public Page<ChatLieuDeGiay> getAll(Pageable pageable) {
        return chatLieuDeGiayRepository.findAll(pageable);
    }

    @Override
    public ChatLieuDeGiay findById(Integer id) {
        if (id != null) {
        return chatLieuDeGiayRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public Page<ChatLieuDeGiay> findAllByName(String ten, Pageable pageable) {
        return chatLieuDeGiayRepository.findAllByTen("%"+ten+"%",pageable);
    }

    @Override
    public ChatLieuDeGiay create(ChatLieuDeGiay chatLieuDeGiay) {
        chatLieuDeGiay.setId(null);
        return chatLieuDeGiayRepository.save(chatLieuDeGiay);
    }

    @Override
    public ChatLieuDeGiay update(ChatLieuDeGiay chatLieuDeGiay, Integer id) {
        chatLieuDeGiay.setId(id);
        return chatLieuDeGiayRepository.save(chatLieuDeGiay);
    }

    @Override
    public ChatLieuDeGiay delete(Integer id) {
        Optional<ChatLieuDeGiay> xx = chatLieuDeGiayRepository.findById(id);
        if (xx.isPresent()) {
            xx.get().setTrangThai(0);
            return chatLieuDeGiayRepository.save(xx.get());
        }
        return null;
    }
}
