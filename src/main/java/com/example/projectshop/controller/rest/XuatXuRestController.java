package com.example.projectshop.controller.rest;

<<<<<<< HEAD
import com.example.projectshop.dto.xuatxu.XuatXuRequest;
import com.example.projectshop.dto.xuatxu.XuatXuResponse;
=======
import com.example.projectshop.domain.Xuatxu;
import com.example.projectshop.dto.xuatxu.XuatXuRequest;
>>>>>>> develop
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
<<<<<<< HEAD
import org.springframework.web.bind.annotation.*;
=======
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
>>>>>>> develop

@Controller
@RequestMapping("/api/origin")
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "*")
public class XuatXuRestController {

//    @Autowired
//    private IXuatXuService service;

//    @Autowired
//    private HttpServletRequest request;
//
//    @GetMapping("/get-all")
//    public ResponseEntity<?> findAll() {
//        String page = request.getParameter("page");
//        String limit = request.getParameter("limit");
//        return ResponseEntity.ok(service.findAllXuatXu(page,limit));
//    }
    @Autowired
    private XuatXuServiceImpl xuatXuService;

<<<<<<< HEAD
    @GetMapping("/get-all")
    public ResponseEntity<?> findAll() {
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        return ResponseEntity.ok(service.findAllXuatXu(page, limit));
=======
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
        Page<Xuatxu> xuatxus;
        if (keyword != null && !keyword.isEmpty()) {
            xuatxus = xuatXuService.findAllByName(keyword, pageable);
        } else {
            xuatxus = xuatXuService.findAll(pageable);
        }
        return ResponseEntity.ok(xuatxus);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(xuatXuService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody XuatXuRequest request) {
        Xuatxu xx = ObjectMapperUtils.map(request, Xuatxu.class);
        return ResponseEntity.ok(xuatXuService.create(xx));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody XuatXuRequest request, @PathVariable("id") Integer id) {
        Xuatxu xx = ObjectMapperUtils.map(request, Xuatxu.class);
        return ResponseEntity.ok(xuatXuService.update(xx, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(xuatXuService.delete(id));
>>>>>>> develop
    }


    @PostMapping("/create")
    public ResponseEntity<XuatXuResponse> create(@RequestBody XuatXuRequest xuatXuRequest) {
        XuatXuResponse response = service.create(xuatXuRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update")
    public ResponseEntity<XuatXuResponse> update(@RequestBody XuatXuRequest xuatXuRequest) {

        Integer id = xuatXuRequest.getId();
        XuatXuResponse response = service.update(xuatXuRequest, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<?> delete(@PathVariable("id") Integer id){
        service.delete(id);
        return ResponseEntity.ok("xoa thanh cong");
    }



}
