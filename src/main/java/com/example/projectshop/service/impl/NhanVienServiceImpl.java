package com.example.projectshop.service.impl;

import com.example.projectshop.domain.ChucVu;
import com.example.projectshop.domain.NhanVien;
import com.example.projectshop.dto.BaseResponse;
import com.example.projectshop.dto.nhanvien.NhanVienRequest;
import com.example.projectshop.exception.BaseException;
import com.example.projectshop.repository.ChucVuRepository;
import com.example.projectshop.repository.NhanVienRepository;
import com.example.projectshop.service.INhanVienService;
import com.example.projectshop.service.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    public Optional<NhanVien> findById(Integer id) {
        return nhanVienRepository.findById(id);
    }

    @Override
    public Page<NhanVien> findAllByName(String name, Pageable pageable) {
        return nhanVienRepository.findAllByTen("%" + name + "%",pageable);
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
        if(ms.isPresent()){
            ms.get().setTrangThai(0);
            return nhanVienRepository.save(ms.get());
        }
        return null;
    }

    @Override
    public BaseResponse registerAccount(NhanVienRequest nhanVienRequest) {
        BaseResponse response =new BaseResponse();
        validateAccount(nhanVienRequest);
        NhanVien nhanVien = this.insertNhanVien(nhanVienRequest);
        try {
            nhanVienRepository.save(nhanVien);
            response.setCode(String.valueOf(HttpStatus.CREATED.value()));
            response.setMessage("Register account successfully!!!");
        }catch (Exception e){
            response.setCode(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()));
            response.setMessage("Service Unavailable");
            //throw new BaseException(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()), "Service Unavailable");
        }
        return response;
    }

    @Override
    public NhanVien insertNhanVien(NhanVienRequest nhanVienRequest) {
//        NhanVien nhanVien = ObjectMapperUtils.map(nhanVienRequest,NhanVien.class);
        NhanVien nhanVien =new NhanVien();
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
        Set<ChucVu> roles =new HashSet<>();
        roles.add(chucVuRepository.findByTenChucVu(nhanVienRequest.getRole()));
        nhanVien.setChucVus(roles);
        return nhanVien;
    }

    private void validateAccount(NhanVienRequest nhanVienRequest){
        if(ObjectUtils.isEmpty(nhanVienRequest)){
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Request data not found!");
        }

        try {
            if(ObjectUtils.isEmpty(nhanVienRequest.checkProperties())){
                throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Request data not found!");
            }
        }catch (IllegalAccessException e){
            throw new BaseException(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()), "Service Unavailable");
        }

        List<String> roles = chucVuRepository.findAll()
                .stream()
                .map(ChucVu::getTenChucVu)
                .collect(Collectors.toList());
//                .toList();

        if(!roles.contains(nhanVienRequest.getRole())){
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Invalid role");
        }

        NhanVien user = nhanVienRepository.findByEmail(nhanVienRequest.getEmail());

        if(!ObjectUtils.isEmpty(user)){
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Email had existed!!!");
        }
    }
}
