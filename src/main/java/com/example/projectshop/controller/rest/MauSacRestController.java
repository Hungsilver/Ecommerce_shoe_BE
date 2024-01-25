package com.example.projectshop.controller.rest;

import com.example.projectshop.domain.MauSac;
import com.example.projectshop.domain.Xuatxu;
import com.example.projectshop.dto.auth.LoginRequest;
import com.example.projectshop.dto.danhmuc.ExcelDanhMuc;
import com.example.projectshop.dto.mausac.ExcelMauSac;
import com.example.projectshop.dto.mausac.MauSacRequest;
import com.example.projectshop.dto.xuatxu.XuatXuRequest;
import com.example.projectshop.service.IMauSacService;
import com.example.projectshop.service.ObjectMapperUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/api/color")
public class MauSacRestController {
    @Autowired
    private IMauSacService mauSacService;

    private String p_chu = "\\d+";

    @GetMapping//localhost:8080/api/color
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
        Page<MauSac> mauSacs;
        if (keyword != null && !keyword.isEmpty()) {
            mauSacs = mauSacService.findAllByName(keyword, pageable);
        } else {
            mauSacs = mauSacService.getAll(pageable);
        }
        return ResponseEntity.ok(mauSacs);
    }

    @GetMapping("{id}")//localhost:8080/api/color/1
    public ResponseEntity<?> findById(@PathVariable String id) {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id màu sắc phải là số");
        }
        return ResponseEntity.ok(mauSacService.findById(Integer.valueOf(id)));
    }

    @PostMapping//localhost:8080/api/color
    public ResponseEntity<?> create(@RequestBody MauSacRequest request) {
        MauSac xx = ObjectMapperUtils.map(request, MauSac.class);
        return ResponseEntity.ok(mauSacService.create(xx));
    }

    @PutMapping("{id}")//localhost:8080/api/color/1
    public ResponseEntity<?> update(@RequestBody MauSacRequest request,
                                    @PathVariable("id") String id)
    {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id màu sắc phải là số");
        }
        MauSac xx = ObjectMapperUtils.map(request, MauSac.class);
        return ResponseEntity.ok(mauSacService.update(xx, Integer.valueOf(id)));
    }

    @DeleteMapping("{id}")//localhost:8080/api/color/1
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id màu sắc phải là số");
        }
        return ResponseEntity.ok(mauSacService.delete(Integer.valueOf(id)));
    }

    @GetMapping("/excel/export")//localhost:8080/api/color/excel/export
    public  ResponseEntity<?> exportExcel() {
        return ResponseEntity.ok(mauSacService.exportExcel());
    }

    @PostMapping("/excel/import")//localhost:8080/api/color/excel/import
    public  ResponseEntity<?> exportExcel(@RequestBody List<ExcelMauSac> excelMauSacs){
        return ResponseEntity.ok(mauSacService.importExcel(excelMauSacs));
    }

}
