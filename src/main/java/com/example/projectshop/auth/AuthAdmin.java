package com.example.projectshop.auth;

import com.example.projectshop.domain.GioHangChiTiet;
import com.example.projectshop.domain.KhachHang;
import com.example.projectshop.domain.NhanVien;
import com.example.projectshop.dto.BaseResponse;
import com.example.projectshop.dto.auth.LoginRequest;
import com.example.projectshop.dto.auth.RegisterRequest;
import com.example.projectshop.dto.khachhang.KhachHangRequest;
import com.example.projectshop.dto.nhanvien.NhanVienRequest;
import com.example.projectshop.dto.nhanvien.NhanVienResponse;
import com.example.projectshop.service.impl.NhanVienServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth/admin")
public class AuthAdmin {

    @Autowired
    private NhanVienServiceImpl nhanVienService;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //TODO role: ADMIN và STAFF
    @PostMapping("/register-account")
    public ResponseEntity<NhanVien> registerAccount(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(nhanVienService.registerAccount(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<NhanVien> login(@RequestBody LoginRequest staffRequest) {
        try {
            NhanVien nv = nhanVienService.authenticateUser(staffRequest);
            return ResponseEntity.ok(nv);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/forgot-password/{email}")//localhost:8080/api/auth/admin/forgot-password
    public ResponseEntity<?> forgotPassword(@PathVariable("email")String email) throws UnsupportedEncodingException, MessagingException {
        httpSession.setAttribute("emailAdminForgot",email);
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message);
//
//        helper.setFrom("vuttph25379@fpt.edu.vn", "Shop Gens-z");
//        helper.setTo(email);
//
//        String subject = "Đây là liên kết để đặt lại mật khẩu của bạn";
//
//        String content = "<p>Xin Chào,<p>" +
//                "<p> Bạn đã yêu cầu đặt lại mật khẩu của mình.</p>" +
//                "<p> Nhấp vào liên kết bên dưới để thay đổi mật khẩu của bạn:</p>" +
//                "<p><b><a href=" + "https://translate.google.com.vn/?hl=en&sl=en&tl=vi&op=translate" + ">" + "https://translate.google.com.vn/?hl=en&sl=en&tl=vi&op=translate</a><b></p>" +
//                "<p> Bỏ qua email này nếu bạn nhớ mật khẩu của mình hoặc bạn chưa thực hiện yêu cầu";
//        helper.setSubject(subject);
//        helper.setText(content, true);
//        System.out.println(passwordEncoder.encode("123"));
//        mailSender.send(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.<GioHangChiTiet>builder()
                .code(200)
                .isOK(true)
                .data(null)
                .message("Send email successfully")
                .build()
        );
    }

    @PostMapping("/reset-password")//localhost:8080/api/auth/admin/forgot-password
    public ResponseEntity<?> resetPassword(@RequestParam("password")String password){
//        String email = (String) httpSession.getAttribute("emailAdminForgot");
//        NhanVien nhanVien = nhanVienService.findByEmail(email);
//        nhanVien.setMatKhau(passwordEncoder.encode(password));
//        nhanVienService.update(nhanVien,nhanVien.getId());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.<GioHangChiTiet>builder()
                .code(200)
                .isOK(true)
                .data(null)
                .message("Reset password successfully")
                .build()
        );

    }
}
