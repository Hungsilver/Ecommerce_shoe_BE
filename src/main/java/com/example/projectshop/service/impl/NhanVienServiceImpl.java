package com.example.projectshop.service.impl;

import com.example.projectshop.domain.ChucVu;
import com.example.projectshop.domain.NhanVien;
import com.example.projectshop.domain.ThuongHieu;
import com.example.projectshop.dto.BaseResponse;
import com.example.projectshop.dto.auth.LoginRequest;
import com.example.projectshop.dto.auth.RegisterRequest;
import com.example.projectshop.dto.nhanvien.NhanVienRequest;
import com.example.projectshop.dto.nhanvien.NhanVienResponse;
import com.example.projectshop.exception.BaseException;
import com.example.projectshop.repository.ChucVuRepository;
import com.example.projectshop.repository.NhanVienRepository;
import com.example.projectshop.service.INhanVienService;
import com.example.projectshop.service.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NhanVienServiceImpl implements INhanVienService {

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private ChucVuRepository chucVuRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Page<NhanVien> getAll(Pageable pageable) {
        return nhanVienRepository.findAll(pageable);
    }

    @Override
    public NhanVien findById(Integer id) {
        Optional<NhanVien> nhanVien = nhanVienRepository.findById(id);
        if (nhanVien.isPresent()){
            return nhanVien.get();
        }
        return null;
    }

    @Override
    public Page<NhanVien> findAllByName(String name, Pageable pageable) {
        return nhanVienRepository.findAllByTen("%" + name + "%", pageable);
    }

    @Override
    public NhanVien findByName(String name) {
        Optional<NhanVien> nhanVien = nhanVienRepository.findByName(name);
        if (nhanVien.isPresent()){
            return nhanVien.get();
        }
        return null;
    }

    @Override
    public NhanVien create(NhanVien nhanVien) {
        nhanVien.setId(null);

        return nhanVienRepository.save(nhanVien);
    }

    @Override
    public NhanVien update(NhanVien nhanVien, Integer id) {
        nhanVien.setId(id);
        return nhanVienRepository.save(nhanVien);
    }

    @Override
    public NhanVien delete(Integer id) {
        Optional<NhanVien> ms = nhanVienRepository.findById(id);
        if (ms.isPresent()) {
            ms.get().setTrangThai(0);
            return nhanVienRepository.save(ms.get());
        }
        return null;
    }

    //dang ky cho nhan vien
    @Override
    public NhanVien insertNhanVien(NhanVienRequest nhanVienRequest) {
        NhanVien nhanVien = new NhanVien();
        nhanVien.setId(null);
        nhanVien.setHoTen(nhanVienRequest.getHoTen());
        nhanVien.setAnhDaiDien(nhanVienRequest.getAnhDaiDien());
        nhanVien.setEmail(nhanVienRequest.getEmail());
        nhanVien.setMatKhau(passwordEncoder.encode(nhanVienRequest.getMatKhau()));
        nhanVien.setSoDienThoai(nhanVienRequest.getSoDienThoai());
        nhanVien.setGioiTinh(nhanVienRequest.getGioiTinh());
        nhanVien.setNgaySinh(nhanVienRequest.getNgaySinh());
        nhanVien.setDiaChi(nhanVienRequest.getDiaChi());
        nhanVien.setTrangThai(1);
        Set<ChucVu> roles = new HashSet<>();
        roles.add(chucVuRepository.findByTenChucVu(nhanVienRequest.getRole()));// TODO role ADMIN, STAFF
        nhanVien.setChucVus(roles);
        validateAccount(nhanVienRequest); // kiem tra role có tồn tại và mail có tồn tại không
        return nhanVienRepository.save(nhanVien);
    }

    //trả về thông tin người dùng
    @Override
    public NhanVien authenticateUser(LoginRequest loginRequest) {
        NhanVien nhanVien = nhanVienRepository.findByEmail(loginRequest.getEmail()); // truy vấn dữ liệu để lấy thông tin người dùng dựa trên email
        if (nhanVien != null && passwordEncoder.matches(loginRequest.getPassword(), nhanVien.getMatKhau())) { // kiểm tra mật khẩu có đúng hay không
            return nhanVien; // trả về thông tin nhân viên
        }
        throw new UsernameNotFoundException("User not found");
    }

    // check dữ liệu khi đăng ký
    private void validateAccount(NhanVienRequest nhanVienRequest) {

        // check role có tồn tồn tại trong cơ sở dữ liệu hay không
        List<String> roles = chucVuRepository.findAll()
                .stream()
                .map(ChucVu::getTenChucVu)
                .collect(Collectors.toList());

        if (!roles.contains(nhanVienRequest.getRole())) {
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Invalid role");
        }

        NhanVien user = nhanVienRepository.findByEmail(nhanVienRequest.getEmail());

        // kiểm tra email tồn tại hay chưa nếu có sẽ ném ngoại lệ
        if (!ObjectUtils.isEmpty(user)) {
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Email had existed!!!");
        }
        // check các trường trong nhanvien request
//        if(!ObjectUtils.isEmpty(nhanVienRequest)){
//            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Request data not found!");
//        }
//
//        try {
//            if(!ObjectUtils.isEmpty(nhanVienRequest.checkProperties())){
//                throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Request data not found!");
//            }
//        }catch (IllegalAccessException e){
//            throw new BaseException(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()), "Service Unavailable");
//        }
    }

    @Override
    public NhanVien registerAccount(RegisterRequest registerRequest) {
        NhanVien nv = nhanVienRepository.findByEmail(registerRequest.getEmail());
        if (nv == null) {
            return nhanVienRepository.
                    save(NhanVien.builder()
                                 .hoTen(registerRequest.getFullName())
                                 .email(registerRequest.getEmail())
                                 .matKhau(passwordEncoder.encode(registerRequest.getPassword()))
                                 .build()
                    );
        } else {
            return null;
        }
    }
}
