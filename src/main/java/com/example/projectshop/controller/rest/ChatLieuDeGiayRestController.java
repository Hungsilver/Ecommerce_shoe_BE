package com.example.projectshop.controller.rest;

import com.example.projectshop.dto.chatLieuDeGiay.ChatLieuDeGiayRequest;
import com.example.projectshop.service.IChatLieuDeGiayService;
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
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/chat-lieu-de-giay")
public class ChatLieuDeGiayRestController {

    @Autowired
    private IChatLieuDeGiayService service;

    @Autowired
    private HttpServletRequest request;

    @GetMapping(value = "/get-all")
    public ResponseEntity<?> getALl() {
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        return ResponseEntity.ok(service.getAll(page,limit));
    }

    @GetMapping(value = "/find-all")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(value = "/get-one/{id}")
    public ResponseEntity<?> getALl(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping(value = "/tim-kiem")
    public ResponseEntity<?> timKiem() {
        String timKiem = request.getParameter("timKiem");
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        return ResponseEntity.ok(service.timKiem(timKiem,page,limit));
    }


    @PostMapping(value = "/create", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> create(
            @RequestBody @Valid ChatLieuDeGiayRequest request,
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
            @RequestBody @Valid ChatLieuDeGiayRequest request,
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
