package com.example.projectshop.controller.rest;

import com.example.projectshop.domain.ChucVu;
import com.example.projectshop.domain.Xuatxu;
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

    @GetMapping()//localhost:8080/api/position
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sortField", required = false, defaultValue = "id") String sortField,
            @RequestParam(value = "isSortDesc", required = false, defaultValue = "false") Boolean isSortDesc,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        Sort sort = Sort.by(isSortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, pageSize, sort);
        Page<ChucVu> chucVus;
        if (keyword != null && !keyword.isEmpty()) {
            chucVus = chucVuService.findAllByName(keyword, pageable);
        } else {
            chucVus = chucVuService.getAll(pageable);
        }
        return ResponseEntity.ok(chucVus);
    }
    @GetMapping("{id}")//localhost:8080/api/position/1
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(chucVuService.findById(id));
    }

    @PostMapping//localhost:8080/api/position
    public ResponseEntity<?> create(@RequestBody XuatXuRequest request) {
        ChucVu xx = ObjectMapperUtils.map(request, ChucVu.class);
        return ResponseEntity.ok(chucVuService.create(xx));
    }

    @PutMapping("{id}")//localhost:8080/api/position/1
    public ResponseEntity<?> update(@RequestBody XuatXuRequest request, @PathVariable("id") Integer id) {
        ChucVu xx = ObjectMapperUtils.map(request, ChucVu.class);
        return ResponseEntity.ok(chucVuService.update(xx, id));
    }

    @DeleteMapping("{id}")//localhost:8080/api/position/1
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(chucVuService.delete(id));
    }


}

