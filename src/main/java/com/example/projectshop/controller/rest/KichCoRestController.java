package com.example.projectshop.controller.rest;

import com.example.projectshop.dto.kichco.KichCoRequest;
import com.example.projectshop.service.IKichCoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/kichco")
public class KichCoRestController {

    @Autowired
    private IKichCoService service;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/get-all")
    public ResponseEntity<?> findAll() {
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        return ResponseEntity.ok(service.findAllKichCo(page,limit));
    }

    @GetMapping(value =  "/findAll")
    public ResponseEntity<?> getALl(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping(value =  "/findById/{id}")
    public ResponseEntity<?> getALl(@PathVariable("id")Integer id){
        return ResponseEntity.ok(service.findById(id));
    }


    @PostMapping(value =  "/create",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> create(@RequestBody KichCoRequest request){
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping(value =  "/update/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> update(@RequestBody KichCoRequest request, @PathVariable("id")Integer id){
        return ResponseEntity.ok(service.update(request,id));
    }

    @DeleteMapping(value =  "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
