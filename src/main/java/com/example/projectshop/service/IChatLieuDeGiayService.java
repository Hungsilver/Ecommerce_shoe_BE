package com.example.projectshop.service;


import com.example.projectshop.domain.ChatLieuDeGiay;
import com.example.projectshop.dto.chatlieudegiay.ExcelCLDG;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IChatLieuDeGiayService {
    Page<ChatLieuDeGiay> getAll(Pageable pageable);

    ChatLieuDeGiay findById(Integer id);

    ChatLieuDeGiay findByName(String name);

    Page<ChatLieuDeGiay> findAllByName(String ten,Pageable pageable);

    List<ExcelCLDG> importExcel(List<ExcelCLDG> excelCLDGS);

    List<ExcelCLDG> exportExcel();

    ChatLieuDeGiay create(ChatLieuDeGiay chatLieuDeGiay);

    ChatLieuDeGiay update(ChatLieuDeGiay  chatLieuDeGiay , Integer id);

    ChatLieuDeGiay delete(Integer id);

}
