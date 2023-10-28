package com.example.projectshop.service.impl;

import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.GioHang;
import com.example.projectshop.domain.GioHangChiTiet;
import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamRequest;
import com.example.projectshop.repository.GioHangChiTietRepository;
import com.example.projectshop.repository.GioHangRepository;
import com.example.projectshop.service.IGioHangCTService;
import com.example.projectshop.service.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GioHangCTServiceImpl implements IGioHangCTService {

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;
    @Autowired
    private GioHangRepository gioHangRepository;

    @Override
    public Page<GioHangChiTiet> getall(Pageable pageable) {

        return gioHangChiTietRepository.findAll(pageable);
    }




    @Override
    public GioHangChiTiet addSP(ChiTietSanPhamRequest chiTietSanPhamRequest) {
     // add SP mới khi mà sản phẩm này ko có trong giỏ(đã có giỏ hàng) chưa có GHCT

        ChiTietSanPham ctspDB;
        ctspDB = ObjectMapperUtils.map(chiTietSanPhamRequest, ChiTietSanPham.class);
        GioHangChiTiet gioHangChiTiet = new GioHangChiTiet();
        gioHangChiTiet.setChiTietSanPham(ctspDB);
        gioHangChiTietRepository.save(gioHangChiTiet);

//        GioHang gb = new GioHang();
//        gb.setListGioHangChiTiet(gioHangChiTiet);
        return null;
    }

    @Override
    public List<GioHangChiTiet> GetGioHangCTByIdGioHang(Integer id) {
        List<GioHangChiTiet> dsghct = gioHangChiTietRepository.findGioHangChiTietByGioHangId(id);
        return dsghct;
    }


    @Override
    public void delete(Integer id) {

    }
}
