package com.example.projectshop.controller.rest;

import com.example.projectshop.repository.ChatLieuDeGiayRepository;
import com.example.projectshop.repository.SanPhamRepository;
import com.example.projectshop.service.ISanPhamService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/san-pham")
public class SanPhamRestController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ISanPhamService service;

    @Autowired
    private ChatLieuDeGiayRepository chatLieuDeGiayRepository;

    @GetMapping("/find-all")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(){
        System.out.println(chatLieuDeGiayRepository.getTop1().getId());
        String priceMin = request.getParameter("pricemin");
        String priceMax = request.getParameter("pricemax");
        String thuongHieu = request.getParameter("thuonghieu");
        String xuatXu = request.getParameter("xuatxu");
        String mauSac = request.getParameter("mausac");
        String chatLieuGiay = request.getParameter("chatlieugiay");
        String chatLieuDeGiay = request.getParameter("chatlieudegiay");
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        return ResponseEntity.ok(service.getAllByParam(priceMin,priceMax,thuongHieu,xuatXu,mauSac,chatLieuGiay,chatLieuDeGiay,page,limit));
    }

    @GetMapping("/get-one/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id")Integer id){
        return ResponseEntity.ok(service.getOne(id));
    }
}
