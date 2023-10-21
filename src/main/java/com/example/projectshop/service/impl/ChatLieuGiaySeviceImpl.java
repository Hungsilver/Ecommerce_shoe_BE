package com.example.projectshop.service.impl;

import com.example.projectshop.domain.ChatLieuGiay;
import com.example.projectshop.dto.chatlieugiay.ChatLieuGiayRequest;
import com.example.projectshop.dto.chatlieugiay.ChatLieuGiayResponse;
import com.example.projectshop.repository.ChatLieuGiayRepository;
import com.example.projectshop.service.IChatLieuGiayService;
import com.example.projectshop.service.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatLieuGiaySeviceImpl implements IChatLieuGiayService {
    @Autowired
    private ChatLieuGiayRepository chatLieuGiayRepository;


    @Override
    public List<ChatLieuGiayResponse> findAll() {
        List<ChatLieuGiayResponse> list = ObjectMapperUtils.mapAll(chatLieuGiayRepository.findAll(), ChatLieuGiayResponse.class);
        return list;
    }

    @Override
    public Page<ChatLieuGiayResponse> getAll(String pageParam, String limitParam) {
        Integer page = pageParam == null ? 0 : Integer.valueOf(pageParam);
        Integer limit = limitParam == null ? 5 : Integer.valueOf(limitParam);
        Pageable pageable = PageRequest.of(page, limit);
        Page<ChatLieuGiayResponse> list = ObjectMapperUtils.mapEntityPageIntoDtoPage(chatLieuGiayRepository.findAll(pageable), ChatLieuGiayResponse.class);
        return list;
    }

    @Override
    public ChatLieuGiayResponse findById(Integer id) {
        return ObjectMapperUtils.map(chatLieuGiayRepository.findById(id).get(), ChatLieuGiayResponse.class);
    }

    @Override
    public ChatLieuGiayResponse create(ChatLieuGiayRequest chatLieuDeGiayRequest) {
        ChatLieuGiay entity = ObjectMapperUtils.map(chatLieuDeGiayRequest, ChatLieuGiay.class);
        entity.setId(null);
        entity.setTen(chatLieuDeGiayRequest.getTen());
        ChatLieuGiay entityRs = chatLieuGiayRepository.save(entity);
        ChatLieuGiayResponse response = ObjectMapperUtils.map(entityRs, ChatLieuGiayResponse.class);
        return response;
    }

    @Override
    public ChatLieuGiayResponse update(ChatLieuGiayRequest chatLieuDeGiayRequest, Integer id) {
        ChatLieuGiay eDb = chatLieuGiayRepository.findById(id).get();
        ChatLieuGiay entity = ObjectMapperUtils.map(chatLieuDeGiayRequest, ChatLieuGiay.class);
        entity.setId(eDb.getId());
        entity.setTen(chatLieuDeGiayRequest.getTen());
        entity = chatLieuGiayRepository.save(entity);
        return ObjectMapperUtils.map(chatLieuGiayRepository.save(entity), ChatLieuGiayResponse.class);
    }

    @Override
    public void delete(Integer id) {
        chatLieuGiayRepository.deleteById(id);
    }
}
