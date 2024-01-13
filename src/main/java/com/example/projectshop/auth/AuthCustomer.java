package com.example.projectshop.auth;

import com.example.projectshop.domain.KhachHang;
import com.example.projectshop.dto.BaseResponse;
import com.example.projectshop.dto.khachhang.KhachHangRequest;
import com.example.projectshop.dto.khachhang.LoginKhachHang;
import com.example.projectshop.service.IKhachHangService;
import com.example.projectshop.service.ObjectMapperUtils;
import com.example.projectshop.utilities.utility;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpCookie;
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
@Slf4j
@RequestMapping("/api/auth/customer")
//@RequiredArgsConstructor
public class AuthCustomer {

    @Autowired
    @Lazy
    private IKhachHangService khachHangService;

    BaseResponse<KhachHang> base = new BaseResponse<>();

    @Autowired
    private AuthService authService;


    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private WebApplicationContext appContext;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private HttpSession httpSession;

//    @Autowired
//    private HttpCookie httpCookie;

    @PostMapping("/register")//localhost:8080/api/auth/customer/register
    // trả về chuỗi string đăng ký thành công hay thất bại
    public ResponseEntity<?> registerKhachHang(
            @RequestBody KhachHangRequest khachHangRequest) {
        KhachHang khachHang = khachHangService.registerKhachHang(khachHangRequest);
        if (khachHang != null) {
            return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.<Object>builder()
                                                                     .code(200)
                                                                     .isOK(true)
                                                                     .data(khachHang)
                                                                     .message("register successfully")
                                                                     .build()
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseResponse.<Object>builder()
                                                                            .code(400)
                                                                            .isOK(false)
                                                                            .data(null)
                                                                            .message("Email đã tồn tại")
                                                                            .build()
            );
        }
    }

    @PostMapping("/login")//localhost:8080/api/auth/customer/login
    public ResponseEntity<?> loginKhachHang(
            @RequestBody LoginKhachHang loginKhachHang) {

        if (loginKhachHang == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(base.createBaseResponse(HttpStatus.BAD_REQUEST.value(), null, false, "Thông tin khách hàng trống"));
        }
        KhachHang khachHang = khachHangService.loginKhachHang(loginKhachHang.getEmail(), loginKhachHang.getMatKhau());
        if (khachHang == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(base.createBaseResponse(HttpStatus.BAD_REQUEST.value(), null, false, "Tài khoản hoặc mật khẩu không chính xác"));
        }
        appContext.getServletContext().setAttribute("khachHang", khachHang);// lưu thông tin khách hàng đăng nhập


        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.<Object>builder()
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


    @GetMapping("/forget-password")//localhost:8080/api/auth/customer/forget-password
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email) throws UnsupportedEncodingException, MessagingException {
        KhachHang khachHang = khachHangService.findByEmail(email);
        if (khachHang == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponse.<String>builder()
                                  .code(404)
                                  .isOK(false)
                                  .data(email)
                                  .message("Email not found")
                                  .build()
                    );
        }
        String password = utility.generateRandomString(6);
        try {
            log.info(khachHang.getEmail() + "__" + khachHang.getMatKhau());

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom("vuttph25379@fpt.edu.vnn", "Shop Gens-z");
            helper.setTo(email);

            String subject = "Shop GENS-Z - Đây là mật khẩu của bạn";

            String content = "<p>Xin Chào,<p>" +
                    "<p> Bạn đã yêu cầu lấy lại mật khẩu của mình.</p>" +
                    "<p> Đây là mật khẩu mới của bạn: " + password + "</p>" +
                    "<p> Bỏ qua email này nếu bạn nhớ mật khẩu của mình hoặc bạn chưa thực hiện yêu cầu";
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);

            KhachHangRequest khreq = KhachHangRequest.builder()
                    .trangThai(khachHang.getTrangThai())
                    .soDienThoai(khachHang.getSoDienThoai())
                    .email(khachHang.getEmail())
                    .hoTen(khachHang.getHoTen())
                    .id(khachHang.getId())
                    .matKhau(password )
                    .ngaySinh(khachHang.getNgaySinh())
                    .build();

            KhachHang khUpdate = khachHangService.update(khachHang.getId(), khreq);

            if (khUpdate != null) {

                return ResponseEntity.status(HttpStatus.OK)
                        .body(BaseResponse.<String>builder()
                                      .code(200)
                                      .isOK(true)
                                      .data(null)
                                      .message("Send email successfully")
                                      .build()
                        );
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(BaseResponse.<String>builder()
                                      .code(400)
                                      .isOK(false)
                                      .data(null)
                                      .message("error update kh")
                                      .build()
                        );
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponse.<String>builder()
                                  .code(400)
                                  .isOK(false)
                                  .data(null)
                                  .message("Send email error")
                                  .build()
                    );
        }
    }

    //get otp by email
    @GetMapping("/forget-password-otp")//localhost:8080/api/auth/customer/forget-password
    public ResponseEntity<?> getOtpForgetPass(@RequestParam("email") String email) throws UnsupportedEncodingException, MessagingException {
        KhachHang khachHang = khachHangService.findByEmail(email);
        if (khachHang == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(BaseResponse.<String>builder()
                                  .isOK(false)
                                  .data(email)
                                  .code(404)
                                  .message("Email not found")
                                  .build()
                    );
        }

        Integer otp = utility.generateOTP();
//        httpSession.setAttribute('');

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom("vuttph25379@fpt.edu.vnn", "Shop Gens-z");
            helper.setTo(email);

            String subject = "Shop GENS-Z - Đây là mật khẩu của bạn";

            String content = "<p>Xin Chào,<p>" +
                    "<p> Bạn đã yêu cầu lấy lại mật khẩu của mình.</p>" +
                    "<p> Đây là mã OTP của bạn: " + otp + "</p>" +
                    "<p> Bỏ qua email này nếu bạn nhớ mật khẩu của mình hoặc bạn chưa thực hiện yêu cầu";
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(BaseResponse.<String>builder()
                                  .code(200)
                                  .isOK(true)
                                  .data(null)
                                  .message("Send email successfully")
                                  .build()
                    );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(BaseResponse.<String>builder()
                                  .code(400)
                                  .isOK(false)
                                  .data(null)
                                  .message("Send email error")
                                  .build()
                    );
        }
    }

}
