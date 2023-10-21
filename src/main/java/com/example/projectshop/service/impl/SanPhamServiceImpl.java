package com.example.projectshop.service.impl;


import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.SanPham;
import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamResponse;
import com.example.projectshop.dto.sanpham.SanPhamRequest;
import com.example.projectshop.repository.ChiTietSanPhamRepository;
import com.example.projectshop.repository.SanPhamRepository;
import com.example.projectshop.service.ISanPhamService;
import com.example.projectshop.service.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SanPhamServiceImpl implements ISanPhamService {

    @Autowired
    private SanPhamRepository sanPhamrepo;

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepo;


    @Override
    public List<SanPham> findAll() {
//        List<SanPhamResponse> list = ObjectMapperUtils.mapAll(repo.findAll(),SanPhamResponse.class);
//        return list;
        return sanPhamrepo.findAll();
    }

    @Override
    public Page<SanPham> getAllByParam(String priceMin,
                                       String priceMax,
                                       String thuongHieu,
                                       String xuatXu,
                                       String mauSac,
                                       String chatLieuGiay,
                                       String chatLieuDeGiay,
                                       String pageParam,
                                       String limitParam) {

        Integer page = pageParam == null ? 0 : Integer.valueOf(pageParam);
        Integer limit = limitParam == null ? 5 : Integer.valueOf(limitParam);

        Pageable pageable = PageRequest.of(page, limit);
        if (priceMin == null && priceMax == null && thuongHieu == null && xuatXu == null && mauSac == null && chatLieuGiay == null && chatLieuDeGiay == null) {
            return sanPhamrepo.getAll(pageable);
        }

        if (priceMin != null && priceMax != null && thuongHieu == null && xuatXu == null && mauSac == null && chatLieuGiay == null && chatLieuDeGiay == null) {
            return sanPhamrepo.getAllByBetweenPrice(BigDecimal.valueOf(Double.valueOf(priceMin)), BigDecimal.valueOf(Double.valueOf(priceMin)), pageable);
        }

        BigDecimal priceMaxx = priceMax == null ? chiTietSanPhamRepo.getTop1ByPriceMax().getGiaBan() : BigDecimal.valueOf(Double.valueOf(priceMax));
        BigDecimal priceMinn = priceMin == null ? chiTietSanPhamRepo.getTop1ByPriceMin().getGiaBan() : BigDecimal.valueOf(Double.valueOf(priceMin));
        Integer id_thuongHieu = thuongHieu == null ? sanPhamrepo.getTop1().getThuongHieu().getId() : Integer.valueOf(thuongHieu);
        Integer id_xuatXu = xuatXu == null ? sanPhamrepo.getTop1().getXuatXu().getId() : Integer.valueOf(xuatXu);
        Integer id_mauSac = mauSac == null ? sanPhamrepo.getTop1().getListChiTietSanPham().get(0).getMauSac().getId() : Integer.valueOf(mauSac);
        Integer id_chatLieuGiay = chatLieuGiay == null ? sanPhamrepo.getTop1().getListChiTietSanPham().get(0).getChatLieuGiay().getId() : Integer.valueOf(chatLieuGiay);
        Integer id_chatLieuDeGiay = chatLieuDeGiay == null ? sanPhamrepo.getTop1().getListChiTietSanPham().get(0).getChatLieuDeGiay().getId() : Integer.valueOf(chatLieuDeGiay);

        Page<SanPham> list = ObjectMapperUtils.mapEntityPageIntoDtoPage(sanPhamrepo.getAllByParam(
                priceMinn,
                priceMaxx,
                id_thuongHieu,
                id_xuatXu,
                id_mauSac,
                id_chatLieuGiay,
                id_chatLieuDeGiay,
                pageable), SanPham.class);

        System.out.println("abc" + priceMinn + priceMaxx + id_thuongHieu + id_xuatXu + id_mauSac + id_chatLieuGiay + id_chatLieuDeGiay);
        System.out.println(list.getContent());
        return list;
    }


    @Override
    public SanPham getOne(Integer id) {
        return sanPhamrepo.findById(id).get();
    }

    @Override
    public SanPham create(SanPhamRequest sanPhamRequest) {
        SanPham entity = new SanPham();
        entity.setId(null);
        entity.setMa(sanPhamRequest.getMa());
        entity.setTen(sanPhamRequest.getTen());
        entity.setAnhChinh(sanPhamRequest.getAnhChinh());
        entity.setMoTa(sanPhamRequest.getMoTa());
        entity.setTrangThai(sanPhamRequest.getTrangThai());
        entity.setThuongHieu(sanPhamRequest.getThuonghieu());
        entity.setXuatXu(sanPhamRequest.getXuatxu());
        SanPham entityRs = sanPhamrepo.save(entity);
        return entityRs;
    }

    @Override
    public SanPham update(Integer id, SanPhamRequest sanPhamRequest) {
        SanPham entity = new SanPham();
        entity.setId(id);
        entity.setMa(sanPhamRequest.getMa());
        entity.setTen(sanPhamRequest.getTen());
        entity.setAnhChinh(sanPhamRequest.getAnhChinh());
        entity.setMoTa(sanPhamRequest.getMoTa());
        entity.setTrangThai(sanPhamRequest.getTrangThai());
        entity.setThuongHieu(sanPhamRequest.getThuonghieu());
        entity.setXuatXu(sanPhamRequest.getXuatxu());
        SanPham entityRs = sanPhamrepo.save(entity);
        return entityRs;
    }

    @Override
    public void delete(Integer id) {
sanPhamrepo.deleteById(id);
    }

    @Override
    public Page<SanPham> timKiem(String timKiem, String pageParam, String limitParam) {
        Integer page = pageParam == null ? 0 : Integer.valueOf(pageParam);
        Integer limit = limitParam == null ? 5 : Integer.valueOf(limitParam);
        Pageable pageable = PageRequest.of(page, limit);
        return sanPhamrepo.timKiem(timKiem,pageable);
    }
}
