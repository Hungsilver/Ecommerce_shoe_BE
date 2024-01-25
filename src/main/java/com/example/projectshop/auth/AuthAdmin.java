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
import com.example.projectshop.service.IKhachHangService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

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
    private AuthService authService;

    @Autowired
    private WebApplicationContext appContext;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IKhachHangService khachHangService;

    //TODO role: ADMIN và STAFF
    @PostMapping("/register-account")
    public ResponseEntity<?> registerAccount(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(nhanVienService.registerAccount(registerRequest));
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest staffRequest) {
        try {
            NhanVien nv = nhanVienService.authenticateUser(staffRequest);
            if (nv != null) {
                appContext.getServletContext().setAttribute("nhanVien", nv);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(BaseResponse.builder()
                                      .code(200)
                                      .isOK(true)
                                      .data(nv)
                                      .message("success")
                                      .build()
                        );
            } else {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(BaseResponse.builder()
                                      .code(400)
                                      .isOK(false)
                                      .data(null)
                                      .message("ko thanh cong")
                                      .build()
                        );
            }

        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        Objects.requireNonNull(appContext.getServletContext()).removeAttribute("nhanVien");
        Objects.requireNonNull(appContext.getServletContext()).removeAttribute("admin");

        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.builder()
                              .code(200)
                              .isOK(true)
                              .data(null)
                              .message("success logout")
                              .build()
                );
    }

    @GetMapping("/forget-password/{email}")//localhost:8080/api/auth/admin/forgot-password
    public ResponseEntity<?> forgetPassword(@PathVariable("email") String email) throws UnsupportedEncodingException, MessagingException {
        NhanVien nv = nhanVienService.findByEmail(email);

        if (nv != null) {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message);
                String passwordNew = authService.generateRandomPassword();

                helper.setFrom("hungtdph25410@fpt.edu.vn", "Shop Gens-z");
                helper.setTo(email);

                String subject = "Đây là liên kết để đặt lại mật khẩu của bạn";

                String content = "<p>Xin Chào,<p>" +
                        "<p> Bạn đã yêu cầu đặt lại mật khẩu của mình.</p>" +
                        "<p> Đây là mật khẩu của bạn: </p>" +
                        "<p>" + passwordNew + "<p>" +
                        "<p> Bỏ qua email này nếu bạn nhớ mật khẩu của mình hoặc bạn chưa thực hiện yêu cầu";
                helper.setSubject(subject);
                helper.setText(content, true);
                mailSender.send(message);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.<GioHangChiTiet>builder()
                                                                                  .code(200)
                                                                                  .isOK(true)
                                                                                  .data(null)
                                                                                  .message("Send email successfully")
                                                                                  .build()
                );
            }catch (Exception e){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.<GioHangChiTiet>builder()
                                                                                  .code(400)
                                                                                  .isOK(false)
                                                                                  .data(null)
                                                                                  .message("Send email failed")
                                                                                  .build()
                );
            }


        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.<GioHangChiTiet>builder()
                                                                              .code(400)
                                                                              .isOK(false)
                                                                              .data(null)
                                                                              .message("email not found")
                                                                              .build()
            );
        }
    }

    @PostMapping("/forget-password-confirm")//localhost:8080/api/auth/admin/forget-password-confirm
    public ResponseEntity<?> forgetPassConfirm(@RequestParam("password") String password) {
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

    @GetMapping("/reset-password")//localhost:8080/api/auth/admin/forgot-password
    public ResponseEntity<?> resetPassword(@RequestParam("password") String password) {
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
