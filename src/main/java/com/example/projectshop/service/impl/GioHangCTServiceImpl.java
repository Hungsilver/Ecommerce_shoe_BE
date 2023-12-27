package com.example.projectshop.service.impl;

import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.GioHang;
import com.example.projectshop.domain.GioHangChiTiet;
import com.example.projectshop.domain.KhachHang;
import com.example.projectshop.repository.ChiTietSanPhamRepository;
import com.example.projectshop.repository.GioHangChiTietRepository;
import com.example.projectshop.repository.GioHangRepository;
import com.example.projectshop.service.IGioHangCTService;
import com.example.projectshop.utilities.URLDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public GioHangChiTiet addSP(ChiTietSanPham chiTietSanPham, int soluong, Integer idgiohang) {

        return null;
    }


    GioHang gh;

    @Override
    public GioHangChiTiet onlineCart(KhachHang kh, Integer idctsp, int soluong) {

        GioHang gioHang = kh.getGiohang();
        ChiTietSanPham ctsp = chiTietSanPhamRepository.findById(idctsp).get();
        // Truong hop da có sản phẩm trong giohangchitiet
        GioHangChiTiet gioHangChiTiet1 = gioHangChiTietRepository.findByIdGioHangAndIdCTSP(gioHang.getId(), idctsp);
        if (gioHangChiTiet1 != null) {
            gioHangChiTiet1.setGiaBan(ctsp.getGiaBan());
            gioHangChiTiet1.setSoLuong(soluong+gioHangChiTiet1.getSoLuong());
            return gioHangChiTietRepository.save(gioHangChiTiet1);
        }

        GioHangChiTiet gioHangChiTiet = GioHangChiTiet.builder()
                .id(null)
                .soLuong(soluong)
                .giaBan(ctsp.getGiaBan())
                .chiTietSanPham(ctsp)
                .gioHang(gioHang)
                .build();
        return gioHangChiTietRepository.save(gioHangChiTiet);
    }

    @Override
    public List<GioHangChiTiet> GetGioHangCTByIdGioHang(Integer id) {
        List<GioHangChiTiet> dsghct = gioHangChiTietRepository.findGioHangChiTietByGioHangId(id);
        return dsghct;
    }

    @Override
    public List<GioHangChiTiet> findById(String listIdGhct) {
        List<GioHangChiTiet> list = new ArrayList<>();
        List<Integer> listId = listIdGhct == null ? null : Arrays.stream(URLDecode.getDecode(listIdGhct).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        for (Integer x : listId) {
            list.add(gioHangChiTietRepository.findById(x).get());
        }
        return list;
    }

    @Override
    public GioHangChiTiet update(GioHangChiTiet gioHangChiTiet) {

        return gioHangChiTietRepository.save(gioHangChiTiet);
    }

    @Override
    public GioHangChiTiet updateQuantity(Integer id, Integer soLuong) {
        GioHangChiTiet gioHangChiTiet = gioHangChiTietRepository.findById(id).get();
        gioHangChiTiet.setSoLuong(soLuong);
        gioHangChiTiet.setGiaBan(gioHangChiTiet.getChiTietSanPham().getGiaBan());
        return gioHangChiTietRepository.save(gioHangChiTiet);
    }


    @Override
    public void remove(KhachHang kh, String listIdGhct) {
        GioHang gh = kh.getGiohang();
        List<Integer> listId = listIdGhct == null ? null : Arrays.stream(URLDecode.getDecode(listIdGhct).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        for (Integer x : listId) {
            GioHangChiTiet gioHangChiTiet = gioHangChiTietRepository.findById(x).get();
            gioHangChiTietRepository.delete(gioHangChiTiet);
        }


    }

    @Override
    public boolean increase(KhachHang kh, Integer idctsp) {
        GioHang gh = kh.getGiohang();
        List<GioHangChiTiet> list = gioHangChiTietRepository.findGioHangChiTietByGioHangId(gh.getId());

        if (kh != null && idctsp != null) {
            list.stream().forEach(x -> {
                if (x.getChiTietSanPham().getId().equals(idctsp)) {
                    x.setSoLuong(x.getSoLuong() + 1);
                    System.out.println("gia ban" + x.getChiTietSanPham().getGiaBan());
                    BigDecimal giacong = x.getChiTietSanPham().getGiaBan();
                    BigDecimal sum = x.getGiaBan().add(giacong);
                    x.setGiaBan(sum);
                    gioHangChiTietRepository.save(x);
                }


            });
        }

        return true;
    }


    @Override
    public boolean reduce(KhachHang kh, Integer idctsp) {
        GioHang gh = kh.getGiohang();
        List<GioHangChiTiet> list = gioHangChiTietRepository.findGioHangChiTietByGioHangId(gh.getId());
        if (kh != null && idctsp != null) {
            list.stream().forEach(x -> {
                if (x.getChiTietSanPham().getId().equals(idctsp)) {
                    if (x.getSoLuong() == 1) {
                        gioHangChiTietRepository.delete(x);
//                        gioHangRepository.save(gh);
                    } else {
                        x.setSoLuong(x.getSoLuong() - 1);
                        BigDecimal giatru = x.getChiTietSanPham().getGiaBan();
                        // lay gia hien tai trong gio tru di gia của sản phẩm
                        BigDecimal difference = x.getGiaBan().subtract(giatru);
                        x.setGiaBan(difference);
                        gioHangChiTietRepository.save(x);
                    }
                }
            });

        }
        return true;
    }
}
