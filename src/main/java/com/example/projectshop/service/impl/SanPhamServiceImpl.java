package com.example.projectshop.service.impl;


import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.SanPham;
import com.example.projectshop.dto.sanpham.SanPhamRequest;
import com.example.projectshop.repository.ChiTietSanPhamRepository;
import com.example.projectshop.repository.SanPhamRepository;
import com.example.projectshop.service.IDanhMucSevice;
import com.example.projectshop.service.ISanPhamService;
import com.example.projectshop.service.IThuongHieuService;
import com.example.projectshop.service.ObjectMapperUtils;
import com.example.projectshop.utils.URLDecode;
import com.example.projectshop.utils.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
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
    private ChiTietSanPhamRepository chiTietSanPhamRepo;

    @Autowired
    private IThuongHieuService thuongHieuService;

    @Autowired
    private IDanhMucSevice danhMucSevice;

    @Autowired
    private XuatXuServiceImpl xuatXuService;

    // lấy ra ngày hiện tại
    private LocalDate curruntDate = LocalDate.now();


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
    public SanPham findById(Integer id) {
        if (id != null) {
        return sanPhamrepo.findById(id).get();
        }
        return null;
    }

    @Override
    public SanPham create(SanPhamRequest sanPhamRequest) {
        String maSanPham = sanPhamrepo.getTop1ByIdMax().getMa();
        String maMoi;
        if (maSanPham == null){
             maMoi = "SP00001";
        }else{
            maMoi = "SP"+utils.getNumberFromCode(maSanPham);
        }

        SanPham sanPham = SanPham.builder()
                .id(null)
                .ma(maMoi)
                .ten(sanPhamRequest.getTen())
                .anhChinh(sanPhamRequest.getAnhChinh())
                .moTa(sanPhamRequest.getMoTa())
                .ngayTao(Date.valueOf(curruntDate))
                .ngayCapNhat(null)
                .trangThai(0)
                .thuongHieu(thuongHieuService.findById(sanPhamRequest.getThuongHieu()))
                .danhMuc(danhMucSevice.findById(sanPhamRequest.getDanhMuc()))
                .xuatXu(xuatXuService.findById(sanPhamRequest.getXuatXu()))
                .build();
        return sanPhamrepo.save(sanPham);
    }

    @Override
    public SanPham update(Integer id, SanPhamRequest sanPhamRequest) {
        SanPham sanPham = SanPham.builder()
                .id(id)
                .ma(this.findById(id).getMa())
                .ten(sanPhamRequest.getTen())
                .anhChinh(sanPhamRequest.getAnhChinh())
                .moTa(sanPhamRequest.getMoTa())
                .ngayTao(sanPhamrepo.findById(id).get().getNgayTao())
                .ngayCapNhat(Date.valueOf(curruntDate))
                .trangThai(0)
                .thuongHieu(thuongHieuService.findById(sanPhamRequest.getThuongHieu()))
                .danhMuc(danhMucSevice.findById(sanPhamRequest.getDanhMuc()))
                .xuatXu(xuatXuService.findById(sanPhamRequest.getXuatXu()))
                .build();
        return sanPhamrepo.save(sanPham);
    }

    @Override
    public SanPham delete(Integer id) {
        SanPham sanPham = this.findById(id);
            sanPham.setTrangThai(0);
            return sanPhamrepo.save(sanPham);
    }

}
