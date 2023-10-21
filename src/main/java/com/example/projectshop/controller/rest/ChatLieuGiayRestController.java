package com.example.projectshop.controller.rest;

import com.example.projectshop.dto.chatlieugiay.ChatLieuGiayRequest;
import com.example.projectshop.repository.ChatLieuGiayRepository;
import com.example.projectshop.service.IChatLieuGiayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/chat-lieu-giay")
public class ChatLieuGiayRestController {

    @Autowired
    private IChatLieuGiayService service;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    ChatLieuGiayRepository chatLieuGiayRepo;

    @GetMapping(value = "/find-all")
    public ResponseEntity<?> findAll(@RequestParam(value = "id",required = false)String id) {
        String[] idArray = id.split("-");
        for (String ids: idArray){
            System.out.println(ids);
        }
//        List<String > list = new ArrayList<>();
//        list.add("abc");
//        list.add("adf");
//        System.out.println(list);

        return ResponseEntity.ok(chatLieuGiayRepo.test(1, idArray));

    }

    @GetMapping(value = "/get-all")
    public ResponseEntity<?> getALl() {
        String page = request.getParameter("page");
        String limt = request.getParameter("limit");
        return ResponseEntity.ok(service.getAll(page, limt));
    }

    @GetMapping(value = "/get-one/{id}")
    public ResponseEntity<?> getALl(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }


    @PostMapping(value = "/create", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> create(
            @RequestBody @Valid ChatLieuGiayRequest request,
            BindingResult result) {
//        if (result.hasErrors()) {
//            Map<String, String> mapError = new HashMap<>();
//            result.getAllErrors().stream().forEach(
//                    x -> mapError.put(((FieldError) x).getField(), x.getDefaultMessage())
//            );
//            return ResponseEntity.ok(mapError);
//        }
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping(value = "/update/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> update(
            @PathVariable("id") Integer id,
            @RequestBody @Valid ChatLieuGiayRequest request,
            BindingResult result) {
//        if (result.hasErrors()) {
//            Map<String, String> mapError = new HashMap<>();
//            result.getAllErrors().stream().forEach(
//                    x -> mapError.put(((FieldError) x).getField(), x.getDefaultMessage())
//            );
//            return ResponseEntity.ok(mapError);
//        }
        return ResponseEntity.ok(service.update(request, id));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
