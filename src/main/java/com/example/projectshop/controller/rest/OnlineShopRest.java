package com.example.projectshop.controller.rest;


import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.GioHang;
import com.example.projectshop.domain.GioHangChiTiet;
import com.example.projectshop.domain.KhachHang;
import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamRequest;
import com.example.projectshop.repository.GioHangChiTietRepository;
import com.example.projectshop.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    private IChiTietSanPhamService ctspService;

    //test
    @Autowired
    private GioHangChiTietRepository thu;

    @GetMapping
    public ResponseEntity<?> loadGioHangChiTiet(@PageableDefault(size = 1) Pageable pageable) {

        return ResponseEntity.ok(iGioHangCTService.getall(pageable));
    }

    int check = 0;
        // truyen id khach hang (TH khach đã đăng nhập và có 1 Cart trong DB) vao
    @PostMapping("/addcart/{id}")
    public ResponseEntity<?> themsp(@PathVariable("id") Integer idkh,
                                    @RequestBody ChiTietSanPhamRequest ctspRequest,
                                    @RequestParam("quality") int soluongthem) {
//        ChiTietSanPham ctspentity = ObjectMapperUtils.map(chiTietSanPhamRequest, ChiTietSanPham.class);
        System.out.println("chay vao 1");
        Optional<KhachHang> kh = khachHangService.findById(idkh);
        GioHang gh = kh.get().getGiohang();
        System.out.println(" id gio hang" + gh.getId());

        List<GioHangChiTiet> listgiohangct = gh.getListGioHangChiTiet();
        System.out.println("id gh chi tiet cua GH" + gh.getId() + "la" + listgiohangct);

        for (GioHangChiTiet x : listgiohangct) {
            System.out.println("chay vao 2");
            // Truong Hop  co sp trong ghct
            if (x.getChiTietSanPham().getId().equals(ctspRequest.getId())) {
                System.out.println("chay vao if");
                x.setSoLuong(x.getSoLuong() + soluongthem);
                BigDecimal giamoi = x.getGiaBan().multiply(new BigDecimal(soluongthem));
                x.setGiaBan(giamoi);
//                x.setGioHang(gh);
                iGioHangCTService.update(x);
                check = 1;
            }
        }
        // Truong Hop chua co SP trong ghct(Da co GioHang)
        if (check == 0) {
            iGioHangCTService.addSP(ctspRequest, soluongthem, gh.getId());
        }
//        gioHangService.add(gh);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ofcart/{id}")
    public ResponseEntity<?> getGHCTByGiohangID(@PathVariable("id") Integer id) {
        List<GioHangChiTiet> dsgh = iGioHangCTService.GetGioHangCTByIdGioHang(id);
        return ResponseEntity.ok(dsgh);
    }


}
