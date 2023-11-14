package com.example.projectshop.security;

import com.example.projectshop.domain.ChucVu;
import com.example.projectshop.domain.NhanVien;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDetailsCustom implements UserDetails {

    private String hoTen;

    private String anhDaiDien;

    private String email;

    private String matKhau;

    private String soDienThoai;

    private Byte gioiTinh;

    private String ngaySinh;

    private String diaChi;

    private Integer trangThai;

    private List<GrantedAuthority> authorities;

    public UserDetailsCustom(String hoTen, String anhDaiDien, String email, String matKhau, String soDienThoai, Byte gioiTinh, String ngaySinh, String diaChi, Integer trangThai, List<GrantedAuthority> authorities) {
        this.hoTen = hoTen;
        this.anhDaiDien = anhDaiDien;
        this.email = email;
        this.matKhau = matKhau;
        this.soDienThoai = soDienThoai;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.trangThai = trangThai;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return matKhau;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
