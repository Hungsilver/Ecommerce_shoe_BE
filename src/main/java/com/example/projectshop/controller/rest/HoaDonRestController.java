package com.example.projectshop.controller.rest;

import com.example.projectshop.domain.HoaDon;
import com.example.projectshop.dto.hoadon.HoaDonChiTietRequest;
import com.example.projectshop.dto.hoadon.HoaDonRequest;
import com.example.projectshop.repository.HoaDonRepository;
import com.example.projectshop.service.IHoaDonService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/api/invoice")
public class HoaDonRestController {
    @Autowired
    private IHoaDonService hoaDonService;

    @Autowired
    private HoaDonRepository hoaDonRepo;

    @Autowired
    private HttpSession session;

    private String p_chu = "\\d+";

    @GetMapping()//localhost:8080/api/invoice
    public ResponseEntity<?> findAll(
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "sortField", required = false, defaultValue = "id") String sortField,
            @RequestParam(value = "isSortDesc", required = false, defaultValue = "false") Boolean isSortDesc,
            @RequestParam(value = "page", required = false, defaultValue = "1") String page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") String pageSize
    ) {
        if (!page.matches(p_chu) || !pageSize.matches(p_chu)) {
            return ResponseEntity.ok("*page || pageSize || status phải là số");
        }
        return ResponseEntity.ok(hoaDonService.findAll(status, keyword, sortField, isSortDesc, Integer.valueOf(page), Integer.valueOf(pageSize)));
    }

    @GetMapping("{id}")//localhost:8080/api/invoice/1
    public ResponseEntity<?> findById(@PathVariable("id") String id) {
        if (!id.matches(p_chu)) {
            return ResponseEntity.ok("*id hóa đơn phải là số");
        }
        return ResponseEntity.ok(hoaDonService.findById(Integer.valueOf(id)));
    }

    @GetMapping("/code/{ma}")//localhost:8080/api/invoice/code/abc
    public ResponseEntity<?> findByMa(@PathVariable("ma") String ma) {
        return ResponseEntity.ok(hoaDonService.findByMa(ma));
    }

    @PutMapping("{id}")//localhost:8080/api/invoice/1
    public ResponseEntity<?> update(
            @PathVariable("id") String id,
            @RequestBody HoaDonRequest hoaDonRequest) {
        if (!id.matches(p_chu)) {
            return ResponseEntity.ok("*id hóa đơn phải là số");
        }
        return ResponseEntity.ok(hoaDonService.update(Integer.valueOf(id), hoaDonRequest));
    }

    // start bán hàng tại quầy

    // Thanh toán hóa đơn
    @PostMapping("/shop/payments/{id}")//localhost:8080/api/invoice/shop/payments/1
    public ResponseEntity<?> shopCheckout(@PathVariable("id") String idHoaDon,
                                          @RequestBody HoaDonRequest hoaDonRequest) {
        if (!idHoaDon.matches(p_chu)) {
            return ResponseEntity.ok("*id hóa đơn phải là số");
        }
        return ResponseEntity.ok(hoaDonService.shopPayments(Integer.valueOf(idHoaDon), hoaDonRequest));
    }


    // Tạo hóa đơn chờ với id là idNhanVien
    @PostMapping("/shop/create/{id}")//localhost:8080/api/invoice/shop/create/1
    public ResponseEntity<?> shopCreateInvoice(@PathVariable("id") Integer idNhanVien) {
        return ResponseEntity.ok(hoaDonService.shopCreateInvoice(idNhanVien));
    }

    // Thêm sản phẩm vào hóa đơn chi tiết
    @PostMapping("/shop/add-product")//localhost:8080/api/invoice/shop/add-product
    public ResponseEntity<?> shopCreateInvoiceDetail(@RequestBody HoaDonChiTietRequest hoaDonChiTietRequest) {
        return ResponseEntity.ok(hoaDonService.shopCreateInvoiceDetail(hoaDonChiTietRequest));
    }

    // Xóa hóa đơn chi tiết với id của hóa đơn chi tiết
    //localhost:8080/api/invoice/shop/delete-invoie-detail/1
    @DeleteMapping("/shop/delete-invoice-detail/{id}")
    public ResponseEntity<?> shopCreateInvoiceDetail(@PathVariable("id") String id) {
        if (!id.matches(p_chu)) {
            return ResponseEntity.ok("*id phải là số");
        }
        hoaDonService.shopDeleteInvoiceDetail(Integer.valueOf(id));
        return ResponseEntity.ok().build();
    }

    // cập nhật số lượng sản phẩm trong hóa đơn chi tiết
    //localhost:8080/api/invoice/shop/update-invoie-detail
    @PutMapping("/shop/update-invoice-detail/{idHDCT}")
    public ResponseEntity<?> shopUpdateInvoiceDetail(@PathVariable Integer idHDCT,
                                                     @RequestBody HoaDonChiTietRequest hoaDonChiTietRequest) {
        return ResponseEntity.ok(hoaDonService.shopUpdateInvoiceDetail(idHDCT, hoaDonChiTietRequest));
    }
    // end bán hàng tại quầy


    // start bán hàng online

    // thanh toán online
    //localhost:8080/api/invoice/online/payment
    @PostMapping("/online/payment")
    public ResponseEntity<?> onlineCheckout(@RequestBody HoaDonRequest hoaDonRequest) throws UnsupportedEncodingException {
        if (hoaDonRequest.getPhuongThucThanhToan() == 0) {
            return ResponseEntity.ok(hoaDonService.onlinePayment(hoaDonRequest));
        } else {
            return ResponseEntity.ok(hoaDonService.vnPayService(hoaDonRequest));
        }
    }


    //localhost:8080/api/invoice/online/payment-callback
    @GetMapping("/online/payment-callback")
    public void paymentCallback(
            @RequestParam(value = "vnp_ResponseCode") String responseCode,
            @RequestParam(value = "vnp_TxnRef") String maHoaDon,
            HttpServletResponse response)
            throws IOException {

        if (responseCode.equals("00") && maHoaDon != null) {
            hoaDonService.vnPayment(maHoaDon);
            response.sendRedirect("http://localhost:4200/"); //đường dẫn trang quản lý đơn hàng của user bên angular
            //vd: https://localhost:4200/user/...
        }else{
            hoaDonService.delete(maHoaDon);
            response.sendRedirect("http://localhost:4200/");//đường dẫn trang thanh toán đơn hàng của user bên angular
            //vd: https://localhost:4200/user/...
        }
    }


    // cập nhật trạng thái hóa đơn với id của hóa đơn
    //localhost:8080/api/invoice/update-status?id=1&status=2
    @GetMapping("/update-status")
    public ResponseEntity<?> choVanChuyen(@RequestParam(value = "id") Integer id,
                                          @RequestParam(value = "status") Integer status) {
        return ResponseEntity.ok(hoaDonService.updateStatus(id, status));
    }


    // xuất hóa đơn file pdf với id hóa đơn
    //localhost:8080/api/invoice/export/1
    @GetMapping("/export/{id}")
    public ResponseEntity<?> exportPDF(HttpServletResponse response,
                                       @PathVariable("id") String id) throws IOException {
        if (!id.matches(p_chu)) {
            return ResponseEntity.ok("*id hóa đơn phải là số");
        }
        response.setContentType("application/pdf");
        response.setCharacterEncoding("UTF-8");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormat.format(new Date());

        String headerKey = "Content-Dispostion";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";

        response.setHeader(headerKey, headerValue);
        hoaDonService.exportPDF(response, Integer.valueOf(id));
        return ResponseEntity.ok().build();
    }


}
