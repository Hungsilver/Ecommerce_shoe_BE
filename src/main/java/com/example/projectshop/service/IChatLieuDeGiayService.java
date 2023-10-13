package com.example.projectshop.service;


import com.example.projectshop.domain.ChatLieuDeGiay;
import com.example.projectshop.dto.chatlieudegiay.ChatLieuDeGiayRequest;
import com.example.projectshop.dto.chatlieudegiay.ChatLieuDeGiayResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IChatLieuDeGiayService {

    List<ChatLieuDeGiayResponse> findAll();
    Page<ChatLieuDeGiayResponse> getAll(String pageParam,String limitParam);

    ChatLieuDeGiayResponse findById(Integer id);

    Page<ChatLieuDeGiay> timKiem(String input, String page, String limit);

    ChatLieuDeGiayResponse create(ChatLieuDeGiayRequest chatLieuDeGiayRequest);

    ChatLieuDeGiayResponse update(ChatLieuDeGiayRequest chatLieuDeGiayRequest, Integer id);

    void delete(Integer id);
}
