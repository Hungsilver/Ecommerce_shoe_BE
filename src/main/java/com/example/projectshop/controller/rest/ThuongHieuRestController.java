package com.example.projectshop.controller.rest;

import com.example.projectshop.dto.thuonghieu.ThuongHieuRequest;
import com.example.projectshop.dto.thuonghieu.ThuongHieuResponse;
import com.example.projectshop.service.IThuongHieuService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/thuong-hieu")
public class ThuongHieuRestController {

    @Autowired
    private IThuongHieuService service;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/get-all")
    public ResponseEntity<?> findAll() {
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        return ResponseEntity.ok(service.findAllThuongHieu(page, limit));
    }

    @PostMapping("/create")
    public ResponseEntity<ThuongHieuResponse> create(ThuongHieuRequest thuongHieuRequest) {
        ThuongHieuResponse response = service.create(thuongHieuRequest);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<ThuongHieuResponse> update(ThuongHieuRequest thuongHieuRequest){
        ThuongHieuResponse response = service.update(thuongHieuRequest);
        return ResponseEntity.ok(response);
    }

}
