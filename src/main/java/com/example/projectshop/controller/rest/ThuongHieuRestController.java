package com.example.projectshop.controller.rest;

import com.example.projectshop.service.IThuongHieuService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/thuonghieu")
public class ThuongHieuRestController {

    @Autowired
    private IThuongHieuService service;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/get-all")
    public ResponseEntity<?> findAll() {
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        return ResponseEntity.ok(service.findAllThuongHieu(page,limit));
    }

}
