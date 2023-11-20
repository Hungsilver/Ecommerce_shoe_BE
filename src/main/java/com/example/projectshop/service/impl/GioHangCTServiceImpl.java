package com.example.projectshop.service.impl;

import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.GioHang;
import com.example.projectshop.domain.GioHangChiTiet;
import com.example.projectshop.domain.KhachHang;
import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamRequest;
import com.example.projectshop.repository.ChiTietSanPhamRepository;
import com.example.projectshop.repository.GioHangChiTietRepository;
import com.example.projectshop.repository.GioHangRepository;
import com.example.projectshop.service.IGioHangCTService;
import com.example.projectshop.service.ObjectMapperUtils;
import jakarta.transaction.Transactional;
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
    public GioHangChiTiet addSP(ChiTietSanPham chiTietSanPham, int soluong, Integer idgiohang) {

        return null;
    }


    GioHang gh;

    @Override
    public GioHangChiTiet onlineCart(KhachHang kh, Integer idctsp, int soluong) {
        gh = kh.getGiohang();
        ChiTietSanPham ctsp = chiTietSanPhamRepository.findById(idctsp).orElse(null);
        List<GioHangChiTiet> list = gioHangChiTietRepository.findGioHangChiTietByGioHangId(gh.getId());
        for (GioHangChiTiet x : list) {
            if (x.getChiTietSanPham().getId().equals(ctsp.getId())) {
                System.out.println("chay vao if");
                x.setSoLuong(x.getSoLuong() + soluong);
                BigDecimal giamoi = x.getGiaBan().multiply(new BigDecimal(soluong));
                x.setGiaBan(giamoi);
                gioHangChiTietRepository.save(x);

                return x;
            }
        }
        // Truong Hop chua co SP trong ghct(Da co GioHang)

        System.out.println(" truong hop tao ghct");
        GioHangChiTiet gioHangChiTiet = new GioHangChiTiet();
        gioHangChiTiet.setChiTietSanPham(ctsp);
        gioHangChiTiet.setGioHang(gh);
        gioHangChiTiet.setSoLuong(soluong);
        BigDecimal dongia = ctsp.getGiaBan().multiply(new BigDecimal(soluong));
        gioHangChiTiet.setGiaBan(dongia);
        GioHangChiTiet sanphammoi = gioHangChiTietRepository.save(gioHangChiTiet);


        return sanphammoi;
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
    public void remove(KhachHang kh, Integer id) {
        GioHang gh = kh.getGiohang();
        System.out.println("gio hang sevice" + gh);
        List<GioHangChiTiet> list = gioHangChiTietRepository.findGioHangChiTietByGioHangId(gh.getId());
        for (GioHangChiTiet x : list) {
            if (x.getChiTietSanPham().getId().equals(id)) {
                gioHangChiTietRepository.deleteById(x.getId());
                break;
            }
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
