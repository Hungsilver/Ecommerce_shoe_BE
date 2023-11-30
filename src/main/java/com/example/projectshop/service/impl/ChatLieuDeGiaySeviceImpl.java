package com.example.projectshop.service.impl;

import com.example.projectshop.domain.ChatLieuDeGiay;
import com.example.projectshop.dto.chatlieudegiay.ExcelCLDG;
import com.example.projectshop.repository.ChatLieuDeGiayRepository;
import com.example.projectshop.service.IChatLieuDeGiayService;
import com.example.projectshop.utils.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatLieuDeGiaySeviceImpl implements IChatLieuDeGiayService {
    @Autowired
    private ChatLieuDeGiayRepository chatLieuDeGiayRepository;


    @Override
    public Page<ChatLieuDeGiay> getAll(Pageable pageable) {
        return chatLieuDeGiayRepository.findAll(pageable);
    }

    @Override
    public ChatLieuDeGiay findById(Integer id) {
        if (id != null) {
            return chatLieuDeGiayRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public ChatLieuDeGiay findByName(String name) {
        Optional<ChatLieuDeGiay> chatLieuDeGiay = chatLieuDeGiayRepository.findByName(name);
        if (chatLieuDeGiay.isPresent()) {
            return chatLieuDeGiay.get();
        }
        return null;
    }

    @Override
    public Page<ChatLieuDeGiay> findAllByName(String ten, Pageable pageable) {
        return chatLieuDeGiayRepository.findAllByTen("%" + ten + "%", pageable);
    }

    @Override
    public List<ExcelCLDG> importExcel(List<ExcelCLDG> excelCLDGS) {
        // tạo list để chứa các đối tượng bị lỗi
        List<ExcelCLDG> errorImports = new ArrayList<>();
        // sử dụng for để lặp các đối tượng được truyền vào từ file excel
        for (ExcelCLDG x : excelCLDGS) {
            // lấy ra đối tượng theo tên được truyền vào từ file excel
            ChatLieuDeGiay chatLieuDeGiay = this.findByName(x.getTenChatLieuDeGiay());

            // kiểm nếu tên truyền vào null || space add vào errorImports
            if (x.getTenChatLieuDeGiay() == null || x.getTenChatLieuDeGiay().isBlank()) {
                errorImports.add(x);
                System.out.println("case 1");
                continue;
            }

            // kiểm nếu trạng thái là null add vào errorImports
            if (utils.getNumberByNameStatus(x.getTrangThai()) == null) {
                errorImports.add(x);
                System.out.println("case 2");
                continue;
            }

            // kiểm tra nếu chưa có trong db thì thêm mới
            if (chatLieuDeGiay == null) {
                ChatLieuDeGiay chatLieuDeGiay1 = ChatLieuDeGiay.builder()
                        .id(null)
                        .ten(x.getTenChatLieuDeGiay())
                        .trangThai(utils.getNumberByNameStatus(x.getTrangThai()))
                        .build();
                System.out.println("case3");
                chatLieuDeGiayRepository.save(chatLieuDeGiay1);
            } else {// nếu đã có thì cập nhật lại thông tin
                ChatLieuDeGiay chatLieuDeGiay2 = ChatLieuDeGiay.builder()
                        .id(chatLieuDeGiay.getId())
                        .ten(x.getTenChatLieuDeGiay())
                        .trangThai(utils.getNumberByNameStatus(x.getTrangThai()))
                        .build();
                System.out.println("case3");
                chatLieuDeGiayRepository.save(chatLieuDeGiay2);
            }
        }
        return errorImports;
    }

    @Override
    public List<ExcelCLDG> exportExcel() {
        List<ExcelCLDG> excelCLDGS = new ArrayList<>();
        Integer index = 1;
        for (ChatLieuDeGiay x : chatLieuDeGiayRepository.findAll()) {// convert từ ChatLieuDeGiay sang ExcelCLDG
            ExcelCLDG excelCLDG = ExcelCLDG.builder()
                    .stt(index++)
                    .tenChatLieuDeGiay(x.getTen())
                    .trangThai(utils.trangThaiSanPham(x.getTrangThai()))
                    .build();
            excelCLDGS.add(excelCLDG);
        }
        return excelCLDGS;
    }

    @Override
    public ChatLieuDeGiay create(ChatLieuDeGiay chatLieuDeGiay) {
        chatLieuDeGiay.setId(null);
        return chatLieuDeGiayRepository.save(chatLieuDeGiay);
    }

    @Override
    public ChatLieuDeGiay update(ChatLieuDeGiay chatLieuDeGiay, Integer id) {
        chatLieuDeGiay.setId(id);
        return chatLieuDeGiayRepository.save(chatLieuDeGiay);
    }

    @Override
    public ChatLieuDeGiay delete(Integer id) {
        Optional<ChatLieuDeGiay> xx = chatLieuDeGiayRepository.findById(id);
        if (xx.isPresent()) {
            xx.get().setTrangThai(0);
            return chatLieuDeGiayRepository.save(xx.get());
        }
        return null;
    }
}
