package com.example.projectshop.controller.rest;


import com.example.projectshop.domain.GioHangChiTiet;
import com.example.projectshop.repository.GioHangChiTietRepository;
import com.example.projectshop.service.IGioHangCTService;
import com.example.projectshop.service.IGioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/api/cart-detail")
public class OnlineShopRest {

    @Autowired
    private IGioHangCTService iGioHangCTService;

//    @Autowired
//    private IGioHangService gioHangService;
    //test
    @Autowired
    private GioHangChiTietRepository thu;

    @GetMapping
    public ResponseEntity<?> loadGioHangChiTiet(@PageableDefault(size = 1) Pageable pageable) {

        return ResponseEntity.ok(iGioHangCTService.getall(pageable));
    }

    @GetMapping("/OfCart/{id}")
    public ResponseEntity<?> getGHCTByGiohangID(@PathVariable("id") Integer id) {
      List<GioHangChiTiet> dsgh =  iGioHangCTService.GetGioHangCTByIdGioHang(id);
        return ResponseEntity.ok(dsgh);
    }



}
