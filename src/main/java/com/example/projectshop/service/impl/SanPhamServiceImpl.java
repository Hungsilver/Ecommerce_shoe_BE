package com.example.projectshop.service.impl;


import com.example.projectshop.domain.*;
import com.example.projectshop.dto.sanpham.SanPhamRequest;
import com.example.projectshop.repository.*;
import com.example.projectshop.service.ISanPhamService;
import com.example.projectshop.service.ObjectMapperUtils;
import com.example.projectshop.utils.URLDecode;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SanPhamServiceImpl implements ISanPhamService {

    @Autowired
    private SanPhamRepository sanPhamrepo;

    @Autowired
    ThuongHieuRepository thuongHieuRepository;

    @Autowired
    private DanhMucRepository danhMucRepository;

    @Autowired
    private XuatXuRepository xuatXuRepository;

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepo;


    @Override
    public Page<SanPham> findAll(Integer page,
                                 Integer pageSize,
                                 String sortField,
                                 Boolean isSortDesc
    ) {
        Sort sort = Sort.by(isSortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, pageSize, sort);
        return sanPhamrepo.findAll(pageable);
    }

    @Override
    public Page<SanPham> filter(String priceMin,
                                String priceMax,
                                String brand,
                                String origin,
                                String color,
                                String size,
                                String shoe_material,
                                String shoe_sole_material,
                                String keyword,
                                Boolean isSortAsc,
                                Integer page,
                                Integer pageSize) {
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, pageSize);


        BigDecimal priceMinOutput = priceMin == null ? BigDecimal.valueOf(0) : BigDecimal.valueOf(Long.valueOf(priceMin));
        BigDecimal priceMaxOutput = priceMax == null ? chiTietSanPhamRepo.getTop1ByPriceMax().getGiaBan() : BigDecimal.valueOf(Long.valueOf(priceMax));

        List<Integer> listThuongHieu = brand == null ? null : Arrays.stream(URLDecode.getDecode(brand).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        List<Integer> listXuatXu = origin == null ? null : Arrays.stream(URLDecode.getDecode(origin).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        List<Integer> listMauSac = color == null ? null : Arrays.stream(URLDecode.getDecode(color).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        List<Integer> listKichCo = size == null ? null : Arrays.stream(URLDecode.getDecode(size).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        List<Integer> listChatLieuGiay = shoe_material == null ? null : Arrays.stream(URLDecode.getDecode(shoe_material).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        List<Integer> listChatLieuDeGiay = shoe_sole_material == null ? null : Arrays.stream(URLDecode.getDecode(shoe_sole_material).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        // sắp xếp list chitietsanpham theo giá
        Page<SanPham> listSanPham = sanPhamrepo.getAllByParam(
                priceMinOutput,
                priceMaxOutput,
                listThuongHieu,
                listXuatXu,
                listMauSac,
                listKichCo,
                listChatLieuGiay,
                listChatLieuDeGiay,
                keyword,
                pageable);
        for (SanPham sanPham : listSanPham.getContent()) {
            List<ChiTietSanPham> chiTietSanPhamList = sanPham.getListChiTietSanPham();
            if (isSortAsc == false) {
                // sort asc
                Collections.sort(chiTietSanPhamList, (ctsp1, ctsp2) -> ctsp1.getGiaBan().compareTo(ctsp2.getGiaBan()));
            } else {
                // sort desc
                Collections.sort(chiTietSanPhamList, (ctsp1, ctsp2) -> ctsp2.getGiaBan().compareTo(ctsp1.getGiaBan()));
            }
        }

        return listSanPham;
    }


    @Override
    public Optional<SanPham> findById(Integer id) {
        return sanPhamrepo.findById(id);
    }

    @Override
    public SanPham create(SanPhamRequest sanPhamRequest) {

        SanPham sanPham2 = new SanPham();
        sanPham2 = ObjectMapperUtils.map(sanPhamRequest, SanPham.class);
//        sanPham2.setId(null);
        ThuongHieu thuongHieu = thuongHieuRepository.findById(sanPhamRequest.getThuongHieu())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy thương hiệu"));
        sanPham2.setThuongHieu(thuongHieu);

        DanhMuc danhMuc = danhMucRepository.findById(sanPhamRequest.getDanhMuc())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy thương hiệu"));
        sanPham2.setDanhMuc(danhMuc);

        Xuatxu xuatxu = xuatXuRepository.findById(sanPhamRequest.getXuatXu())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy thương hiệu"));
        sanPham2.setXuatXu(xuatxu);


        return sanPhamrepo.save(sanPham2);
    }

    @Override
    public SanPham update(Integer id, SanPhamRequest sanPhamRequest) {
//        SanPham sanPham2 = ObjectMapperUtils.map(sanPhamRequest, SanPham.class);
//        sanPham2.setId(id);
//        return sanPhamrepo.save(sanPham2);

        SanPham sanPham2 = new SanPham();
               sanPham2= ObjectMapperUtils.map(sanPhamRequest, SanPham.class);
        sanPham2.setId(id);
        ThuongHieu thuongHieu=thuongHieuRepository.findById(sanPhamRequest.getThuongHieu())
         .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy thương hiệu"));
        sanPham2.setThuongHieu(thuongHieu);

        DanhMuc danhMuc=danhMucRepository.findById(sanPhamRequest.getDanhMuc())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy thương hiệu"));
        sanPham2.setDanhMuc(danhMuc);

        Xuatxu xuatxu=xuatXuRepository.findById(sanPhamRequest.getThuongHieu())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy thương hiệu"));
        sanPham2.setXuatXu(xuatxu);


        return sanPhamrepo.save(sanPham2);
    }

    @Override
    public SanPham delete(Integer id) {
        Optional<SanPham> sanPham = this.findById(id);
        if (sanPham.isPresent()) {
            sanPham.get().setTrangThai(0);
            return sanPhamrepo.save(sanPham.get());
        }
        return null;
    }

}
