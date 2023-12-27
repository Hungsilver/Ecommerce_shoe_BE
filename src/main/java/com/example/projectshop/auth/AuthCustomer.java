package com.example.projectshop.auth;

import com.example.projectshop.domain.KhachHang;
import com.example.projectshop.dto.BaseResponse;
import com.example.projectshop.dto.khachhang.KhachHangRequest;
import com.example.projectshop.dto.khachhang.LoginKhachHang;
import com.example.projectshop.service.IKhachHangService;
import com.example.projectshop.utilities.utility;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth/customer")
//@RequiredArgsConstructor
public class AuthCustomer {

    @Autowired
    @Lazy
    private IKhachHangService khachHangService;

    BaseResponse<KhachHang> base = new BaseResponse<>();


    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private WebApplicationContext appContext;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/register")//localhost:8080/api/auth/customer/register
    // trả về chuỗi string đăng ký thành công hay thất bại
    public ResponseEntity<KhachHang> registerKhachHang(
            @RequestBody KhachHangRequest khachHangRequest) {
        KhachHang khachHang = khachHangService.registerKhachHang(khachHangRequest);
        return ResponseEntity.ok(khachHang);
    }

    @PostMapping("/login")//localhost:8080/api/auth/customer/login
    public ResponseEntity<?> loginKhachHang(
            @RequestBody LoginKhachHang loginKhachHang) {

        if (loginKhachHang == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(base.createBaseResponse(HttpStatus.UNAUTHORIZED.value(), null, false, "Thông tin khách hàng trống"));
        }
        KhachHang khachHang = khachHangService.loginKhachHang(loginKhachHang.getEmail(), loginKhachHang.getMatKhau());
        if (khachHang == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(base.createBaseResponse(HttpStatus.UNAUTHORIZED.value(), null, false, "Tài khoản hoặc mật khẩu không chính xác"));
        }

        appContext.getServletContext().setAttribute("khachHang", khachHang);// lưu thông tin khách hàng đăng nhập


        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.<Object>builder()
                .code(200)
                .isOK(false)
                .data(appContext.getServletContext().getAttribute("khachHang"))
                .message("Login successfully")
                .build()
        );// đăng nhập thành công trả về thông tin của khách hàng
    }

    @GetMapping("/logout")//localhost:8080/api/auth/customer/check-login-status
    public ResponseEntity<?> logout() {
        appContext.getServletContext().removeAttribute("khachHang");
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.<Object>builder()
                .code(200)
                .isOK(false)
                .data(appContext.getServletContext().getAttribute("khachHang"))
                .message("Login successfully")
                .build()
        );
    }

    @GetMapping("/check-login-status")//localhost:8080/api/auth/customer/check-login-status
    public ResponseEntity<String> checkLoginStatus(HttpSession session) {
        KhachHang khachHang = (KhachHang) session.getAttribute("khachHang");
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

    @PostMapping("/forgot-password")//localhost:8080/api/auth/customer/forgot-password
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email) throws UnsupportedEncodingException, MessagingException {
        KhachHang khachHang = khachHangService.findByEmail(email);
        if (khachHang == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseResponse.<String>builder()
                    .code(500)
                    .isOK(false)
                    .data(email)
                    .message("Email not found")
                    .build()
            );
        }

        String password = utility.generateRandomString(6);
        KhachHangRequest khachHangRequest = KhachHangRequest.builder()
                .id(khachHang.getId())
                .hoTen(khachHang.getHoTen())
                .email(khachHang.getEmail())
                .matKhau(passwordEncoder.encode(password))
                .soDienThoai(khachHang.getSoDienThoai())
                .ngaySinh(khachHang.getNgaySinh())
                .trangThai(khachHang.getTrangThai())
                .build();
        khachHangService.update(khachHang.getId(), khachHangRequest);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("vuttph25379@fpt.edu.vn", "Shop Gens-z");
        helper.setTo(email);

        String subject = "Shop GENS-Z - Đây là mật khẩu của bạn";

        String content = "<p>Xin Chào,<p>" +
                "<p> Bạn đã yêu cầu lấy lại mật khẩu của mình.</p>" +
                "<p> Đây là mật khẩu của bạn: " + password + "</p>" +
                "<p> Bỏ qua email này nếu bạn nhớ mật khẩu của mình hoặc bạn chưa thực hiện yêu cầu";
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.<String>builder()
                .code(200)
                .isOK(true)
                .data(null)
                .message("Send email successfully")
                .build()
        );
    }
}
