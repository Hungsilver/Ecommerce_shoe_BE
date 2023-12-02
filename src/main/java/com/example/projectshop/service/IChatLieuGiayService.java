package com.example.projectshop.service;

import com.example.projectshop.domain.ChatLieuGiay;
import com.example.projectshop.dto.chatlieudegiay.ExcelCLDG;
import com.example.projectshop.dto.chatlieugiay.ExcelCLG;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IChatLieuGiayService {
    Page<ChatLieuGiay> getAll(Pageable pageable);

    ChatLieuGiay findById(Integer id);

    ChatLieuGiay findByName(String name);

    Page<ChatLieuGiay> findAllByName(String ten,Pageable pageable);

    List<ExcelCLG> importExcel(List<ExcelCLG> excelCLGs);

    List<ExcelCLG> exportExcel();

    ChatLieuGiay create(ChatLieuGiay chatLieuGiay);

    ChatLieuGiay update(ChatLieuGiay  chatLieuGiay , Integer id);

    ChatLieuGiay delete(Integer id);
}
