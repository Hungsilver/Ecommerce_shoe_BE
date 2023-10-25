package com.example.projectshop.controller.rest;


import com.example.projectshop.dto.sanpham.SanPhamRequest;
import com.example.projectshop.repository.ChatLieuDeGiayRepository;
import com.example.projectshop.repository.SanPhamRepository;
import com.example.projectshop.service.ISanPhamService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/api/product")
public class SanPhamRestController {
    @Autowired
    private ISanPhamService service;


    @GetMapping()
    public ResponseEntity<?> findAll(
            @RequestParam(value = "pricemin",required = false) String pricemin,
            @RequestParam(value = "pricemax",required = false) String pricemax,
            @RequestParam(value = "trademark",required = false) String trademark,
            @RequestParam(value = "origin",required = false) String origin,
            @RequestParam(value = "color",required = false) String color,
            @RequestParam(value = "shoe_material",required = false) String shoe_material,
            @RequestParam(value = "shoe_sole_material",required = false) String shoe_sole_material,
            @RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize
    ){
        return ResponseEntity.ok(service.findAll(pricemin,pricemax,trademark,origin,color,shoe_material,shoe_sole_material, page,pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody SanPhamRequest sanPhamRequest) {
        return ResponseEntity.ok(service.create(sanPhamRequest));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(
            @PathVariable("id") Integer id,
            @RequestBody SanPhamRequest sanPhamRequest
    ) {
        return ResponseEntity.ok(service.update(id, sanPhamRequest));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam("keyword")String keyword,
            @RequestParam(value = "page",required = false,defaultValue = "1")Integer page,
            @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize
    ) {
        return ResponseEntity.ok(service.search(keyword,page,pageSize));
    }

}
