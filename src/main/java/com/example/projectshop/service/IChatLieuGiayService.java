package com.example.projectshop.service;

import com.example.projectshop.domain.ChatLieuGiay;
import com.example.projectshop.domain.KichCo;
import com.example.projectshop.dto.chatlieugiay.ChatLieuGiayRequest;
import com.example.projectshop.dto.chatlieugiay.ChatLieuGiayResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface IChatLieuGiayService {
    Page<ChatLieuGiay> getAll(Pageable pageable);


    ChatLieuGiay findById(Integer id);

    Page<ChatLieuGiay> findAllByName(String ten,Pageable pageable);

    ChatLieuGiay create(ChatLieuGiay chatLieuGiay);

    ChatLieuGiay update(ChatLieuGiay  chatLieuGiay , Integer id);

    ChatLieuGiay delete(Integer id);
}
