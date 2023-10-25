package com.example.projectshop.controller.rest;

import com.example.projectshop.domain.ChatLieuDeGiay;
import com.example.projectshop.domain.Xuatxu;
import com.example.projectshop.dto.chatlieudegiay.ChatLieuDeGiayRequest;
import com.example.projectshop.dto.xuatxu.XuatXuRequest;
import com.example.projectshop.service.IChatLieuDeGiayService;
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
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/shoe-sole-material")
public class ChatLieuDeGiayRestController {

    @Autowired
    private IChatLieuDeGiayService chatLieuDeGiayService;

    @Autowired
    private HttpServletRequest request;

    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sortField", required = false, defaultValue = "id") String sortField,
            @RequestParam(value = "isSortDesc", required = false, defaultValue = "false") Boolean isSortDesc,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        Sort sort = Sort.by(isSortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, pageSize, sort);
        Page<ChatLieuDeGiay> chatLieuDeGiays;
        if (keyword != null && !keyword.isEmpty()) {
            chatLieuDeGiays = chatLieuDeGiayService.findAllByName(keyword, pageable);
        } else {
            chatLieuDeGiays = chatLieuDeGiayService.getAll(pageable);
        }
        return ResponseEntity.ok(chatLieuDeGiays);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(chatLieuDeGiayService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ChatLieuDeGiayRequest request) {
        ChatLieuDeGiay xx = ObjectMapperUtils.map(request, ChatLieuDeGiay.class);
        return ResponseEntity.ok(chatLieuDeGiayService.create(xx));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody ChatLieuDeGiayRequest request, @PathVariable("id") Integer id) {
        ChatLieuDeGiay xx = ObjectMapperUtils.map(request, ChatLieuDeGiay.class);
        return ResponseEntity.ok(chatLieuDeGiayService.update(xx, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(chatLieuDeGiayService.delete(id));
    }

}
