package com.example.projectshop.controller.rest;

import com.example.projectshop.domain.KichCo;
import com.example.projectshop.domain.Xuatxu;
import com.example.projectshop.dto.kichco.KichCoRequest;
import com.example.projectshop.dto.xuatxu.XuatXuRequest;
import com.example.projectshop.service.IKichCoService;
import com.example.projectshop.service.ObjectMapperUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/size")
public class KichCoRestController {

    @Autowired
    private IKichCoService kichCoService;
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
        Page<KichCo> kichCos;
        if (keyword != null && !keyword.isEmpty()) {
            kichCos = kichCoService.findAllByName(keyword, pageable);
        } else {
            kichCos = kichCoService.getAll(pageable);
        }
        return ResponseEntity.ok(kichCos);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(kichCoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody KichCoRequest request) {
        KichCo xx = ObjectMapperUtils.map(request, KichCo.class);
        return ResponseEntity.ok(kichCoService.create(xx));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody KichCoRequest request, @PathVariable("id") Integer id) {
        KichCo xx = ObjectMapperUtils.map(request, KichCo.class);
        return ResponseEntity.ok(kichCoService.update(xx, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(kichCoService.delete(id));
    }



}
