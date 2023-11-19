package com.example.projectshop.controller.rest;


import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.GioHang;
import com.example.projectshop.domain.GioHangChiTiet;
import com.example.projectshop.domain.KhachHang;
import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamRequest;
import com.example.projectshop.repository.ChiTietSanPhamRepository;
import com.example.projectshop.repository.GioHangChiTietRepository;
import com.example.projectshop.service.*;
import com.example.projectshop.service.impl.ChiTietSanPhamServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/api/cart-detail")
public class OnlineShopRest {

    @Autowired
    private IGioHangCTService iGioHangCTService;

    @Autowired
    private IKhachHangService khachHangService;
    @Autowired
    private IGioHangService gioHangService;

    @Autowired
    private ChiTietSanPhamServiceImpl ctspservice;

    @Autowired
    private IChiTietSanPhamService ctspService;

    //test

    @GetMapping
    public ResponseEntity<?> loadGioHangChiTiet(@PageableDefault(size = 1) Pageable pageable) {

        return ResponseEntity.ok(iGioHangCTService.getall(pageable));
    }


    KhachHang kh;

    // them san pham vao gio hang online
    @PostMapping("/addsp/{id}")
    public ResponseEntity<?> addSPVaoGio(HttpServletRequest request,
                                         @PathVariable("id") Integer idctsp,
                                         @RequestParam("quality") int soluongthem) {
        try {
            System.out.println("them san pham ");
            HttpSession session = request.getSession(false);
            if (session != null) {
                kh = (KhachHang) session.getAttribute("khachHang");

                if (kh != null && idctsp != null) {
                    iGioHangCTService.onlineCart(kh, idctsp, soluongthem);

                } else {
                    return ResponseEntity.ok("khach hang chua dang nhap");
                }

            } else {
                return ResponseEntity.ok("session ko có tt kh");
            }
        } catch (Exception e) {
            System.out.println("day la loi:" + e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }

    // xoa theo id cua chi tiet san pham
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> remove(HttpServletRequest request, @PathVariable("id") Integer idctsp) {
        System.out.println("khach hang " + kh);
        HttpSession session = request.getSession();
        kh = (KhachHang) session.getAttribute("khachHang");
        if (kh != null) {
            iGioHangCTService.remove(kh, idctsp);

        }

        return ResponseEntity.ok("xoa thanh cong");
    }


    KhachHang khload;

    // load gio hang chi tiet  cua khach hang trong session
    // anh em dang nhap truoc moi load gio hang nhe
    // dang nhap ở trong khachhangRestController
    @GetMapping("/ofcart")
    public ResponseEntity<?> getGHCTByGiohangID(HttpServletRequest request) {
        HttpSession session = request.getSession();

        khload = (KhachHang) session.getAttribute("khachHang");

        if (khload != null) {
            GioHang gh = khload.getGiohang();
            List<GioHangChiTiet> dsgh = iGioHangCTService.GetGioHangCTByIdGioHang(gh.getId());
            return ResponseEntity.ok(dsgh);
        } else {
            return ResponseEntity.ok("ban chua dang nhap tk");
        }

    }

    // luu y khi su dung
    // phai load giở hàng trước mới cộng trừ được
    // tăng số lượng lên +1 sản phẩm trong giỏ
    @PutMapping("/increase/{id}")
    public ResponseEntity<?> increase(HttpServletRequest request,
                                      @PathVariable("id") Integer id) {
        try {
//        HttpSession session = request.getSession();
//        khload = (KhachHang) session.getAttribute("khachHang");
            if (khload != null) {
                iGioHangCTService.increase(khload, id);
            } else {
                return ResponseEntity.ok("ban chua dang nhap");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    // tru so luong -1 san pham trong gio
    @PutMapping("/reduce/{id}")
    public ResponseEntity<?> reduce(HttpServletRequest request,
                                    @PathVariable("id") Integer id) {
//        HttpSession session = request.getSession();
//        kh = (KhachHang) session.getAttribute("khachHang");
        try {
            if (khload != null) {
                iGioHangCTService.reduce(khload, id);
            } else {
                return ResponseEntity.ok("ban chua dang nhap");
            }

        } catch (Exception e) {
            System.out.println("loi:" + e.getMessage());

        }
        return ResponseEntity.ok().build();
    }


}
