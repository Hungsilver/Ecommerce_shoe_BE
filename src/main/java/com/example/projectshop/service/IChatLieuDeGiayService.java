package com.example.projectshop.service;

import com.example.projectshop.dto.chatLieuDeGiay.ChatLieuDeGiayRequest;
import com.example.projectshop.dto.chatLieuDeGiay.ChatLieuDeGiayResponse;

import java.util.List;

public interface IChatLieuDeGiayService {

    List<ChatLieuDeGiayResponse> getAll();

    ChatLieuDeGiayResponse findById(Integer id);

    ChatLieuDeGiayResponse create(ChatLieuDeGiayRequest chatLieuDeGiayRequest);

    ChatLieuDeGiayResponse update(ChatLieuDeGiayRequest chatLieuDeGiayRequest, Integer id);

    void delete(Integer id);
}
