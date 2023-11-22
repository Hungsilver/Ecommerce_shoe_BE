package com.example.projectshop.controller.rest;

import com.example.projectshop.domain.ChatLieuGiay;
import com.example.projectshop.domain.ChucVu;
import com.example.projectshop.domain.Xuatxu;
import com.example.projectshop.dto.chucvu.ChucVuRequest;
import com.example.projectshop.dto.xuatxu.XuatXuRequest;
import com.example.projectshop.service.ObjectMapperUtils;
import com.example.projectshop.service.impl.ChucVuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/position")
public class ChucVuRestController {

    @Autowired
    private ChucVuServiceImpl chucVuService;

    private String p_chu = "\\d+";

    @GetMapping()//localhost:8080/api/position
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") String pageSize,
            @RequestParam(value = "sortField", required = false, defaultValue = "id") String sortField,
            @RequestParam(value = "isSortDesc", required = false, defaultValue = "false") Boolean isSortDesc,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        if (!page.matches(p_chu)|| !pageSize.matches(p_chu)){
            return ResponseEntity.ok("*page || pageSize phải là số");
        }
        Sort sort = Sort.by(isSortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(Integer.valueOf(page) > 0 ? Integer.valueOf(page) - 1 : Integer.valueOf(page), Integer.valueOf(pageSize), sort);
        Page<ChucVu> chucVus;
        if (keyword != null && !keyword.isEmpty()) {
            chucVus = chucVuService.findAllByName(keyword, pageable);
        } else {
            chucVus = chucVuService.getAll(pageable);
        }
        return ResponseEntity.ok(chucVus);
    }
    @GetMapping("{id}")//localhost:8080/api/position/1
    public ResponseEntity<?> findById(@PathVariable String id) {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id chức vụ phải là số");
        }
        return ResponseEntity.ok(chucVuService.findById(Integer.valueOf(id)));
    }

    @PostMapping//localhost:8080/api/position
    public ResponseEntity<?> create(@RequestBody XuatXuRequest request) {
        ChucVu xx = ObjectMapperUtils.map(request, ChucVu.class);
        return ResponseEntity.ok(chucVuService.create(xx));
    }

    @PutMapping("{id}")//localhost:8080/api/position/1
    public ResponseEntity<?> update(@RequestBody ChucVuRequest request,
                                    @PathVariable("id") String id) {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id chức vụ phải là số");
        }
        ChucVu xx = ObjectMapperUtils.map(request, ChucVu.class);
        return ResponseEntity.ok(chucVuService.update(xx, Integer.valueOf(id)));
    }

    @DeleteMapping("{id}")//localhost:8080/api/position/1
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id chức vụ phải là số");
        }
        return ResponseEntity.ok(chucVuService.delete(Integer.valueOf(id)));
    }


}

