package com.example.projectshop.controller.rest;

import com.example.projectshop.dto.hoadon.HoaDonChiTietRequest;
import com.example.projectshop.dto.hoadon.HoaDonRequest;
import com.example.projectshop.service.IHoaDonService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/api/invoice")
public class HoaDonRestController {
    @Autowired
    private IHoaDonService hoaDonService;

    @GetMapping()
    public ResponseEntity<?> findAll(
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "sortField", required = false, defaultValue = "id") String sortField,
            @RequestParam(value = "isSortDesc", required = false, defaultValue = "false") Boolean isSortDesc,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        return ResponseEntity.ok(hoaDonService.findAll(status, keyword, sortField, isSortDesc, page, pageSize));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(hoaDonService.findById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(
            @PathVariable("id") Integer id,
            @RequestBody HoaDonRequest hoaDonRequest) {
        return ResponseEntity.ok(hoaDonService.update(id, hoaDonRequest));
    }

    // start bán hàng tại quầy
    @PostMapping("/shop/payments/{id}")
    public ResponseEntity<?> shopCheckout(@PathVariable("id") Integer idHoaDon,
                                          @RequestBody HoaDonRequest hoaDonRequest) {
        return ResponseEntity.ok(hoaDonService.shopPayments(idHoaDon, hoaDonRequest));
    }

    @PostMapping("/shop/create/{id}")
    public ResponseEntity<?> shopCreateInvoice(@PathVariable("id") Integer idNhanVien) {
        return ResponseEntity.ok(hoaDonService.shopCreateInvoice(idNhanVien));
    }

    @PostMapping("/shop/add-product")
    public ResponseEntity<?> shopCreateInvoiceDetail(@RequestBody HoaDonChiTietRequest hoaDonChiTietRequest) {
        return ResponseEntity.ok(hoaDonService.shopCreateInvoiceDetail(hoaDonChiTietRequest));
    }

    @DeleteMapping("/shop/delete-invoice-detail/{id}")
    public ResponseEntity<?> shopCreateInvoiceDetail(@PathVariable("id") Integer id) {
        hoaDonService.shopDeleteInvoiceDetail(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/shop/update-invoice-detail")
    public ResponseEntity<?> shopUpdateInvoiceDetail(
            @RequestParam(value = "id", required = false) Integer id,
            @RequestParam(value = "soLuong", required = false) Integer soLuong
    ) {
        return ResponseEntity.ok(hoaDonService.shopUpdateInvoiceDetail(id, soLuong));
    }
    // end bán hàng tại quầy

    // start bán hàng online
    @PostMapping("/online/payments")
    public ResponseEntity<?> onlineCheckout(@RequestBody HoaDonRequest hoaDonRequest) {
        return ResponseEntity.ok(hoaDonService.onlinePayments(hoaDonRequest));
    }

    @PostMapping("/cho-van-chuyen/{id}")
    public ResponseEntity<?> choVanChuyen(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(hoaDonService.choVanChuyen(id));
    }

    @PostMapping("/dang-giao/{id}")
    public ResponseEntity<?> dangGiao(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(hoaDonService.dangGiao(id));
    }

    @PostMapping("/da-giao/{id}")
    public ResponseEntity<?> daGiao(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(hoaDonService.daGiao(id));
    }

    @PostMapping("/da-huy/{id}")
    public ResponseEntity<?> daHuy(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(hoaDonService.daHuy(id));
    }

    @PostMapping("/tra-hang/{id}")
    public ResponseEntity<?> traHang(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(hoaDonService.traHang(id));
    }

    @GetMapping("/export/{id}")
    public void exportPDF(HttpServletResponse response,
                          @PathVariable("id")Integer id) throws IOException {
        response.setContentType("application/pdf");
        response.setCharacterEncoding("UTF-8");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormat.format(new Date());

        String headerKey = "Content-Dispostion";
        String headerValue = "attachment; filename=pdf_"+currentDateTime+".pdf";

        response.setHeader(headerKey,headerValue);
        hoaDonService.exportPDF(response, id);
    }


}
