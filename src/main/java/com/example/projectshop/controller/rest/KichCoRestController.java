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

    private String p_chu = "\\d+";

    @GetMapping//localhost:8080/api/size
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
        Page<KichCo> kichCos;
        if (keyword != null && !keyword.isEmpty()) {
            kichCos = kichCoService.findAllByName(keyword, pageable);
        } else {
            kichCos = kichCoService.getAll(pageable);
        }
        return ResponseEntity.ok(kichCos);
    }

    @GetMapping("{id}")//localhost:8080/api/size/1
    public ResponseEntity<?> findById(@PathVariable String id) {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id kích cỡ phải là số");
        }
        return ResponseEntity.ok(kichCoService.findById(Integer.valueOf(id)));
    }

    @PostMapping//localhost:8080/api/size/1
    public ResponseEntity<?> create(@RequestBody KichCoRequest request) {
        KichCo xx = ObjectMapperUtils.map(request, KichCo.class);
        return ResponseEntity.ok(kichCoService.create(xx));
    }

    @PutMapping("{id}")///localhost:8080/api/size/1
    public ResponseEntity<?> update(@RequestBody KichCoRequest request,
                                    @PathVariable("id") String id)
    {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id kích cỡ phải là số");
        }
        KichCo xx = ObjectMapperUtils.map(request, KichCo.class);
        return ResponseEntity.ok(kichCoService.update(xx, Integer.valueOf(id)));
    }

    @DeleteMapping("{id}")//localhost:8080/api/size/1
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id kích cỡ phải là số");
        }
        return ResponseEntity.ok(kichCoService.delete(Integer.valueOf(id)));
    }



}
