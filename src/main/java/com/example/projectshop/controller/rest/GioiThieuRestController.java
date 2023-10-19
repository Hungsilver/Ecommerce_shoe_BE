package com.example.projectshop.controller.rest;


import com.example.projectshop.dto.gioithieu.GioiThieuReponse;
import com.example.projectshop.dto.gioithieu.GioiThieuRequest;
import com.example.projectshop.dto.thuonghieu.ThuongHieuRequest;
import com.example.projectshop.dto.thuonghieu.ThuongHieuResponse;
import com.example.projectshop.service.impl.GioiThieuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/gioi-thieu")
public class GioiThieuRestController {

    @Autowired
    private GioiThieuServiceImpl gioiThieuService;

    @GetMapping
    public ResponseEntity<List<GioiThieuReponse>> load() {
        List<GioiThieuReponse> reponses = gioiThieuService.getall();
        return ResponseEntity.ok(reponses);
    }

    @PostMapping("/create")
    public ResponseEntity<GioiThieuReponse> create(@RequestBody GioiThieuRequest gioiThieuRequest) {
        GioiThieuReponse reponse = gioiThieuService.create(gioiThieuRequest);
        return ResponseEntity.ok(reponse);
    }
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        gioiThieuService.delete(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/update")
    public ResponseEntity<GioiThieuReponse> update(@RequestBody GioiThieuRequest gioiThieuRequest){
        GioiThieuReponse response = gioiThieuService.update(gioiThieuRequest);
        return ResponseEntity.ok(response);
    }


}
