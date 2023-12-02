package com.example.projectshop.controller.rest;

import com.example.projectshop.domain.ChatLieuDeGiay;
import com.example.projectshop.dto.chatlieudegiay.ChatLieuDeGiayRequest;
import com.example.projectshop.dto.chatlieudegiay.ExcelCLDG;
import com.example.projectshop.service.IChatLieuDeGiayService;
import com.example.projectshop.service.ObjectMapperUtils;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/shoe-sole-material")
public class ChatLieuDeGiayRestController {

    @Autowired
    private IChatLieuDeGiayService chatLieuDeGiayService;

    private String p_chu = "\\d+";

    @GetMapping()// localhost:8080/api/shoe-sole-material
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
        Page<ChatLieuDeGiay> chatLieuDeGiays;
        if (keyword != null && !keyword.isEmpty()) {
            chatLieuDeGiays = chatLieuDeGiayService.findAllByName(keyword, pageable);
        } else {
            chatLieuDeGiays = chatLieuDeGiayService.getAll(pageable);
        }
        return ResponseEntity.ok(chatLieuDeGiays);
    }

    @GetMapping("{id}")//localhost:8080/api/shoe-sole-material/1
    public ResponseEntity<?> findById(@PathVariable String id) {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id chât liệu đế giày phải là số");
        }
        return ResponseEntity.ok(chatLieuDeGiayService.findById(Integer.valueOf(id)));
    }

    @GetMapping("/excel/export")//localhost:8080/api/shoe-sole-material/excel/export
    public  ResponseEntity<?> exportExcel() {
        return ResponseEntity.ok(chatLieuDeGiayService.exportExcel());
    }

    @PostMapping("/excel/import")//localhost:8080/api/shoe-sole-material/excel/import
    public  ResponseEntity<?> exportExcel(@RequestBody List<ExcelCLDG> excelCLDGS){
        return ResponseEntity.ok(chatLieuDeGiayService.importExcel(excelCLDGS));
    }

    @PostMapping//localhost:8080/api/shoe-sole-material
    public ResponseEntity<?> create(@RequestBody ChatLieuDeGiayRequest request) {
        ChatLieuDeGiay xx = ObjectMapperUtils.map(request, ChatLieuDeGiay.class);
        return ResponseEntity.ok(chatLieuDeGiayService.create(xx));
    }

    @PutMapping("{id}")//localhost:8080/api/shoe-sole-material/1
    public ResponseEntity<?> update(@RequestBody ChatLieuDeGiayRequest request, @PathVariable("id") String id) {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id chât liệu đế giày phải là số");
        }
        ChatLieuDeGiay xx = ObjectMapperUtils.map(request, ChatLieuDeGiay.class);
        return ResponseEntity.ok(chatLieuDeGiayService.update(xx, Integer.valueOf(id)));
    }

    @DeleteMapping("{id}")//localhost:8080/api/shoe-sole-material/1
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id chât liệu đế giày phải là số");
        }
        return ResponseEntity.ok(chatLieuDeGiayService.delete(Integer.valueOf(id)));
    }

}
