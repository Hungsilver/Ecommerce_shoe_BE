package com.example.projectshop.service.impl;

import com.example.projectshop.domain.GioHang;
import com.example.projectshop.domain.KhachHang;
import com.example.projectshop.dto.khachhang.ExportExcelKhachHang;
import com.example.projectshop.dto.khachhang.KhachHangRequest;
import com.example.projectshop.exception.UnauthorizedException;
import com.example.projectshop.repository.GioHangRepository;
import com.example.projectshop.repository.KhachHangRepository;
import com.example.projectshop.service.IKhachHangService;
import com.example.projectshop.service.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KhachHangServiceImpl implements IKhachHangService {

    @Autowired
    private KhachHangRepository khachHangRepo;

    @Autowired
    private GioHangRepository gioHangRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Page<KhachHang> findAll(Integer page,
                                   Integer pageSize,
                                   String sortField,
                                   Boolean isSortDesc,
                                   String keyword) {
        Sort sort = Sort.by(isSortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, pageSize, sort);
        if (keyword != null && !keyword.isEmpty()) {
            return khachHangRepo.findAllByEmailOrSoDienThoai(keyword, pageable);
        } else {
            return khachHangRepo.findAll(pageable);
        }
    }

    @Override
    public KhachHang findById(Integer id) {
        if (id != null) {
            return khachHangRepo.findById(id).get();
        }
        return null;
    }

    @Override
    public List<ExportExcelKhachHang> exportExcel() {
        List<ExportExcelKhachHang> exportExcelKhachHangs = new ArrayList<>();
        Integer index = 1;
        String trangThai = null;
        for (KhachHang x : khachHangRepo.findAll()) {// convert từ KhachHang sang ExportExcelKhachHang
            if (x.getTrangThai() == 0) {
                trangThai = "Hoạt Động";
            } else {
                trangThai = "Vô hiệu hóa";
            }
            ExportExcelKhachHang exportExcelKhachHang = ExportExcelKhachHang.builder()
                    .stt(index++)
                    .hoTen(x.getHoTen())
                    .email(x.getEmail())
                    .soDienThoai(x.getSoDienThoai())
                    .ngaySinh(x.getNgaySinh())
                    .trangThai(trangThai)
                    .build();
            exportExcelKhachHangs.add(exportExcelKhachHang);
        }
        return exportExcelKhachHangs;
    }

    @Override
    public KhachHang create(KhachHangRequest khachHangRequest) {
        KhachHang khachHang = KhachHang.builder()
                .id(null)
                .hoTen(khachHangRequest.getHoTen())
                .email(khachHangRequest.getEmail())
                .matKhau(null)
                .soDienThoai(khachHangRequest.getSoDienThoai())
                .ngaySinh(khachHangRequest.getNgaySinh())
                .trangThai(1)
                .build();
        return khachHangRepo.save(khachHang);
    }

    @Override
    public KhachHang update(Integer id, KhachHangRequest khachHangRequest) {
        KhachHang khachHang = KhachHang.builder()
                .id(id)
                .hoTen(khachHangRequest.getHoTen())
                .email(khachHangRequest.getEmail())
                .matKhau(passwordEncoder.encode(khachHangRequest.getMatKhau()))
                .soDienThoai(khachHangRequest.getSoDienThoai())
                .ngaySinh(khachHangRequest.getNgaySinh())
                .trangThai(khachHangRequest.getTrangThai())
                .build();
        return khachHangRepo.save(khachHang);
    }

    @Override
    public KhachHang delete(Integer id) {
        Optional<KhachHang> khachHang = khachHangRepo.findById(id);
        if (khachHang.isPresent()) {
            khachHang.get().setTrangThai(0);
            return khachHangRepo.save(khachHang.get());
        }
        return null;
    }


    //đăng ký cho khách hàng
    @Override
    public KhachHang registerKhachHang(KhachHangRequest khachHangRequest) {
        if (khachHangRepo.findByEmail(khachHangRequest.getEmail()) != null) {
//            throw new UnauthorizedException("Email đã tồn tại");
            return null;
        }
        // tao khach hang
        KhachHang khachHang = ObjectMapperUtils.map(khachHangRequest, KhachHang.class);
        khachHang.setId(null);
        khachHang.setMatKhau(passwordEncoder.encode(khachHangRequest.getMatKhau()));
        khachHangRepo.save(khachHang);


        // khi đăng ký thành công khởi tạo giỏ hàng cho khách hàng
        GioHang gioHang = GioHang.builder().build();
        gioHang.setKhachHang(khachHang);
        khachHang.setGiohang(gioHang);
        gioHangRepository.save(gioHang);
        return khachHang;
    }

    @Override
    public KhachHang loginKhachHang(String email, String matKhau) {
        KhachHang khachHang = khachHangRepo.findByEmail(email);

        if (khachHang == null) {
            return null;
//            throw new UnauthorizedException("Email hoặc mật khẩu không đúng");
        }

        if (!passwordEncoder.matches(matKhau, khachHang.getMatKhau())) {
            return null;
        }
        return khachHang;
    }

    @Override
    public KhachHang findByEmail(String email) {
        return khachHangRepo.findByEmail(email);
    }

    @Override
    public KhachHang findBySdt(String sdt) {
        return khachHangRepo.findBySoDienThoai(sdt);
    }
    @Override
    public boolean isSoDienThoaiExists(String soDienThoai) {
        return false;
    }

    @Override
    public boolean isEmailExists(String email) {
        return false;

    }
}
