package com.example.projectshop.controller.rest;

import com.example.projectshop.dto.mausac.MauSacRequest;
import com.example.projectshop.service.IMauSacService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/api/mausac")
public class MauSacRestController {
    @Autowired
    private IMauSacService service;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/get-all")
    public ResponseEntity<?> findAll() {
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        return ResponseEntity.ok(service.findAllMauSac(page,limit));
    }

    @GetMapping(value =  "/findAll")
    public ResponseEntity<?> getALl(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping(value =  "/findById/{id}")
    public ResponseEntity<?> getALl(@PathVariable("id")Integer id){
        return ResponseEntity.ok(service.findById(id));
    }


    @PostMapping(value =  "/create",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> create(@RequestBody MauSacRequest request){
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping(value =  "/update/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> update(@RequestBody MauSacRequest request, @PathVariable("id")Integer id){
        return ResponseEntity.ok(service.update(request,id));
    }

    @DeleteMapping(value =  "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
