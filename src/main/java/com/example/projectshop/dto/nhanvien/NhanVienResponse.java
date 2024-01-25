package com.example.projectshop.dto.nhanvien;

import com.example.projectshop.domain.ChucVu;
import com.example.projectshop.domain.NhanVien;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NhanVienResponse {

    private Integer id;

    private String hoTen;

    private String anhDaiDien;

    private String email;

    private String soDienThoai;

    private Boolean gioiTinh;

    private String ngaySinh;

    private String diaChi;

    private Integer trangThai;

    private String roles;

    public static NhanVienResponse fromNhanVien(NhanVien nhanVien) {
        NhanVienResponse response = new NhanVienResponse();
        response.setId(nhanVien.getId());
        response.setHoTen(nhanVien.getHoTen());
        response.setAnhDaiDien(nhanVien.getAnhDaiDien());
        response.setEmail(nhanVien.getEmail());
        response.setSoDienThoai(nhanVien.getSoDienThoai());
        response.setGioiTinh(nhanVien.getGioiTinh());
        response.setNgaySinh(nhanVien.getNgaySinh());
        response.setDiaChi(nhanVien.getDiaChi());
        response.setTrangThai(nhanVien.getTrangThai());

        String role = nhanVien.getChucVus().stream()
                .findFirst()
                .map(ChucVu::getTenChucVu)
                .orElse(null);
        response.setRoles(role);

        return response;
    }

}
