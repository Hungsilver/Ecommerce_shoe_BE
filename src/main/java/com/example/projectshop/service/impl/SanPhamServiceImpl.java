//package com.example.projectshop.service.impl;
//
//
//import com.example.projectshop.domain.SanPham;
//import com.example.projectshop.dto.sanpham.SanPhamRequest;
//import com.example.projectshop.dto.sanpham.SanPhamResponse;
//import com.example.projectshop.repository.ChiTietSanPhamRepository;
//import com.example.projectshop.repository.SanPhamRepository;
//import com.example.projectshop.service.ISanPhamService;
//import com.example.projectshop.service.ObjectMapperUtils;
//import com.example.projectshop.utils.URLDecode;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.net.URLDecoder;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class SanPhamServiceImpl implements ISanPhamService {
//
//    @Autowired
//    private SanPhamRepository sanPhamrepo;
//
//    @Autowired
//    private ChiTietSanPhamRepository chiTietSanPhamRepo;
//
//
//    @Override
//    public List<SanPhamResponse> findAll() {
//        List<SanPhamResponse> list = ObjectMapperUtils.mapAll(sanPhamrepo.findAll(), SanPhamResponse.class);
//        return list;
////        return sanPhamrepo.findAll();
//    }
//
//    @Override
//    public Page<SanPham> getAllByParam(String priceMin,
//                                       String priceMax,
//                                       String thuongHieu,
//                                       String xuatXu,
//                                       String mauSac,
//                                       String chatLieuGiay,
//                                       String chatLieuDeGiay,
//                                       Integer pageParam,
//                                       Integer pageSizeParam) {
//
//        Integer page = pageParam == null ? 0 : Integer.valueOf(pageParam);
//        Integer pageSize = pageSizeParam == null ? Integer.MAX_VALUE : Integer.valueOf(pageSizeParam);
////
//        Pageable pageable = PageRequest.of(page, pageSize);
//
//
//        BigDecimal priceMinOutput = priceMin == null ? chiTietSanPhamRepo.getTop1ByPriceMin().getGiaBan() : BigDecimal.valueOf(Long.valueOf(priceMin));
//        BigDecimal priceMaxOutput = priceMax == null ? chiTietSanPhamRepo.getTop1ByPriceMax().getGiaBan() : BigDecimal.valueOf(Long.valueOf(priceMax));
//        List<Integer> listThuongHieu = thuongHieu == null ? null : Arrays.stream(URLDecode.getDecode(thuongHieu).split(","))
//                .map(Integer::valueOf)
//                .collect(Collectors.toList());
//        List<Integer> listXuatXu = thuongHieu == null ? null : Arrays.stream(URLDecode.getDecode(xuatXu).split(","))
//                .map(Integer::valueOf)
//                .collect(Collectors.toList());
//        List<Integer> listMauSac = thuongHieu == null ? null : Arrays.stream(URLDecode.getDecode(mauSac).split(","))
//                .map(Integer::valueOf)
//                .collect(Collectors.toList());
//        List<Integer> listChatLieuGiay = thuongHieu == null ? null : Arrays.stream(URLDecode.getDecode(chatLieuGiay).split(","))
//                .map(Integer::valueOf)
//                .collect(Collectors.toList());
//        List<Integer> listChatLieuDeGiay = thuongHieu == null ? null : Arrays.stream(URLDecode.getDecode(chatLieuDeGiay).split(","))
//                .map(Integer::valueOf)
//                .collect(Collectors.toList());
//
//        return sanPhamrepo.getAllByParam(
//                priceMinOutput,
//                priceMaxOutput,
//                listThuongHieu,
//                listXuatXu,
//                listMauSac,
//                listChatLieuGiay,
//                listChatLieuDeGiay,
//                pageable);
//    }
//
//
//    @Override
//    public SanPhamResponse getOne(Integer id) {
//        return ObjectMapperUtils.map(sanPhamrepo.findById(id).get(), SanPhamResponse.class);
//    }
//
//    @Override
//    public SanPhamResponse create(SanPhamRequest sanPhamRequest) {
//        SanPham entity = new SanPham();
//        entity.setId(null);
//        entity.setMa(sanPhamRequest.getMa());
//        entity.setTen(sanPhamRequest.getTen());
//        entity.setAnhChinh(sanPhamRequest.getAnhChinh());
//        entity.setMoTa(sanPhamRequest.getMoTa());
//        entity.setTrangThai(sanPhamRequest.getTrangThai());
//        entity.setThuongHieu(sanPhamRequest.getThuonghieu());
//        entity.setXuatXu(sanPhamRequest.getXuatxu());
//        SanPhamResponse entityRs = ObjectMapperUtils.map(sanPhamrepo.save(entity), SanPhamResponse.class);
//        return entityRs;
//    }
//
//    @Override
//    public SanPhamResponse update(Integer id, SanPhamRequest sanPhamRequest) {
//        SanPham entity = new SanPham();
//        entity.setId(id);
//        entity.setMa(sanPhamRequest.getMa());
//        entity.setTen(sanPhamRequest.getTen());
//        entity.setAnhChinh(sanPhamRequest.getAnhChinh());
//        entity.setMoTa(sanPhamRequest.getMoTa());
//        entity.setTrangThai(sanPhamRequest.getTrangThai());
//        entity.setThuongHieu(sanPhamRequest.getThuonghieu());
//        entity.setXuatXu(sanPhamRequest.getXuatxu());
//        SanPhamResponse entityRs = ObjectMapperUtils.map(sanPhamrepo.save(entity), SanPhamResponse.class);
//        return entityRs;
//    }
//
//    @Override
//    public void delete(Integer id) {
//        sanPhamrepo.deleteById(id);
//    }
//
//    @Override
//    public Page<SanPhamResponse> timKiem(String timKiem, String pageParam, String limitParam) {
//        Integer page = pageParam == null ? 0 : Integer.valueOf(pageParam);
//        Integer limit = limitParam == null ? 5 : Integer.valueOf(limitParam);
//        Pageable pageable = PageRequest.of(page, limit);
//        return ObjectMapperUtils.mapEntityPageIntoDtoPage(sanPhamrepo.timKiem(timKiem, pageable), SanPhamResponse.class);
//    }
//}
