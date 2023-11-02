package com.example.projectshop.service.impl;

import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.HoaDon;
import com.example.projectshop.domain.HoaDonChiTiet;
import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamRequest;
import com.example.projectshop.dto.hoadon.HoaDonChiTietRequest;
import com.example.projectshop.dto.hoadon.HoaDonRequest;
import com.example.projectshop.repository.ChiTietSanPhamRepository;
import com.example.projectshop.repository.HoaDonChiTietRepository;
import com.example.projectshop.repository.HoaDonRepository;
import com.example.projectshop.service.HoaDonService;
import com.example.projectshop.service.IChiTietSanPhamService;
import com.example.projectshop.service.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class HoaDonServiceImpl implements HoaDonService {
    @Autowired
    private HoaDonRepository hoaDonRepo;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepo;

    @Autowired
    private IChiTietSanPhamService chiTietSanPhamService;

    @Override
    public Page<HoaDon> findAll(Integer status, String keyword,String sortField,Boolean isSortDesc, Integer page, Integer pageSize) {
        Sort sort = Sort.by(isSortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, pageSize, sort);
        return hoaDonRepo.findAll(status,keyword,pageable);
    }

    @Override
    public Optional<HoaDon> findById(Integer id) {
        return hoaDonRepo.findById(id);
    }

    @Override
    public HoaDon shopCheckout(HoaDonRequest hoaDonRequest) {
        // lấy ra ngày hiện tại
        LocalDate curruntDate = LocalDate.now();
        System.out.println(curruntDate);

        // start add hoadon
        HoaDon hoaDon = new HoaDon();
        hoaDon.setId(null);
        hoaDon.setTenKhachHang(hoaDonRequest.getTenKhachHang());
        hoaDon.setSoDienThoai(hoaDonRequest.getSoDienThoai());
        hoaDon.setDiaChi(hoaDonRequest.getDiaChi());
        hoaDon.setPhuongXa(hoaDonRequest.getPhuongXa());
        hoaDon.setQuanHuyen(hoaDonRequest.getQuanHuyen());
        hoaDon.setTinhThanh(hoaDonRequest.getTinhThanh());
        hoaDon.setNgayTao(Date.valueOf(curruntDate));
        hoaDon.setNgayCapNhat(null);
        hoaDon.setTongTien(hoaDonRequest.getTongTien());
        hoaDon.setPhiVanChuyen(hoaDonRequest.getPhiVanChuyen());
        hoaDon.setPhuongThucThanhToan(hoaDonRequest.getPhuongThucThanhToan());
        hoaDon.setTrangThai(0);
        hoaDon.setPhieuGiamGia(hoaDonRequest.getPhieuGiamGiaRequest());
        hoaDon.setKhachHang(hoaDonRequest.getKhachHangRequest());
        hoaDon.setNhanVien(hoaDonRequest.getNhanVien());
        hoaDonRepo.save(hoaDon);
        // end add hoadon
        for (HoaDonChiTietRequest x: hoaDonRequest.getHoaDonChiTietRequests()){
            // start add hoadonchitiet
            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
            hoaDonChiTiet.setId(null);
            hoaDonChiTiet.setDonGia(x.getDonGia());
            hoaDonChiTiet.setSoLuong(x.getSoLuong());
            hoaDonChiTiet.setHoaDon(hoaDon);
            hoaDonChiTiet.setChiTietSanPham(ObjectMapperUtils.map(x.getChiTietSanPhamRequest(), ChiTietSanPham.class));
            hoaDonChiTietRepo.save(hoaDonChiTiet);
            // and add hoadonchitiet

            // start update chitietsanpham
            ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findById(x.getChiTietSanPhamRequest().getId()).get();
            chiTietSanPham.setSoLuong(chiTietSanPham.getSoLuong() - x.getSoLuong());
            chiTietSanPhamService.update(x.getChiTietSanPhamRequest().getId(),ObjectMapperUtils.map(chiTietSanPham, ChiTietSanPhamRequest.class));
        }
        return hoaDon;
    }


    @Override
    public HoaDon onlineCheckout(HoaDonRequest hoaDonRequest) {
        // lấy ra ngày hiện tại
        LocalDate curruntDate = LocalDate.now();
        System.out.println(curruntDate);

        // start add hoadon
        HoaDon hoaDon = new HoaDon();
        hoaDon.setId(null);
        hoaDon.setTenKhachHang(hoaDonRequest.getTenKhachHang());
        hoaDon.setSoDienThoai(hoaDonRequest.getSoDienThoai());
        hoaDon.setDiaChi(hoaDonRequest.getDiaChi());
        hoaDon.setPhuongXa(hoaDonRequest.getPhuongXa());
        hoaDon.setQuanHuyen(hoaDonRequest.getQuanHuyen());
        hoaDon.setTinhThanh(hoaDonRequest.getTinhThanh());
        hoaDon.setNgayTao(Date.valueOf(curruntDate));
        hoaDon.setNgayCapNhat(null);
        hoaDon.setTongTien(hoaDonRequest.getTongTien());
        hoaDon.setPhiVanChuyen(hoaDonRequest.getPhiVanChuyen());
        hoaDon.setPhuongThucThanhToan(hoaDonRequest.getPhuongThucThanhToan());
        hoaDon.setTrangThai(1);
        hoaDon.setPhieuGiamGia(hoaDonRequest.getPhieuGiamGiaRequest());
        hoaDon.setKhachHang(hoaDonRequest.getKhachHangRequest());
        hoaDon.setNhanVien(hoaDonRequest.getNhanVien());
        hoaDonRepo.save(hoaDon);
        // end add hoadon
        for (HoaDonChiTietRequest x: hoaDonRequest.getHoaDonChiTietRequests()){
            // start add hoadonchitiet
            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
            hoaDonChiTiet.setId(null);
            hoaDonChiTiet.setDonGia(x.getDonGia());
            hoaDonChiTiet.setSoLuong(x.getSoLuong());
            hoaDonChiTiet.setHoaDon(hoaDon);
            hoaDonChiTiet.setChiTietSanPham(ObjectMapperUtils.map(x.getChiTietSanPhamRequest(), ChiTietSanPham.class));
            hoaDonChiTietRepo.save(hoaDonChiTiet);
            // and add hoadonchitiet

            // start update chitietsanpham
            ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findById(x.getChiTietSanPhamRequest().getId()).get();
            chiTietSanPham.setSoLuong(chiTietSanPham.getSoLuong() - x.getSoLuong());
            chiTietSanPhamService.update(x.getChiTietSanPhamRequest().getId(),ObjectMapperUtils.map(chiTietSanPham, ChiTietSanPhamRequest.class));
            // and update chitietsanpham

            // start update giohangchitiet
            //
        }
        return hoaDon;
    }


    @Override
    public HoaDon choVanChuyen(Integer id) {
        HoaDon hoaDon = hoaDonRepo.findById(id).get();
        hoaDon.setTrangThai(2);
        return hoaDonRepo.save(hoaDon);
    }

    @Override
    public HoaDon dangGiao(Integer id) {
        HoaDon hoaDon = hoaDonRepo.findById(id).get();
        hoaDon.setTrangThai(3);
        return hoaDonRepo.save(hoaDon);
    }

    @Override
    public HoaDon daGiao(Integer id) {
        HoaDon hoaDon = hoaDonRepo.findById(id).get();
        hoaDon.setTrangThai(4);
        return hoaDonRepo.save(hoaDon);
    }

    @Override
    public HoaDon daHuy(Integer id) {
        HoaDon hoaDon = hoaDonRepo.findById(id).get();
        hoaDon.setTrangThai(5);
        return hoaDonRepo.save(hoaDon);
    }

    @Override
    public HoaDon traHang(Integer id) {
        HoaDon hoaDon = hoaDonRepo.findById(id).get();
        hoaDon.setTrangThai(6);
        return hoaDonRepo.save(hoaDon);
    }

}
