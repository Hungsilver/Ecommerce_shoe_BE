package com.example.projectshop.service.impl;

import com.example.projectshop.domain.ChatLieuDeGiay;
import com.example.projectshop.domain.ChatLieuGiay;
import com.example.projectshop.dto.chatlieudegiay.ExcelCLDG;
import com.example.projectshop.dto.chatlieugiay.ExcelCLG;
import com.example.projectshop.repository.ChatLieuGiayRepository;
import com.example.projectshop.repository.ChiTietSanPhamRepository;
import com.example.projectshop.service.IChatLieuGiayService;
import com.example.projectshop.utils.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatLieuGiaySeviceImpl implements IChatLieuGiayService {
    @Autowired
    private ChatLieuGiayRepository chatLieuGiayRepository;

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepo;


    @Override
    public Page<ChatLieuGiay> getAll(Pageable pageable) {
        return chatLieuGiayRepository.findAll(pageable);
    }

    @Override
    public ChatLieuGiay findById(Integer id) {
        if (id != null) {
        return chatLieuGiayRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public ChatLieuGiay findByName(String name) {
        Optional<ChatLieuGiay> chatLieuGiay = chatLieuGiayRepository.findByName(name);
        if (chatLieuGiay.isPresent()) {
            return chatLieuGiay.get();
        }
        return null;
    }

    @Override
    public Page<ChatLieuGiay> findAllByName(String name, Pageable pageable) {
        return chatLieuGiayRepository.findAllByTen("%"+name+"%", pageable);
    }

    @Override
    public List<ExcelCLG> importExcel(List<ExcelCLG> excelCLGs) {
        // tạo list để chứa các đối tượng bị lỗi
        List<ExcelCLG> errorImports = new ArrayList<>();
        // sử dụng for để lặp các đối tượng được truyền vào từ file excel
        for (ExcelCLG x : excelCLGs) {
            // lấy ra đối tượng theo tên được truyền vào từ file excel
            ChatLieuGiay chatLieuGiay = this.findByName(x.getTenChatLieuGiay());

            // kiểm nếu tên truyền vào null || space add vào errorImports
            if (x.getTenChatLieuGiay() == null || x.getTenChatLieuGiay().isBlank()) {
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
            if (chatLieuGiay == null) {
                ChatLieuGiay chatLieuGiay1 = ChatLieuGiay.builder()
                        .id(null)
                        .ten(x.getTenChatLieuGiay())
                        .trangThai(utils.getNumberByNameStatus(x.getTrangThai()))
                        .build();
                System.out.println("case3");
                chatLieuGiayRepository.save(chatLieuGiay1);
            } else {// nếu đã có thì cập nhật lại thông tin
                ChatLieuGiay chatLieuGiay2 = ChatLieuGiay.builder()
                        .id(chatLieuGiay.getId())
                        .ten(x.getTenChatLieuGiay())
                        .trangThai(utils.getNumberByNameStatus(x.getTrangThai()))
                        .build();
                System.out.println("case3");
                chatLieuGiayRepository.save(chatLieuGiay2);
            }
        }
        return errorImports;
    }

    @Override
    public List<ExcelCLG> exportExcel() {
        List<ExcelCLG> excelCLGS = new ArrayList<>();
        Integer index = 1;
        for (ChatLieuGiay x : chatLieuGiayRepository.findAll()) {// convert từ ChatLieuDeGiay sang ExcelCLDG
            ExcelCLG excelCLG = ExcelCLG.builder()
                    .stt(index++)
                    .tenChatLieuGiay(x.getTen())
                    .trangThai(utils.trangThaiSanPham(x.getTrangThai()))
                    .build();
            excelCLGS.add(excelCLG);
        }
        return excelCLGS;
    }

    @Override
    public ChatLieuGiay create(ChatLieuGiay chatLieuGiay) {
        return chatLieuGiayRepository.save(chatLieuGiay);
    }

    @Override
    public ChatLieuGiay update(ChatLieuGiay chatLieuGiay, Integer id) {
        chatLieuGiay.setId(id);
        return chatLieuGiayRepository.save(chatLieuGiay);
    }

    @Override
    public ChatLieuGiay delete(Integer id) {
        Optional<ChatLieuGiay> xx = chatLieuGiayRepository.findById(id);
        if (xx.isPresent()) {
            xx.get().setTrangThai(0);
            return chatLieuGiayRepository.save(xx.get());
        }
        return null;
    }
}
