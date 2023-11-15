package com.example.projectshop.service;


import com.example.projectshop.domain.ChatLieuDeGiay;
import com.example.projectshop.domain.ChatLieuGiay;
import com.example.projectshop.dto.chatlieudegiay.ChatLieuDeGiayRequest;
import com.example.projectshop.dto.chatlieudegiay.ChatLieuDeGiayResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IChatLieuDeGiayService {
    Page<ChatLieuDeGiay> getAll(Pageable pageable);


    ChatLieuDeGiay findById(Integer id);

    Page<ChatLieuDeGiay> findAllByName(String ten,Pageable pageable);

    ChatLieuDeGiay create(ChatLieuDeGiay chatLieuDeGiay);

    ChatLieuDeGiay update(ChatLieuDeGiay  chatLieuDeGiay , Integer id);

    ChatLieuDeGiay delete(Integer id);

}
