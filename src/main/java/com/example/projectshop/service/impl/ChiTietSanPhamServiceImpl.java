package com.example.projectshop.service.impl;

import com.example.projectshop.domain.AnhSanPham;
import com.example.projectshop.domain.ChatLieuDeGiay;
import com.example.projectshop.domain.ChatLieuGiay;
import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.KichCo;
import com.example.projectshop.domain.MauSac;
import com.example.projectshop.domain.SanPham;
import com.example.projectshop.dto.chitietsanpham.ChiTietSanPhamRequest;
import com.example.projectshop.repository.AnhSanPhamRepository;
import com.example.projectshop.repository.ChiTietSanPhamRepository;
import com.example.projectshop.service.IChatLieuDeGiayService;
import com.example.projectshop.service.IChatLieuGiayService;
import com.example.projectshop.service.IChiTietSanPhamService;
import com.example.projectshop.service.IKichCoService;
import com.example.projectshop.service.IMauSacService;
import com.example.projectshop.service.ISanPhamService;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChiTietSanPhamServiceImpl implements IChiTietSanPhamService {

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepo;

    @Autowired
    private AnhSanPhamRepository anhSanPhamRepo;

    @Autowired
    private IMauSacService mauSacService;

    @Autowired
    private IKichCoService kichCoService;

    @Autowired
    private IChatLieuGiayService chatLieuGiayService;

    @Autowired
    private IChatLieuDeGiayService chatLieuDeGiayService;

    @Autowired
    private ISanPhamService sanPhamService;

    // lấy ra ngày hiện tại
    private LocalDate curruntDate = LocalDate.now();


    @Override
    public Page<ChiTietSanPham> findAll(Integer page,
                                        Integer pageSize,
                                        String sortField,
                                        Boolean isSortDesc
    ) {
        Sort sort = Sort.by(isSortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, pageSize, sort);
        return chiTietSanPhamRepo.findAll(pageable);
    }


    @Override
    public Page<ChiTietSanPham> filter(String priceMin,
                                       String priceMax,
                                       String color,
                                       String shoe_material,
                                       String shoe_sole_material,
                                       String keyword,
                                       Boolean isSortDesc,
                                       String sortField,
                                       Integer page,
                                       Integer pageSize) {
        Sort sort = Sort.by(isSortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, pageSize, sort);

        BigDecimal priceMinOutput = priceMin == null ? BigDecimal.valueOf(0) : BigDecimal.valueOf(Long.valueOf(priceMin));
        BigDecimal priceMaxOutput = priceMax == null ? chiTietSanPhamRepo.getTop1ByPriceMax().getGiaBan() : BigDecimal.valueOf(Long.valueOf(priceMax));

        List<Integer> listMauSac = color == null ? null : Arrays.stream(URLDecode.getDecode(color).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        List<Integer> listChatLieuGiay = shoe_material == null ? null : Arrays.stream(URLDecode.getDecode(shoe_material).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        List<Integer> listChatLieuDeGiay = shoe_sole_material == null ? null : Arrays.stream(URLDecode.getDecode(shoe_sole_material).split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        return chiTietSanPhamRepo.getAllByParam(
                priceMinOutput,
                priceMaxOutput,
                listMauSac,
                listChatLieuGiay,
                listChatLieuDeGiay,
                keyword,
                pageable);
    }


    @Override
    public Optional<ChiTietSanPham> findById(Integer id) {
        if (id == null){
            return null;
        }
        return chiTietSanPhamRepo.findById(id);
    }

    @Override
    public ChiTietSanPham create(ChiTietSanPhamRequest chiTietSanPhamRequest) {
        MauSac mauSac = mauSacService.findById(chiTietSanPhamRequest.getMauSac());
        KichCo kichCo = kichCoService.findById(chiTietSanPhamRequest.getKichCo());
        ChatLieuGiay chatLieuGiay = chatLieuGiayService.findById(chiTietSanPhamRequest.getChatLieuGiay());
        ChatLieuDeGiay chatLieuDeGiay = chatLieuDeGiayService.findById(chiTietSanPhamRequest.getChatLieuDeGiay());
        SanPham sanPham = sanPhamService.findById(chiTietSanPhamRequest.getSanPham());
        String maMauSac = utils.tiengVietKhongDau(mauSac.getTen());
        String maChatLieuGiay = utils.tiengVietKhongDau(chatLieuGiay.getTen()).replaceAll("\\s","");;
        String maChatLieuDeGiay = utils.tiengVietKhongDau(chatLieuDeGiay.getTen());
        String maChiTietSanPham = sanPham.getMa()+"_"+maMauSac+"_"+maChatLieuGiay+"_"+maChatLieuDeGiay+"_"+kichCo.getSize();

        if (chiTietSanPhamRepo.findByMa(maChiTietSanPham).isEmpty()){
            ChiTietSanPham chiTietSanPham = ChiTietSanPham.builder()
                    .id(null)
                    .ma(maChiTietSanPham)
                    .soLuong(chiTietSanPhamRequest.getSoLuong())
                    .giaBan(chiTietSanPhamRequest.getGiaBan())
                    .ngayTao(Date.valueOf(curruntDate))
                    .ngayCapNhat(null)
                    .trangThai(0)
                    .mauSac(mauSac)
                    .kichCo(kichCo)
                    .chatLieuGiay(chatLieuGiay)
                    .chatLieuDeGiay(chatLieuDeGiay)
                    .sanPham(sanPham)
                    .build();
            ChiTietSanPham saveChiTietSanPham = chiTietSanPhamRepo.save(chiTietSanPham);
            if (!chiTietSanPhamRequest.getAnhSanPhams().isEmpty()) {
                for (String x : chiTietSanPhamRequest.getAnhSanPhams()) {
                    AnhSanPham anhSanPham = AnhSanPham.builder()
                            .id(null)
                            .chiTietSanPham(saveChiTietSanPham)
                            .ten(x)
                            .build();
                    anhSanPhamRepo.save(anhSanPham);
                }
            }
            return saveChiTietSanPham;
        }
        return null;
    }

    @Override
    public ChiTietSanPham update(Integer id, ChiTietSanPhamRequest chiTietSanPhamRequest) {
        ChiTietSanPham chiTietSanPham = ChiTietSanPham.builder()
                .id(id)
                .ma(this.findById(id).get().getMa())
                .soLuong(chiTietSanPhamRequest.getSoLuong())
                .giaBan(chiTietSanPhamRequest.getGiaBan())
                .ngayTao(chiTietSanPhamRepo.findById(id).get().getNgayTao())
                .ngayCapNhat(Date.valueOf(curruntDate))
                .trangThai(chiTietSanPhamRequest.getTrangThai())
                .mauSac(mauSacService.findById(chiTietSanPhamRequest.getMauSac()))
                .kichCo(kichCoService.findById(chiTietSanPhamRequest.getKichCo()))
                .chatLieuGiay(chatLieuGiayService.findById(chiTietSanPhamRequest.getChatLieuGiay()))
                .chatLieuDeGiay(chatLieuDeGiayService.findById(chiTietSanPhamRequest.getChatLieuDeGiay()))
                .sanPham(sanPhamService.findById(chiTietSanPhamRequest.getSanPham()))
                .build();
        ChiTietSanPham saveChiTietSanPham = chiTietSanPhamRepo.save(chiTietSanPham);
        if (!chiTietSanPhamRequest.getAnhSanPhams().isEmpty()) { // check rỗng list ảnh
            for (String x : chiTietSanPhamRequest.getAnhSanPhams()) { // lặp list đường dẫn ảnh gửi từ client
                if (!anhSanPhamRepo.getByTen(x).isPresent()){ // check tên ảnh trong db nếu chưa có thì insert
                    AnhSanPham anhSanPham = AnhSanPham.builder()
                            .id(null)
                            .chiTietSanPham(saveChiTietSanPham)
                            .ten(x)
                            .build();
                    anhSanPhamRepo.save(anhSanPham);
                }
            }
        }

        return saveChiTietSanPham;
    }

    @Override
    public ChiTietSanPham delete(Integer id) {
        Optional<ChiTietSanPham> chiTietSanPham = this.findById(id);
        if (chiTietSanPham.isPresent()) {
            chiTietSanPham.get().setTrangThai(1);
            return chiTietSanPhamRepo.save(chiTietSanPham.get());
        }
        return null;
    }

    @Override
    public ChiTietSanPham searchMa(String ma) {
        return chiTietSanPhamRepo.searchMa(ma);
    }

}
