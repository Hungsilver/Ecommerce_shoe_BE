package com.example.projectshop.service.impl;

import com.example.projectshop.domain.GioHang;
import com.example.projectshop.domain.KhachHang;
import com.example.projectshop.dto.khachhang.KhachHangRequest;
import com.example.projectshop.exception.UnauthorizedException;
import com.example.projectshop.repository.GioHangRepository;
import com.example.projectshop.repository.KhachHangRepository;
import com.example.projectshop.service.IKhachHangService;
import com.example.projectshop.service.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public Optional<KhachHang> findById(Integer id) {
        return khachHangRepo.findById(id);
    }

    @Override
    public KhachHang create(KhachHangRequest KhachHangRequest) {
        KhachHang khachHang = ObjectMapperUtils.map(KhachHangRequest, KhachHang.class);
        khachHang.setId(null);
        return khachHangRepo.save(khachHang);
    }

    @Override
    public KhachHang update(Integer id, KhachHangRequest KhachHangRequest) {
        KhachHang khachHang = ObjectMapperUtils.map(KhachHangRequest, KhachHang.class);
        khachHang.setId(id);
        return khachHangRepo.save(khachHang);
    }

    @Override
    public KhachHang delete(Integer id) {
        Optional<KhachHang> khachHang = this.findById(id);
        if (khachHang.isPresent()) {
            khachHang.get().setTrangThai(0);
            return khachHangRepo.save(khachHang.get());
        }
        return null;
    }

    @Override
    public KhachHang registerKhachHang(KhachHangRequest khachHangRequest) {
        if (khachHangRepo.findByEmail(khachHangRequest.getEmail()) != null){
            throw new UnauthorizedException("Email đã tồn tại");
        }

        KhachHang khachHang = ObjectMapperUtils.map(khachHangRequest, KhachHang.class);
        khachHang.setId(null);
        khachHang.setMatKhau(passwordEncoder.encode(khachHangRequest.getMatKhau()));

        return khachHangRepo.save(khachHang);
    }

    @Override
    public KhachHang loginKhachHang(String email, String matKhau) {
        KhachHang khachHang=khachHangRepo.findByEmail(email);
        if (khachHang ==null || !passwordEncoder.matches(matKhau,khachHang.getMatKhau())){
            throw new UnauthorizedException("Email hoặc mật khẩu không đúng");
        }
        if (khachHang.getGiohang()== null){
            GioHang gioHang= GioHang.builder().build();
            gioHang.setKhachHang(khachHang);
            khachHang.setGiohang(gioHang);
            gioHangRepository.save(gioHang);
            khachHangRepo.save(khachHang);
        }
        return khachHang;
    }
}
