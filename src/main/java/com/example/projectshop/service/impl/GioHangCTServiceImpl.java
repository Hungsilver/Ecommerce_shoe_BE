package com.example.projectshop.service.impl;

import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.GioHang;
import com.example.projectshop.domain.GioHangChiTiet;
import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamRequest;
import com.example.projectshop.repository.ChiTietSanPhamRepository;
import com.example.projectshop.repository.GioHangChiTietRepository;
import com.example.projectshop.repository.GioHangRepository;
import com.example.projectshop.service.IGioHangCTService;
import com.example.projectshop.service.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class GioHangCTServiceImpl implements IGioHangCTService {

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;
    @Autowired
    private GioHangRepository gioHangRepository;

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Override
    public Page<GioHangChiTiet> getall(Pageable pageable) {

        return gioHangChiTietRepository.findAll(pageable);
    }

    @Override
    public GioHangChiTiet addSP(ChiTietSanPhamRequest chiTietSanPhamRequest, int soluong, Integer idgiohang) {

        // add SP mới khi mà sản phẩm này chưa có trong giỏ(đã có giỏ hàng) chưa có GHCT
        // san pham moi

        GioHang gh = gioHangRepository.findById(idgiohang).orElse(null);
        ChiTietSanPham ctspDB;
        ctspDB = ObjectMapperUtils.map(chiTietSanPhamRequest, ChiTietSanPham.class);
        GioHangChiTiet gioHangChiTiet = new GioHangChiTiet();
        gioHangChiTiet.setSoLuong(soluong);
        BigDecimal dongia = ctspDB.getGiaBan().multiply(new BigDecimal(soluong));
        gioHangChiTiet.setGiaBan(dongia);
        gioHangChiTiet.setChiTietSanPham(ctspDB);
        gioHangChiTiet.setGioHang(gh);
        GioHangChiTiet ghct = gioHangChiTietRepository.save(gioHangChiTiet);

        return ghct;
    }

    @Override
    public List<GioHangChiTiet> GetGioHangCTByIdGioHang(Integer id) {
        List<GioHangChiTiet> dsghct = gioHangChiTietRepository.findGioHangChiTietByGioHangId(id);
        return dsghct;
    }

    @Override
    public GioHangChiTiet update(GioHangChiTiet gioHangChiTiet) {

        return gioHangChiTietRepository.save(gioHangChiTiet);
    }


    @Override
    public void delete(Integer id) {

    }
}
