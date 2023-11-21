package com.example.projectshop.controller.rest;

import com.example.projectshop.domain.Xuatxu;
import com.example.projectshop.dto.xuatxu.XuatXuRequest;
import com.example.projectshop.service.IXuatXuService;
import com.example.projectshop.service.ObjectMapperUtils;
import com.example.projectshop.service.impl.XuatXuServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/origin")
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "*")
public class XuatXuRestController {

    @Autowired
    private XuatXuServiceImpl xuatXuService;

    @GetMapping//localhost:8080/api/origin...
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sortField", required = false, defaultValue = "id") String sortField,
            @RequestParam(value = "isSortDesc", required = false, defaultValue = "false") Boolean isSortDesc,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        Sort sort = Sort.by(isSortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, pageSize, sort);
        Page<Xuatxu> xuatxus;
        if (keyword != null && !keyword.isEmpty()) {
            xuatxus = xuatXuService.findAllByName(keyword, pageable);
        } else {
            xuatxus = xuatXuService.findAll(pageable);
        }
        return ResponseEntity.ok(xuatxus);
    }

    @GetMapping("{id}")//localhost:8080/api/origin/1
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(xuatXuService.findById(id));
    }

    @PostMapping//localhost:8080/api/origin
    public ResponseEntity<?> create(@RequestBody XuatXuRequest request) {
        Xuatxu xx = ObjectMapperUtils.map(request, Xuatxu.class);
        return ResponseEntity.ok(xuatXuService.create(xx));
    }

    @PutMapping("{id}")//localhost:8080/api/origin/1
    public ResponseEntity<?> update(@RequestBody XuatXuRequest request, @PathVariable("id") Integer id) {
        Xuatxu xx = ObjectMapperUtils.map(request, Xuatxu.class);
        return ResponseEntity.ok(xuatXuService.update(xx, id));
    }

    @DeleteMapping("{id}")//localhost:8080/api/origin/1
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(xuatXuService.delete(id));
    }

}
