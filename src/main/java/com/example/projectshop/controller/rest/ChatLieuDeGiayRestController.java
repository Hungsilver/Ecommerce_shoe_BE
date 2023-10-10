package com.example.projectshop.controller.rest;

import com.example.projectshop.dto.chatLieuDeGiay.ChatLieuDeGiayRequest;
import com.example.projectshop.service.IChatLieuDeGiayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ChatLieuDeGiayRestController {

    @Autowired
    private IChatLieuDeGiayService service;

    @GetMapping(value =  "/findAll")
    public ResponseEntity<?> getALl(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping(value =  "/findById/{id}")
    public ResponseEntity<?> getALl(@PathVariable("id")Integer id){
        return ResponseEntity.ok(service.findById(id));
    }


    @PostMapping(value =  "/create",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> create(@RequestBody ChatLieuDeGiayRequest request){
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping(value =  "/update/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> update(@RequestBody ChatLieuDeGiayRequest request, @PathVariable("id")Integer id){
        return ResponseEntity.ok(service.update(request,id));
    }

    @DeleteMapping(value =  "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
