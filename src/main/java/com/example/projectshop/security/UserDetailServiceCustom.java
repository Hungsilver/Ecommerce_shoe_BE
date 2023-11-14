package com.example.projectshop.security;

import com.example.projectshop.domain.NhanVien;
import com.example.projectshop.exception.BaseException;
import com.example.projectshop.repository.NhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.ObjectUtils;

import java.util.stream.Collectors;

public class UserDetailServiceCustom implements UserDetailsService {

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetailsCustom userDetailsCustom = getUserDetailsCustom(email);

        if (ObjectUtils.isEmpty(userDetailsCustom)) {
            throw new UsernameNotFoundException("User not found");
        }
        return userDetailsCustom;
    }

    private UserDetailsCustom getUserDetailsCustom(String email) {
        NhanVien user = nhanVienRepository.findByEmail(email);

        if (ObjectUtils.isEmpty(user)) {
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST), "Email not found");
        }

        return new UserDetailsCustom(

                user.getHoTen(),
                user.getAnhDaiDien(),
                user.getEmail(),
                user.getMatKhau(),
                user.getSoDienThoai(),
                user.getGioiTinh(),
                user.getNgaySinh(),
                user.getDiaChi(),
                user.getTrangThai(),
                user.getChucVus().stream()
                        .map(r -> new SimpleGrantedAuthority(r.getTenChucVu()))
                        .collect(Collectors.toList())
        );
    }

}
