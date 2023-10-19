package com.example.projectshop.controller.rest;

import com.example.projectshop.dto.xuatxu.XuatXuRequest;
import com.example.projectshop.dto.xuatxu.XuatXuResponse;
import com.example.projectshop.service.IXuatXuService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/api/xuatxu")
public class XuatXuRestController {

    @Autowired
    private IXuatXuService service;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/get-all")
    public ResponseEntity<?> findAll() {
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        return ResponseEntity.ok(service.findAllXuatXu(page, limit));
    }


    @PostMapping("/create")
    public ResponseEntity<XuatXuResponse> create(@RequestBody XuatXuRequest xuatXuRequest) {
        XuatXuResponse response = service.create(xuatXuRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<XuatXuResponse> update(@RequestBody XuatXuRequest xuatXuRequest) {

        Integer id = xuatXuRequest.getId();
        XuatXuResponse response = service.update(xuatXuRequest, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<?> delete(@PathVariable("id") Integer id){
        service.delete(id);
        return ResponseEntity.ok("xoa thanh cong");
    }



}
