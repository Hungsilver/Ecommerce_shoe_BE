package com.example.projectshop.controller.rest;

import com.example.projectshop.domain.KhachHang;
import com.example.projectshop.dto.BaseResponse;
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
import org.springframework.web.context.WebApplicationContext;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/customer")
public class KhachHangRestController {

    @Autowired
    @Lazy
    private IKhachHangService khachHangService;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private WebApplicationContext appContext;

    private String p_chu = "\\d+";

    @GetMapping()//localhost:8080/api/customer
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sortField", required = false, defaultValue = "id") String sortField,
            @RequestParam(value = "isSortDesc", required = false, defaultValue = "false") Boolean isSortDesc,
            @RequestParam(value = "keyword", required = false) String keyword
    ) {
        return ResponseEntity.ok(khachHangService.findAll(page, pageSize, sortField, isSortDesc, keyword));
    }

//    @PostMapping()//localhost:8080/api/customer
//    public ResponseEntity<?> create(
//            @RequestBody KhachHangRequest khachHangRequest
//    ) {
//        return ResponseEntity.ok(khachHangService.create(khachHangRequest));
//    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody KhachHangRequest khachHangRequest) {
        return ResponseEntity.ok(khachHangService.create(khachHangRequest));
    }


    @GetMapping("{id}")//localhost:8080/api/customer/1
    public ResponseEntity<?> findById(@PathVariable String id) {
        if (!id.matches(p_chu)) {
            return ResponseEntity.ok("*id khách hàng phải là số");
        }
        return ResponseEntity.ok(khachHangService.findById(Integer.valueOf(id)));

    }
        @GetMapping("/checkEmail/{email}")
        public  boolean checkEmail(@PathVariable String email){
            KhachHang khne = khachHangService.findByEmail(email);
        return khne !=null;
        }
        @GetMapping("/checkPhoneNumberExists/{sdt}")
        public  boolean checkSDT(@PathVariable String sdt){
        KhachHang kh = khachHangService.findBySdt(sdt);
        return  kh !=null;
        }

    @PutMapping("{id}")//localhost:8080/api/customer/1
    public ResponseEntity<?> update(
            @RequestBody KhachHangRequest khachHangRequest,
            @PathVariable("id") String id
    ) {
        if (!id.matches(p_chu)) {
            return ResponseEntity.ok("*id khách hàng phải là số");
        }
        KhachHang kh = khachHangService.update(Integer.valueOf(id), khachHangRequest);
        return kh != null ? ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.builder()
                              .code(200)
                              .data(kh)
                              .isOK(true)
                              .message("update khach hang thanh cong")
                              .build()
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(BaseResponse.builder()
                                      .data(null)
                                      .code(404)
                                      .isOK(false)
                                      .message("update khach hang that bai")
                                      .build()
                        );
    }

    @DeleteMapping("{id}")//localhost:8080/api/customer/1
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        if (!id.matches(p_chu)) {
            return ResponseEntity.ok("*id khách hàng phải là số");
        }
        return ResponseEntity.ok(khachHangService.delete(Integer.valueOf(id)));
    }

    @GetMapping("/excel/export")//localhost:8080/api/customer/excel/export
    public ResponseEntity<?> exportExcel() {
        return ResponseEntity.ok(khachHangService.exportExcel());
    }

    @GetMapping("/check-duplicate")
     public ResponseEntity<?>checkDuplicate( @RequestParam String field,
                                             @RequestParam String value){
        boolean isDuplicate = false;

        if ("soDienThoai".equals(field)) {
            isDuplicate = khachHangService.isSoDienThoaiExists(value);
        } else if ("email".equals(field)) {
            isDuplicate = khachHangService.isEmailExists(value);
        }

        return ResponseEntity.ok(isDuplicate);

    }

    @PutMapping("v1/{id}")//localhost:8080/api/customer/1
    public ResponseEntity<?> updateKH(
            @RequestBody KhachHangRequest khachHangRequest,
            @PathVariable("id") String id
    ) {
        if (!id.matches(p_chu)) {
            return ResponseEntity.ok("*id khách hàng phải là số");
        }
        KhachHang khLSession = (KhachHang) appContext.getServletContext().getAttribute("khachHang");
        KhachHang kh = khachHangService.findById(khLSession.getId());
        kh.setHoTen(khachHangRequest.getHoTen());
        kh.setNgaySinh(khachHangRequest.getNgaySinh());
        kh.setSoDienThoai(khachHangRequest.getSoDienThoai());
        KhachHang khUpdate = khachHangService.updateKHv1(Integer.valueOf(id), kh);

        return khUpdate != null ? ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.builder()
                              .code(200)
                              .data(kh)
                              .isOK(true)
                              .message("update khach hang thanh cong")
                              .build()
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(BaseResponse.builder()
                                      .data(null)
                                      .code(404)
                                      .isOK(false)
                                      .message("update khach hang that bai")
                                      .build()
                        );
    }

//    @PostMapping("/register")//localhost:8080/api/customer/register
//    // trả về chuỗi string đăng ký thành công hay thất bại
//    public ResponseEntity<String> registerKhachHang(@RequestBody KhachHangRequest khachHangRequest) {
//        try {
//            khachHangService.registerKhachHang(khachHangRequest);
//            return ResponseEntity.ok("Đăng Ký thành công!");
//        }catch (UnauthorizedException e){
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
//        }
//    }
//
//    @PostMapping("/login")//localhost:8080/api/customer/login
//    public ResponseEntity<?> loginKhachHang(@RequestBody KhachHangRequest khachHangRequest) {
//        try {
//            KhachHang khachHang = khachHangService.loginKhachHang(khachHangRequest.getEmail(), khachHangRequest.getMatKhau());
//            httpSession.setAttribute("khachHang", khachHang);
//            return ResponseEntity.ok(khachHang); // đăng ký thành công trả về thông tin của khách hàng
//        } catch (UnauthorizedException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage()); // trả về thông báo lỗi
//        }
//    }
//
//    @GetMapping("/check-login-status")//localhost:8080/api/customer/check-login-status
//    public ResponseEntity<String> checkLoginStatus(){
//        KhachHang khachHang= (KhachHang) httpSession.getAttribute("khachHang");
//
//        if (khachHang != null) {
//            return ResponseEntity.ok("khách hàng đã đăng nhập");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("khách hàng chưa đăng nhập");
//        }
//    }
//
//
//    @GetMapping("/checkSession")//localhost:8080/api/customer/checkSession
//    public ResponseEntity<String> checkSession(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//
//        if (session != null) {
//            // Lấy thông tin khách hàng từ Session
//            KhachHang khachHang = (KhachHang) session.getAttribute("khachHang");
//
//            if (khachHang != null) {
//                // Khách hàng đã đăng nhập
//                return ResponseEntity.ok("Khách hàng đã đăng nhập: " + khachHang.getHoTen());
//            } else {
//                // Session tồn tại nhưng không có thông tin khách hàng
//                return ResponseEntity.ok("Khách hàng chưa đăng nhập");
//            }
//        } else {
//            // Session không tồn tại
//            return ResponseEntity.ok("Khách hàng chưa đăng nhập");
//        }
//    }



}