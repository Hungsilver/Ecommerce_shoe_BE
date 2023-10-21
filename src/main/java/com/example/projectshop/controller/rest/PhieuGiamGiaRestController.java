package com.example.projectshop.controller.rest;

import com.example.projectshop.service.IPhieuGiamGiaService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/phieu-giam-gia")
public class PhieuGiamGiaRestController {

    @Autowired
    private IPhieuGiamGiaService phieuGiamGiaService;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/find-all")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(phieuGiamGiaService.findAll());
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(){
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        return ResponseEntity.ok(phieuGiamGiaService.getAll(page,limit));
    }

    @GetMapping("/get-one/{id}")
    public ResponseEntity<?> getAll(
            @PathVariable("id")Integer id
    ){
        return ResponseEntity.ok(phieuGiamGiaService.getOne(id));
    }
}
