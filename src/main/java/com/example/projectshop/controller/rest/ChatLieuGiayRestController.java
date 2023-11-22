package com.example.projectshop.controller.rest;

import com.example.projectshop.domain.ChatLieuDeGiay;
import com.example.projectshop.domain.ChatLieuGiay;
import com.example.projectshop.domain.Xuatxu;
import com.example.projectshop.dto.chatlieugiay.ChatLieuGiayRequest;
import com.example.projectshop.dto.xuatxu.XuatXuRequest;
import com.example.projectshop.repository.ChatLieuGiayRepository;
import com.example.projectshop.service.IChatLieuGiayService;
import com.example.projectshop.service.ObjectMapperUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/shoe-material")
public class ChatLieuGiayRestController {

    @Autowired
    private IChatLieuGiayService chatLieuGiayService;

    @Autowired
    ChatLieuGiayRepository chatLieuGiayRepo;

    private String p_chu = "\\d+";

    @GetMapping()//localhost:8080/api/shoe-materiall
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") String pageSize,
            @RequestParam(value = "sortField", required = false, defaultValue = "id") String sortField,
            @RequestParam(value = "isSortDesc", required = false, defaultValue = "false") Boolean isSortDesc,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        if (!page.matches(p_chu)|| !pageSize.matches(p_chu)){
            return ResponseEntity.ok("*page || pageSize phải là số");
        }
        Sort sort = Sort.by(isSortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(Integer.valueOf(page) > 0 ? Integer.valueOf(page) - 1 : Integer.valueOf(page), Integer.valueOf(pageSize), sort);
        Page<ChatLieuGiay>  chatLieuGiays;
        if (keyword != null && !keyword.isEmpty()) {
            chatLieuGiays = chatLieuGiayService.findAllByName(keyword, pageable);
        } else {
            chatLieuGiays = chatLieuGiayService.getAll(pageable);
        }
        return ResponseEntity.ok(chatLieuGiays);
    }
    @GetMapping("{id}")//localhost:8080/api/shoe-materiall/1
    public ResponseEntity<?> findById(@PathVariable String id) {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id chất liệu giày phải là số");
        }
        return ResponseEntity.ok(chatLieuGiayService.findById(Integer.valueOf(id)));
    }

    @PostMapping//localhost:8080/api/shoe-materiall
    public ResponseEntity<?> create(@RequestBody ChatLieuGiayRequest request) {
        ChatLieuGiay xx = ObjectMapperUtils.map(request, ChatLieuGiay.class);
        return ResponseEntity.ok(chatLieuGiayService.create(xx));
    }

    @PutMapping("{id}")//localhost:8080/api/shoe-materiall
    public ResponseEntity<?> update(@RequestBody ChatLieuGiayRequest request,
                                    @PathVariable("id") String id) {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id chất liệu giày phải là số");
        }
        ChatLieuGiay xx = ObjectMapperUtils.map(request, ChatLieuGiay.class);
        return ResponseEntity.ok(chatLieuGiayService.update(xx, Integer.valueOf(id)));
    }

    @DeleteMapping("{id}")//localhost:8080/api/shoe-materiall/1
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id chất liệu giày phải là số");
        }
        return ResponseEntity.ok(chatLieuGiayService.delete(Integer.valueOf(id)));
    }


}
