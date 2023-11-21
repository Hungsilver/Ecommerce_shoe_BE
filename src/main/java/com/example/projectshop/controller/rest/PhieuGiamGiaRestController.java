package com.example.projectshop.controller.rest;

import com.example.projectshop.dto.phieugiamgia.PhieuGiamGiaRequest;
import com.example.projectshop.service.IPhieuGiamGiaService;
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

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/voucher")
public class PhieuGiamGiaRestController {

    @Autowired
    private IPhieuGiamGiaService phieuGiamGiaService;

    @GetMapping()//localhost:8080/api/voucher
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sortField", required = false, defaultValue = "id") String sortField,
            @RequestParam(value = "isSortDesc", required = false, defaultValue = "false") Boolean isSortDesc,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        return ResponseEntity.ok(phieuGiamGiaService.findAll(page,pageSize,sortField,isSortDesc,keyword));
    }

    @PostMapping()//localhost:8080/api/voucher
    public ResponseEntity<?> create(
            @RequestBody PhieuGiamGiaRequest phieuGiamGiaRequest
    ) {
        return ResponseEntity.ok(phieuGiamGiaService.create(phieuGiamGiaRequest));
    }

    @GetMapping("{id}")//localhost:8080/api/voucher/1
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(phieuGiamGiaService.findById(id));
    }


    @PutMapping("{id}")//localhost:8080/api/voucher/1
    public ResponseEntity<?> update(
            @RequestBody PhieuGiamGiaRequest phieuGiamGiaRequest,
            @PathVariable("id") Integer id
    ){
        return ResponseEntity.ok(phieuGiamGiaService.update(id,phieuGiamGiaRequest));
    }

    @DeleteMapping("{id}")//localhost:8080/api/voucher/1
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(phieuGiamGiaService.delete(id));
    }
}
