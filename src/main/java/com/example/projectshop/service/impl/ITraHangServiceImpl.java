package com.example.projectshop.service.impl;

import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.HoaDon;
import com.example.projectshop.domain.KhachHang;
import com.example.projectshop.domain.TraHang;
import com.example.projectshop.domain.TraHangChiTiet;
import com.example.projectshop.dto.trahang.TraHangChiTietRequest;
import com.example.projectshop.dto.trahang.TraHangRequest;
import com.example.projectshop.dto.trahang.UpdateCTSP;
import com.example.projectshop.repository.ChiTietSanPhamRepository;
import com.example.projectshop.repository.HoaDonChiTietRepository;
import com.example.projectshop.repository.HoaDonRepository;
import com.example.projectshop.repository.TraHangChiTietRepository;
import com.example.projectshop.repository.TraHangRepository;
import com.example.projectshop.service.IHoaDonService;
import com.example.projectshop.service.ITraHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class ITraHangServiceImpl implements ITraHangService {
    @Autowired
    private TraHangRepository traHangRepo;

    @Autowired
    private TraHangChiTietRepository traHangChiTietRepo;

    @Autowired
    private HoaDonRepository hoaDonRepo;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepo;

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepo;

    @Autowired
    private WebApplicationContext appContext;

    // lấy ra ngày hiện tại
    private LocalDate curruntDate = LocalDate.now();

    @Override
    public List<TraHang> findAll(Integer status, String keyword, String sortField, Boolean isSortDesc, Integer page, Integer pageSize) {
//        Sort sort = Sort.by(isSortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
//        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, pageSize, sort);
//        return traHangRepo.findAll(status, keyword, pageable);
        return traHangRepo.findAll();
    }

    @Override
    public List<TraHang> findAllByTrangThai(Integer trangThai) {
        return traHangRepo.findAllByTrangThai(trangThai);
    }

    @Override
    public List<TraHang> findByIdHoaDon(Integer idHoaDon) {
        return traHangRepo.findByIdHoaDon(idHoaDon);
    }

    @Override
    public TraHang findById(Integer id) {
        return traHangRepo.findById(id).get();
    }

    @Override
    public List<TraHang> findByIdKhachHang(  ) {
        KhachHang khachHang = (KhachHang) appContext.getServletContext().getAttribute("khachHang");
        return traHangRepo.findByIdKhachHang(khachHang.getId());
    }

    @Override
    public TraHang add(TraHangRequest traHangRequest) {
        TraHang traHang = TraHang.builder()
                .id(null)
                .lyDo(traHangRequest.getLyDo())
                .ngayTao(Date.valueOf(curruntDate))
                .ngayCapNhat(null)
                .tienTraKhach(traHangRequest.getTienTraKhach())
                .trangThai(0)
                .hoaDon(hoaDonRepo.findById(traHangRequest.getIdHoaDon()).get())
                .build();
        TraHang traHang1 = traHangRepo.save(traHang);
        for (TraHangChiTietRequest x: traHangRequest.getTraHangChiTietRequests()){
            TraHangChiTiet traHangChiTiet = TraHangChiTiet.builder()
                    .id(null)
                    .traHang(traHang1)
                    .hoaDonChiTiet(hoaDonChiTietRepo.findById(x.getIdHoaDonChiTiet()).get())
                    .soLuong(x.getSoLuong())
                    .donGia(x.getDonGia())
                    .build();
            traHangChiTietRepo.save(traHangChiTiet);
        }
        return traHang1;
    }

    @Override
    public TraHang shopAdd(TraHangRequest traHangRequest) {
        TraHang traHang = TraHang.builder()
                .id(null)
                .lyDo(traHangRequest.getLyDo())
                .ngayTao(Date.valueOf(curruntDate))
                .ngayCapNhat(null)
                .tienTraKhach(traHangRequest.getTienTraKhach())
                .trangThai(0)
                .hoaDon(hoaDonRepo.findById(traHangRequest.getIdHoaDon()).get())
                .build();
        TraHang traHang1 = traHangRepo.save(traHang);
        for (TraHangChiTietRequest x: traHangRequest.getTraHangChiTietRequests()){
            TraHangChiTiet traHangChiTiet = TraHangChiTiet.builder()
                    .id(null)
                    .traHang(traHang1)
                    .hoaDonChiTiet(hoaDonChiTietRepo.findById(x.getIdHoaDonChiTiet()).get())
                    .soLuong(x.getSoLuong())
                    .donGia(x.getDonGia())
                    .build();
            traHangChiTietRepo.save(traHangChiTiet);
        }
        HoaDon hoaDon = hoaDonRepo.findById(traHangRequest.getIdHoaDon()).get();
        BigDecimal tienTraKhach = hoaDon.getTongTienSauGiam().subtract(traHangRequest.getTienTraKhach());
        hoaDon.setTongTienSauGiam(tienTraKhach.add(hoaDon.getPhiVanChuyen()));
        hoaDonRepo.save(hoaDon);
        return traHang1;
    }

    @Override
    public void updateQuantity(List<UpdateCTSP> updateCTSPS) {

        for (UpdateCTSP x: updateCTSPS){
            ChiTietSanPham chiTietSanPham = this.chiTietSanPhamRepo.findById(x.getIdChiTietSanPham()).get();
            chiTietSanPham.setSoLuong(chiTietSanPham.getSoLuong() + x.getSoLuong());
            this.chiTietSanPhamRepo.save(chiTietSanPham);
        }

    }

    @Override
    public TraHangChiTiet deleteTHCT(Integer id) {
        TraHangChiTiet traHangChiTiet = traHangChiTietRepo.findById(id).get();
        traHangChiTietRepo.delete(traHangChiTiet);
        return traHangChiTiet;
    }

    @Override
    public TraHangChiTiet updateQuantityTHCT(Integer id, Integer soLuong) {
        TraHangChiTiet traHangChiTiet = traHangChiTietRepo.findById(id).get();
        traHangChiTiet.setSoLuong(soLuong);
        return traHangChiTietRepo.save(traHangChiTiet);
    }

    @Override
    public TraHang updatePayment(Integer id, BigDecimal tongTien) {
        TraHang traHang = traHangRepo.findById(id).get();
        traHang.setTienTraKhach(tongTien);
        return traHangRepo.save(traHang);
    }

    @Override
    public TraHangChiTiet addTHCT(TraHangChiTietRequest traHangChiTietRequest) {
        TraHangChiTiet traHangChiTiet = TraHangChiTiet.builder()
                .id(null)
                .traHang(traHangRepo.findById(traHangChiTietRequest.getIdTraHang()).get())
                .hoaDonChiTiet(hoaDonChiTietRepo.findById(traHangChiTietRequest.getIdHoaDonChiTiet()).get())
                .soLuong(traHangChiTietRequest.getSoLuong())
                .donGia(hoaDonChiTietRepo.findById(traHangChiTietRequest.getIdHoaDonChiTiet()).get().getDonGia())
                .build();
        return traHangChiTietRepo.save(traHangChiTiet);
    }

    @Override
    public TraHang updateGhiChu(Integer id, String ghiChu) {
        TraHang traHang = traHangRepo.findById(id).get();
        traHang.setLyDo(ghiChu);
        traHang.setNgayCapNhat(Date.valueOf(curruntDate));
        traHang.setTrangThai(1);
        return traHangRepo.save(traHang);
    }

    @Override
    public TraHang updateStatus(Integer id, Integer trangThai) {
        TraHang traHang = traHangRepo.findById(id).get();
        traHang.setNgayCapNhat(Date.valueOf(curruntDate));
        traHang.setTrangThai(trangThai);
        return traHangRepo.save(traHang);
    }
}
