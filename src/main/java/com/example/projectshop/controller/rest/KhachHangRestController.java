package com.example.projectshop.controller.rest;

import com.example.projectshop.dto.khachhang.KhachHangRequest;
import com.example.projectshop.service.IKhachHangService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/khach-hang")
public class KhachHangRestController {

    @Autowired
    private IKhachHangService service;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/find-all")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(){
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        return ResponseEntity.ok(service.getAll(page,limit));
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody KhachHangRequest khachHangRequest){
        return ResponseEntity.ok(service.create(khachHangRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable("id")Integer id,
            @RequestBody KhachHangRequest khachHangRequest
            ){
        return ResponseEntity.ok(service.update(id,khachHangRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")Integer id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/tim-kiem")
    public ResponseEntity<?> timKiem(){
        String timKiem = request.getParameter("timKiem");
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        return ResponseEntity.ok(service.timKiem(timKiem,page,limit));
    }

}
