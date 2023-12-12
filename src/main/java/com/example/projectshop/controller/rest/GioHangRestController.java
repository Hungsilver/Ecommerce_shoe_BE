package com.example.projectshop.controller.rest;


import com.example.projectshop.domain.GioHang;
import com.example.projectshop.domain.GioHangChiTiet;
import com.example.projectshop.domain.KhachHang;
import com.example.projectshop.dto.BaseResponse;
import com.example.projectshop.service.IChiTietSanPhamService;
import com.example.projectshop.service.IGioHangCTService;
import com.example.projectshop.service.IGioHangService;
import com.example.projectshop.service.IKhachHangService;
import com.example.projectshop.service.impl.ChiTietSanPhamServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/api/cart-detail")
public class GioHangRestController {

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
    @GetMapping("/addToCart") //http://localhost:8080/api/cart-detail/addToCart/id?quality=soluongthem
    public ResponseEntity<?> addSPVaoGio(HttpServletRequest request,
                                         @RequestParam("id") Integer idctsp,
                                         @RequestParam("quantity") Integer sl) {
        try {
            System.out.println("them san pham ");
            HttpSession session = request.getSession(false);
            if (session != null) {
                kh = (KhachHang) session.getAttribute("khachHang");

                if (kh != null && idctsp != null) {
                    GioHangChiTiet gh = iGioHangCTService.onlineCart(kh, idctsp, sl);
                    return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.<GioHangChiTiet>builder()
                            .code(200)
                            .isOK(true)
                            .data(gh)
                            .message("Thêm thành công")
                            .build()
                    );
                } else {
                    return ResponseEntity.ok("khach hang chua dang nhap");
                }

            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.<GioHangChiTiet>builder()
                        .code(400)
                        .isOK(false)
                        .data(null)
                        .message("Không tìm thấy khách hàng")
                        .build()
                );
            }
        } catch (Exception e) {
            System.out.println("day la loi:" + e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }

    // xoa theo id cua chi tiet san pham
    @DeleteMapping("/remove/{id}") //http://localhost:8080/api/cart-detail/remove/idctsp
    public ResponseEntity<?> remove(HttpServletRequest request, @PathVariable("id") Integer idctsp) {
        System.out.println("khach hang " + kh);
        HttpSession session = request.getSession();
        kh = (KhachHang) session.getAttribute("khachHang");
        if (kh != null && idctsp != null) {
            iGioHangCTService.remove(kh, idctsp);

        }

        return ResponseEntity.ok("xoa thanh cong");
    }


    KhachHang khload;

    // load gio hang chi tiet  cua khach hang trong session
    // anh em dang nhap truoc moi load gio hang nhe
    // dang nhap ở hàm loginkhachhang trong khachhangRestController
    // dang nhap xong thì chạy đoạn code này trước để có thể giảm hoặc tăng số lượng được
    @GetMapping("/ofcart")  //http://localhost:8080/api/cart-detail/ofcart
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
    @PutMapping("/increase/{id}") //http://localhost:8080/api/cart-detail/increase/id
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
    @PutMapping("/reduce/{id}") //http://localhost:8080/api/cart-detail/reduce/id
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
