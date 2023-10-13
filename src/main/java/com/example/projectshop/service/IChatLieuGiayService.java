package com.example.projectshop.service;

import com.example.projectshop.dto.chatLieuGiay.ChatLieuGiayRequest;
import com.example.projectshop.dto.chatLieuGiay.ChatLieuGiayResponse;
import org.springframework.data.domain.Page;

import java.util.List;


public interface IChatLieuGiayService {
    List<ChatLieuGiayResponse> findAll();

    Page<ChatLieuGiayResponse> getAll(String pageParam, String limitParam);

    ChatLieuGiayResponse findById(Integer id);

    ChatLieuGiayResponse create(ChatLieuGiayRequest chatLieuGiayRequest);

    ChatLieuGiayResponse update(ChatLieuGiayRequest chatLieuGiayRequest, Integer id);

    void delete(Integer id);
}
