package com.example.projectshop.auth;

import com.example.projectshop.domain.KhachHang;
import com.example.projectshop.dto.khachhang.KhachHangRequest;
import com.example.projectshop.dto.khachhang.LoginKhachHang;
import com.example.projectshop.exception.UnauthorizedException;
import com.example.projectshop.service.IKhachHangService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth/customer")
public class AuthCustomer {

    @Autowired
    @Lazy
    private IKhachHangService khachHangService;

    @Autowired
    private HttpSession httpSession;

    @PostMapping("/register")//localhost:8080/api/auth/customer/register
    // trả về chuỗi string đăng ký thành công hay thất bại
    public ResponseEntity<KhachHang> registerKhachHang(@RequestBody KhachHangRequest khachHangRequest) {
         KhachHang khachHang= khachHangService.registerKhachHang(khachHangRequest);
            return ResponseEntity.ok(khachHang);
    }

    @PostMapping("/login")//localhost:8080/api/auth/customer/login
    public ResponseEntity<?> loginKhachHang(@RequestBody LoginKhachHang loginKhachHang) {
            KhachHang khachHang = khachHangService.loginKhachHang(loginKhachHang.getEmail(), loginKhachHang.getMatKhau());
            httpSession.setAttribute("khachHang", khachHang);
            return ResponseEntity.ok(khachHang); // đăng ký thành công trả về thông tin của khách hàng
    }

    @GetMapping("/check-login-status")//localhost:8080/api/auth/customer/check-login-status
    public ResponseEntity<String> checkLoginStatus(){
        KhachHang khachHang= (KhachHang) httpSession.getAttribute("khachHang");

        if (khachHang != null) {
            return ResponseEntity.ok("khách hàng đã đăng nhập");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("khách hàng chưa đăng nhập");
        }
    }


    @GetMapping("/checkSession")//localhost:8080/api/auth/customer/checkSession
    public ResponseEntity<String> checkSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
//        check-login-status bor
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
