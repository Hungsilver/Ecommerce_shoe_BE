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

@CrossOrigin
@RestController
@RequestMapping("/api/san-pham")
public class SanPhamRestController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ISanPhamService service;


    @GetMapping("/find-all")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(
            @RequestParam(value = "pricemin",required = false) String pricemin,
            @RequestParam(value = "pricemax",required = false) String pricemax,
            @RequestParam(value = "thuonghieu",required = false) String thuonghieu,
            @RequestParam(value = "xuatxu",required = false) String xuatxu,
            @RequestParam(value = "mausac",required = false) String mausac,
            @RequestParam(value = "chatlieugiay",required = false) String chatlieugiay,
            @RequestParam(value = "chatlieudegiay",required = false) String chatlieudegiay,
            @RequestParam(value = "page",required = false) Integer page,
            @RequestParam(value = "pageSize",required = false) Integer pageSize
    ){
        System.out.println(thuonghieu);
        return ResponseEntity.ok(service.getAllByParam(pricemin,pricemax,thuonghieu,xuatxu,mausac,chatlieugiay,chatlieudegiay, page,pageSize));
    }

    @GetMapping("/get-one/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(service.getOne(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody SanPhamRequest sanPhamRequest) {
        return ResponseEntity.ok(service.create(sanPhamRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable("id") Integer id,
            @RequestBody SanPhamRequest sanPhamRequest
    ) {
        return ResponseEntity.ok(service.update(id, sanPhamRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<?> timKiem() {
        String timKiem = request.getParameter("timKiem");
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        return ResponseEntity.ok(service.timKiem(timKiem, page, limit));
    }

}
