package com.example.projectshop.service.impl;

import com.example.projectshop.domain.Chatlieudegiay;
import com.example.projectshop.dto.chatLieuDeGiay.ChatLieuDeGiayRequest;
import com.example.projectshop.dto.chatLieuDeGiay.ChatLieuDeGiayResponse;
import com.example.projectshop.repository.ChatLieuDeGiayRepository;
import com.example.projectshop.service.IChatLieuDeGiayService;
import com.example.projectshop.service.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatLieuDeGiaySeviceImpl implements IChatLieuDeGiayService {
    @Autowired
    private ChatLieuDeGiayRepository chatLieuDeGiayRepository;

    @Override
    public List<ChatLieuDeGiayResponse> getAll() {
        List<ChatLieuDeGiayResponse> list = ObjectMapperUtils.mapAll(chatLieuDeGiayRepository.findAll(), ChatLieuDeGiayResponse.class);
        return list;
    }

    @Override
    public ChatLieuDeGiayResponse findById(Integer id) {
        return ObjectMapperUtils.map(chatLieuDeGiayRepository.findById(id).get(), ChatLieuDeGiayResponse.class);
    }

    @Override
    public ChatLieuDeGiayResponse create(ChatLieuDeGiayRequest chatLieuDeGiayRequest) {
        Chatlieudegiay entity = ObjectMapperUtils.map(chatLieuDeGiayRequest, Chatlieudegiay.class);
        entity.setTen(chatLieuDeGiayRequest.getTen());
        entity = chatLieuDeGiayRepository.save(entity);
        ChatLieuDeGiayResponse response = ObjectMapperUtils.map(entity, ChatLieuDeGiayResponse.class);
        return response;
    }

    @Override
    public ChatLieuDeGiayResponse update(ChatLieuDeGiayRequest chatLieuDeGiayRequest, Integer id) {
        Chatlieudegiay eDb = chatLieuDeGiayRepository.findById(id).get();
        Chatlieudegiay entity = ObjectMapperUtils.map(chatLieuDeGiayRequest, Chatlieudegiay.class);
        entity.setId(eDb.getId());
        entity.setTen(chatLieuDeGiayRequest.getTen());
        entity = chatLieuDeGiayRepository.save(entity);
        return ObjectMapperUtils.map(chatLieuDeGiayRepository.save(entity), ChatLieuDeGiayResponse.class);
    }

    @Override
    public void delete(Integer id) {
        chatLieuDeGiayRepository.deleteById(id);
    }
}
