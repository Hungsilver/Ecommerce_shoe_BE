package com.example.projectshop.service.impl;

import com.example.projectshop.domain.ChatLieuDeGiay;
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

@Service
public class ChatLieuDeGiaySeviceImpl implements IChatLieuDeGiayService {
    @Autowired
    private ChatLieuDeGiayRepository chatLieuDeGiayRepository;

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepo;

    @Override
    public List<ChatLieuDeGiayResponse> findAll() {
        return ObjectMapperUtils.mapAll(chatLieuDeGiayRepository.findAll(), ChatLieuDeGiayResponse.class);
    }

    @Override
    public Page<ChatLieuDeGiayResponse> getAll(String pageParam, String limitParam) {
        Integer page = pageParam == null ? 0 : Integer.valueOf(pageParam);
        Integer limit = limitParam == null ? 2 : Integer.valueOf(limitParam);
        Pageable pageable = PageRequest.of(page, limit);
        Page<ChatLieuDeGiayResponse> list = ObjectMapperUtils.mapEntityPageIntoDtoPage(chatLieuDeGiayRepository.findAll(pageable), ChatLieuDeGiayResponse.class);
        return list;
    }

    @Override
    public ChatLieuDeGiay findById(Integer id) {
        return ObjectMapperUtils.map(chatLieuDeGiayRepository.findById(id).get(), ChatLieuDeGiay.class);
    }

    @Override
    public Page<ChatLieuDeGiay> timKiem(String input, String pageParam, String limitParam) {
        Integer page = pageParam == null ? 0 : Integer.valueOf(pageParam);
        Integer limit = limitParam == null ? 5 : Integer.valueOf(limitParam);
        Pageable pageable = PageRequest.of(page, limit);
        return chatLieuDeGiayRepository.timKiem(input, pageable);
//        return ObjectMapperUtils.mapEntityPageIntoDtoPage(chatLieuDeGiayRepository.timKiem(input,pageable), ChatLieuDeGiayResponse.class);
    }

    @Override
    public ChatLieuDeGiayResponse create(ChatLieuDeGiayRequest chatLieuDeGiayRequest) {
        ChatLieuDeGiay entity = ObjectMapperUtils.map(chatLieuDeGiayRequest, ChatLieuDeGiay.class);
        entity.setTen(chatLieuDeGiayRequest.getTen());
        entity = chatLieuDeGiayRepository.save(entity);
        ChatLieuDeGiayResponse response = ObjectMapperUtils.map(entity, ChatLieuDeGiayResponse.class);
        return response;
    }

    @Override
    public ChatLieuDeGiayResponse update(ChatLieuDeGiayRequest chatLieuDeGiayRequest, Integer id) {
        ChatLieuDeGiay eDb = chatLieuDeGiayRepository.findById(id).get();
        ChatLieuDeGiay entity = ObjectMapperUtils.map(chatLieuDeGiayRequest, ChatLieuDeGiay.class);
        entity.setId(eDb.getId());
        entity.setTen(chatLieuDeGiayRequest.getTen());
        entity = chatLieuDeGiayRepository.save(entity);
        return ObjectMapperUtils.map(chatLieuDeGiayRepository.save(entity), ChatLieuDeGiayResponse.class);
    }

    @Override
    public void delete(Integer id) {
        ChatLieuDeGiayRequest chatLieuDeGiay = new ChatLieuDeGiayRequest();
        chatLieuDeGiay.setId(id);
        chatLieuDeGiay.setTen(this.findById(id).getTen());
        chatLieuDeGiay.setTrangThai(2);
        this.update(chatLieuDeGiay,id);
    }
}
