package com.example.projectshop.controller.rest;

import com.example.projectshop.domain.MauSac;
import com.example.projectshop.domain.Xuatxu;
import com.example.projectshop.dto.mausac.MauSacRequest;
import com.example.projectshop.dto.xuatxu.XuatXuRequest;
import com.example.projectshop.service.IMauSacService;
import com.example.projectshop.service.ObjectMapperUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping("/api/colors")
public class MauSacRestController {
    @Autowired
    private IMauSacService mauSacService;

    @Autowired
    private HttpServletRequest request;


    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sortField", required = false, defaultValue = "id") String sortField,
            @RequestParam(value = "isSortDesc", required = false, defaultValue = "false") Boolean isSortDesc,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        Sort sort = Sort.by(isSortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, pageSize, sort);
        Page<MauSac> mauSacs;
        if (keyword != null && !keyword.isEmpty()) {
            mauSacs = mauSacService.findAllByName(keyword, pageable);
        } else {
            mauSacs = mauSacService.getAll(pageable);
        }
        return ResponseEntity.ok(mauSacs);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(mauSacService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody MauSacRequest request) {
        MauSac xx = ObjectMapperUtils.map(request, MauSac.class);
        return ResponseEntity.ok(mauSacService.create(xx));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody MauSacRequest request, @PathVariable("id") Integer id) {
        MauSac xx = ObjectMapperUtils.map(request, MauSac.class);
        return ResponseEntity.ok(mauSacService.update(xx, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(mauSacService.delete(id));
    }



}
