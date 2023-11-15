package com.example.projectshop.controller.rest;

import com.example.projectshop.domain.KhachHang;
import com.example.projectshop.dto.khachhang.KhachHangRequest;
import com.example.projectshop.dto.phieugiamgia.PhieuGiamGiaRequest;
import com.example.projectshop.exception.UnauthorizedException;
import com.example.projectshop.service.IKhachHangService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/customer")
public class KhachHangRestController {

    @Autowired
    @Lazy
    private IKhachHangService khachHangService;

    @Autowired
    private HttpSession httpSession;
    
    @GetMapping("/get-all")
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sortField", required = false, defaultValue = "id") String sortField,
            @RequestParam(value = "isSortDesc", required = false, defaultValue = "false") Boolean isSortDesc,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        return ResponseEntity.ok(khachHangService.findAll(page, pageSize, sortField, isSortDesc, keyword));
    }

    @PostMapping()
    public ResponseEntity<?> create(
            @RequestBody KhachHangRequest khachHangRequest
    ) {
        return ResponseEntity.ok(khachHangService.create(khachHangRequest));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(khachHangService.findById(id));
    }


    @PutMapping("{id}")
    public ResponseEntity<?> update(
            @RequestBody KhachHangRequest khachHangRequest,
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(khachHangService.update(id, khachHangRequest));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(khachHangService.delete(id));
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerKhachHang(@RequestBody KhachHangRequest khachHangRequest) {
        try {
             khachHangService.registerKhachHang(khachHangRequest);
            return ResponseEntity.ok("Đăng Ký thành công!");
        }catch (UnauthorizedException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginKhachHang(@RequestBody KhachHangRequest khachHangRequest) {
        try {
            KhachHang khachHang= khachHangService.loginKhachHang(khachHangRequest.getEmail(), khachHangRequest.getMatKhau());
            httpSession.setAttribute("khachHang",khachHang);
            return ResponseEntity.ok("Đăng nhập thành công!");
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/check-login-status")
    public ResponseEntity<String> checkLoginStatus(){
        KhachHang khachHang= (KhachHang) httpSession.getAttribute("khachHang");
        if (khachHang != null) {
            return ResponseEntity.ok("khách hàng đã đăng nhập");
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("khách hàng chưa đăng nhập");
        }
    }


    @GetMapping("/checkSession")
    public ResponseEntity<String> checkSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            // Lấy thông tin khách hàng từ Session
            KhachHang khachHang = (KhachHang) session.getAttribute("khachHang");

            if (khachHang != null) {
                // Khách hàng đã đăng nhập
                return ResponseEntity.ok("Khách hàng đã đăng nhập: " + khachHang.getHoTen());
            } else {
                // Session tồn tại nhưng không có thông tin khách hàng
                return ResponseEntity.ok("Khách hàng chưa đăng nhập");
            }
        } else {
            // Session không tồn tại
            return ResponseEntity.ok("Khách hàng chưa đăng nhập");
        }
    }

}
