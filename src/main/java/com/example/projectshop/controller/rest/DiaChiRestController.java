package com.example.projectshop.controller.rest;

import com.example.projectshop.dto.diachi.DiaChiRequest;
import com.example.projectshop.dto.gioithieu.GioiThieuRequest;
import com.example.projectshop.service.IDiaChiService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/address")
public class DiaChiRestController {
    @Autowired
    private IDiaChiService diaChiService;

    private String p_chu = "\\d+";

    @GetMapping()//localhost:8080/api/address
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") String pageSize,
            @RequestParam(value = "sortField", required = false, defaultValue = "id") String sortField,
            @RequestParam(value = "isSortDesc", required = false, defaultValue = "false") Boolean isSortDesc,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        if (!page.matches(p_chu)|| !pageSize.matches(p_chu)){
            return ResponseEntity.ok("*page || pageSize dịa chỉ phải là số");
        }
        return ResponseEntity.ok(diaChiService.findAll(Integer.valueOf(page),Integer.valueOf(pageSize),sortField,isSortDesc,keyword));
    }

    @PostMapping()//localhost:8080/api/address
    public ResponseEntity<?> create(
            @RequestBody DiaChiRequest diaChiRequest
    ) {
        return ResponseEntity.ok(diaChiService.create(diaChiRequest));
    }

    @GetMapping("{id}")//localhost:8080/api/address/1
    public ResponseEntity<?> findById(@PathVariable String id) {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id địa chỉ phải là số");
        }
        return ResponseEntity.ok(diaChiService.findById(Integer.valueOf(id)));
    }


    @PutMapping("{id}")//localhost:8080/api/address/1
    public ResponseEntity<?> update(
            @RequestBody DiaChiRequest diaChiRequest,
            @PathVariable("id") String id
    ){
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id địa chỉ phải là số ");
        }
        return ResponseEntity.ok(diaChiService.update(Integer.valueOf(id),diaChiRequest));
    }

    @DeleteMapping("{id}")//localhost:8080/api/address/1
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        if (!id.matches(p_chu)){
            return ResponseEntity.ok("*id địa chỉ phải là số");
        }
        return ResponseEntity.ok(diaChiService.delete(Integer.valueOf(id)));
    }
}
