package com.example.projectshop.service.impl;

import com.example.projectshop.dto.thongke.ThongKeDanhMuc;
import com.example.projectshop.dto.thongke.ThongKeDoanhThu;
import com.example.projectshop.dto.thongke.ThongKeHoaDon;
import com.example.projectshop.dto.thongke.ThongKeSanPham;
import com.example.projectshop.repository.ThongKeRepository;
import com.example.projectshop.service.IThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ThongKeImpl implements IThongKeService {
    @Autowired
    private ThongKeRepository thongKeRepo;

    private LocalDate currentDate = LocalDate.now();

    @Override
    public ThongKeHoaDon thongKeHoaDon() {
        ThongKeHoaDon thongKeHoaDon = new ThongKeHoaDon();
        thongKeHoaDon.setTongDonHang(thongKeRepo.demHoaDonByTrangThai(null));
        thongKeHoaDon.setDonHangTaiQuay(thongKeRepo.demHoaDonByTrangThai(1));
        thongKeHoaDon.setChoXacNhan(thongKeRepo.demHoaDonByTrangThai(2));
        thongKeHoaDon.setChoVanChuyen(thongKeRepo.demHoaDonByTrangThai(3));
        thongKeHoaDon.setDangGiaoHang(thongKeRepo.demHoaDonByTrangThai(4));
        thongKeHoaDon.setDaGiaoHang(thongKeRepo.demHoaDonByTrangThai(5));
        thongKeHoaDon.setDaHuy(thongKeRepo.demHoaDonByTrangThai(6));
        thongKeHoaDon.setDoiHang(thongKeRepo.demHoaDonByTrangThai(7));
        return thongKeHoaDon;
    }


    @Override
    public List<ThongKeDoanhThu> thongKeDoanhThu7NgayTruoc() {
        List<Object[]> revenueData = thongKeRepo.thongKeDoanhThu7NgayTruoc();// khai báo 1 list dữ liệu trả ra từ db

        List<Object[]> resultData = new ArrayList<>(); // khai báo 1 list Object[] để xử lý
        Calendar calendar = Calendar.getInstance(); // lấy ra ngày giờ của hệ thống máy tính
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // dùng để format dữ liệu về yyyy-MM

        for (int i = 0; i < 7; i++) {
            Date day = calendar.getTime(); // chuyển đổi từ calendar sang date
            boolean found = false; // biến check

            for (Object[] data : revenueData) {
                if (dateFormat.format(day).equals(data[0])) {
                    resultData.add(data); // add đối tượng data từ db trả về vào list khai bảo ở trên
                    found = true;
                    break;
                }
            }

            if (found == false) {
                resultData.add(new Object[]{dateFormat.format(day), 0, 0});
            }

            calendar.add(Calendar.DAY_OF_MONTH, -1);// sau khi chạy xong 1 vòng thì sẽ set lại ngày
        }


        List<ThongKeDoanhThu> thongKeDoanhThus = new ArrayList<>();// convert từ Object[] sang ThongKeDoanhThu
        for (Object[] x : resultData) {
            ThongKeDoanhThu thongKeDoanhThu = new ThongKeDoanhThu();
            thongKeDoanhThu.setNgayThang(x[0].toString());
            thongKeDoanhThu.setTongDoanhThu(new BigDecimal(x[1].toString()));
            thongKeDoanhThu.setTongDonHang(Integer.valueOf(x[2].toString()));
            thongKeDoanhThus.add(thongKeDoanhThu);
        }
        Collections.sort(thongKeDoanhThus, (tkdt1, tkdt2) -> tkdt1.getNgayThang().compareTo(tkdt2.getNgayThang()));
        return thongKeDoanhThus;
    }

    @Override
    public List<ThongKeDoanhThu> thongKeDoanhThu28NgayTruoc() {
        List<Object[]> revenueData = thongKeRepo.thongKeDoanhThu28NgayTruoc();// khai báo 1 list dữ liệu trả ra từ db

        List<Object[]> resultData = new ArrayList<>(); // khai báo 1 list Object[] để xử lý
        Calendar calendar = Calendar.getInstance(); // lấy ra ngày giờ của hệ thống máy tính
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // dùng để format dữ liệu về yyyy-MM

        for (int i = 0; i < 28; i++) {
            Date day = calendar.getTime(); // chuyển đổi từ calendar sang date
            boolean found = false; // biến check

            for (Object[] data : revenueData) {
                if (dateFormat.format(day).equals(data[0])) {
                    resultData.add(data); // add đối tượng data từ db trả về vào list khai bảo ở trên
                    found = true;
                    break;
                }
            }

            if (found == false) {
                resultData.add(new Object[]{dateFormat.format(day), 0, 0});
            }

            calendar.add(Calendar.DAY_OF_MONTH, -1);// sau khi chạy xong 1 vòng thì sẽ set lại ngày
        }


        List<ThongKeDoanhThu> thongKeDoanhThus = new ArrayList<>();// convert từ Object[] sang ThongKeDoanhThu
        for (Object[] x : resultData) {
            ThongKeDoanhThu thongKeDoanhThu = new ThongKeDoanhThu();
            thongKeDoanhThu.setNgayThang(x[0].toString());
            thongKeDoanhThu.setTongDoanhThu(new BigDecimal(x[1].toString()));
            thongKeDoanhThu.setTongDonHang(Integer.valueOf(x[2].toString()));
            thongKeDoanhThus.add(thongKeDoanhThu);
        }
        Collections.sort(thongKeDoanhThus, (tkdt1, tkdt2) -> tkdt1.getNgayThang().compareTo(tkdt2.getNgayThang()));
        return thongKeDoanhThus;
    }

    @Override
    public List<ThongKeDoanhThu> thongKeDoanhThu1NamTruoc() {
        List<Object[]> revenueData = thongKeRepo.thongKeDoanhThu1NamTruoc();// khai báo 1 list dữ liệu trả ra từ db

        List<Object[]> resultData = new ArrayList<>(); // khai báo 1 list Object[] để xử lý
        Calendar calendar = Calendar.getInstance(); // lấy ra ngày giờ của hệ thống máy tính
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM"); // dùng để format dữ liệu về yyyy-MM

        for (int i = 0; i < 12; i++) {
            Date month = calendar.getTime(); // chuyển đổi từ calendar sang date
            boolean found = false; // biến check

            for (Object[] data : revenueData) {
                if (dateFormat.format(month).equals(data[0])) {
                    resultData.add(data); // add đối tượng data từ db trả về vào list khai bảo ở trên
                    found = true;
                    continue;
                }
            }

            if (found == false) {
                resultData.add(new Object[]{dateFormat.format(month), 0, 0});
            }

            calendar.add(Calendar.MONTH, -1);// sau khi chạy xong 1 vòng thì sẽ set lại tháng
        }


        List<ThongKeDoanhThu> thongKeDoanhThus = new ArrayList<>();// convert từ Object[] sang ThongKeDoanhThu
        for (Object[] x : resultData) {
            ThongKeDoanhThu thongKeDoanhThu = new ThongKeDoanhThu();
            thongKeDoanhThu.setNgayThang(x[0].toString());
            thongKeDoanhThu.setTongDoanhThu(new BigDecimal(x[1].toString()));
            thongKeDoanhThu.setTongDonHang(Integer.valueOf(x[2].toString()));
            thongKeDoanhThus.add(thongKeDoanhThu);
        }
        Collections.sort(thongKeDoanhThus, (tkdt1, tkdt2) -> tkdt1.getNgayThang().compareTo(tkdt2.getNgayThang()));
        return thongKeDoanhThus;
    }

    @Override
    public List<ThongKeDoanhThu> thongKeDoanhThu6ThangTruoc() {
        List<Object[]> revenueData = thongKeRepo.thongKeDoanhThu6ThangTruoc();// khai báo 1 list dữ liệu trả ra từ db

        List<Object[]> resultData = new ArrayList<>(); // khai báo 1 list Object[] để xử lý
        Calendar calendar = Calendar.getInstance(); // lấy ra ngày giờ của hệ thống máy tính
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM"); // dùng để format dữ liệu về yyyy-MM

        for (int i = 0; i < 6; i++) {
            Date month = calendar.getTime(); // chuyển đổi từ calendar về date
            boolean found = false; // biến check

            for (Object[] data : revenueData) {
                if (dateFormat.format(month).equals(data[0])) {
                    resultData.add(data); // add đối tượng data từ db trả về vào list khai bảo ở trên
                    found = true;
                    break;
                }
            }

            if (found == false) {
                resultData.add(new Object[]{dateFormat.format(month), 0, 0});
            }

            calendar.add(Calendar.MONTH, -1);// sau khi chạy xong 1 vòng thì sẽ set lại tháng
        }


        List<ThongKeDoanhThu> thongKeDoanhThus = new ArrayList<>();// convert từ Object[] sang ThongKeDoanhThu
        for (Object[] x : resultData) {
            ThongKeDoanhThu thongKeDoanhThu = new ThongKeDoanhThu();
            thongKeDoanhThu.setNgayThang(x[0].toString());
            thongKeDoanhThu.setTongDoanhThu(new BigDecimal(x[1].toString()));
            thongKeDoanhThu.setTongDonHang(Integer.valueOf(x[2].toString()));
            thongKeDoanhThus.add(thongKeDoanhThu);
        }
        Collections.sort(thongKeDoanhThus, (tkdt1, tkdt2) -> tkdt1.getNgayThang().compareTo(tkdt2.getNgayThang()));
        return thongKeDoanhThus;
    }

    @Override
    public List<ThongKeDoanhThu> thongKeDoanhThuTheoKhoang(java.sql.Date startDate, java.sql.Date endDate) {
        List<ThongKeDoanhThu> thongKeDoanhThus = new ArrayList<>();
        // nếu 1 trong 2 tham số hoặc cả 2 tham số = null thì trả về doanh thu ngày hiện tại của hệ thông máy tính
        if (startDate == null || endDate == null || startDate == null && endDate == null){
            for (Object[] x : thongKeRepo.thongKeDoanhThuNgayHienTai()) {
                ThongKeDoanhThu thongKeDoanhThu = new ThongKeDoanhThu();
                thongKeDoanhThu.setNgayThang(x[0].toString());
                thongKeDoanhThu.setTongDoanhThu(new BigDecimal(x[1].toString()));
                thongKeDoanhThu.setTongDonHang(Integer.valueOf(x[2].toString()));
                thongKeDoanhThus.add(thongKeDoanhThu);
            }
            return thongKeDoanhThus;
        }//

        // nếu cả 2 tham số không null thì trả về doanh thu theo tham số truyền vào
        List<Object[]> revenueData = thongKeRepo.thongKeDoanhThuTheoKhoang(startDate, endDate);// khai báo 1 list dữ liệu trả ra từ db

        List<Object[]> resultData = new ArrayList<>(); // khai báo 1 list Object[] để xử lý
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // dùng để format dữ liệu về yyyy-MM
        Date date = endDate;
        // Tính số ngày giữa hai ngày
        long soNgay = Math.abs((startDate.getTime() - endDate.getTime()) / (24 * 60 * 60 * 1000));


        for (int i = 0; i <= soNgay; i++) {
            boolean found = false; // biến check
            for (Object[] data : revenueData) {
                if (dateFormat.format(date).equals(data[0])) {
                    resultData.add(data); // add đối tượng data từ db trả về vào list khai bảo ở trên
                    found = true;
                    break;
                }
            }

            if (found == false) {
                resultData.add(new Object[]{dateFormat.format(date), 0, 0});
            }
            date.setTime(endDate.getTime() - (24 * 60 * 60 * 1000));// sau khi chạy xong 1 vòng thì sẽ set lại ngày
        }//

        // convert từ list<Object[] sang list<ThongKeDoanhTHu>
        for (Object[] x : resultData) {
            ThongKeDoanhThu thongKeDoanhThu = new ThongKeDoanhThu();
            thongKeDoanhThu.setNgayThang(x[0].toString());
            thongKeDoanhThu.setTongDoanhThu(new BigDecimal(x[1].toString()));
            thongKeDoanhThu.setTongDonHang(Integer.valueOf(x[2].toString()));
            thongKeDoanhThus.add(thongKeDoanhThu);
        }//
        Collections.sort(thongKeDoanhThus, (tkdt1, tkdt2) -> tkdt1.getNgayThang().compareTo(tkdt2.getNgayThang()));
        return thongKeDoanhThus;
    }

    @Override
    public List<ThongKeSanPham> thongKeSanPham7NgayTruoc() {
        // convert từ list<Object[] sang list<ThongKeDoanhTHu>
        List<ThongKeSanPham> thongKeSanPhams = new ArrayList<>();
        for (Object[] x : thongKeRepo.thongKeSanPham7NgayTruoc()) {
            ThongKeSanPham thongKeSanPham = ThongKeSanPham.builder()
                    .tenSanPham(x[0].toString())
                    .soLuong(Integer.valueOf(x[1].toString()))
                    .doanhThu(new BigDecimal(x[2].toString()))
                    .build();
            thongKeSanPhams.add(thongKeSanPham);
        }//
       return thongKeSanPhams;
    }

    @Override
    public List<ThongKeSanPham> thongKeSanPham28NgayTruoc() {
        // convert từ list<Object[] sang list<ThongKeDoanhTHu>
        List<ThongKeSanPham> thongKeSanPhams = new ArrayList<>();
        for (Object[] x : thongKeRepo.thongKeSanPham28NgayTruoc()) {
            ThongKeSanPham thongKeSanPham = ThongKeSanPham.builder()
                    .tenSanPham(x[0].toString())
                    .soLuong(Integer.valueOf(x[1].toString()))
                    .doanhThu(new BigDecimal(x[2].toString()))
                    .build();
            thongKeSanPhams.add(thongKeSanPham);
        }//
        return thongKeSanPhams;
    }

    @Override
    public List<ThongKeSanPham> thongKeSanPham1NamTruoc() {
        // convert từ list<Object[] sang list<ThongKeDoanhTHu>
        List<ThongKeSanPham> thongKeSanPhams = new ArrayList<>();
        for (Object[] x : thongKeRepo.thongKeSanPham1NamTruoc()) {
            ThongKeSanPham thongKeSanPham = ThongKeSanPham.builder()
                    .tenSanPham(x[0].toString())
                    .soLuong(Integer.valueOf(x[1].toString()))
                    .doanhThu(new BigDecimal(x[2].toString()))
                    .build();
            thongKeSanPhams.add(thongKeSanPham);
        }//
        return thongKeSanPhams;
    }

    @Override
    public List<ThongKeSanPham> thongKeSanPham6ThangTruoc() {
        // convert từ list<Object[] sang list<ThongKeDoanhTHu>
        List<ThongKeSanPham> thongKeSanPhams = new ArrayList<>();
        for (Object[] x : thongKeRepo.thongKeSanPham6ThangTruoc()) {
            ThongKeSanPham thongKeSanPham = ThongKeSanPham.builder()
                    .tenSanPham(x[0].toString())
                    .soLuong(Integer.valueOf(x[1].toString()))
                    .doanhThu(new BigDecimal(x[2].toString()))
                    .build();
            thongKeSanPhams.add(thongKeSanPham);
        }//
        return thongKeSanPhams;
    }

    @Override
    public List<ThongKeSanPham> thongKeSanPhamTheoKhoang(java.sql.Date startDate, java.sql.Date endDate) {
        List<ThongKeSanPham> thongKeSanPhams = new ArrayList<>();
        // nếu 1 trong 2 tham số hoặc cả 2 tham số = null thì trả về doanh thu ngày hiện tại của hệ thông máy tính
        if (startDate == null || endDate == null || startDate == null && endDate == null){
            for (Object[] x : thongKeRepo.tongThongKeSanPham()) {
                ThongKeSanPham thongKeSanPham = ThongKeSanPham.builder()
                        .tenSanPham(x[0].toString())
                        .soLuong(Integer.valueOf(x[1].toString()))
                        .doanhThu(new BigDecimal(x[2].toString()))
                        .build();
                thongKeSanPhams.add(thongKeSanPham);
            }
            return thongKeSanPhams;
        }//

        // nếu cả 2 tham số không null thì trả về doanh thu theo tham số truyền vào
        List<Object[]> productData = thongKeRepo.thongKeSanPhamTheoKhoang(startDate, endDate);// khai báo 1 list dữ liệu trả ra từ db
        // convert từ list<Object[] sang list<ThongKeDoanhTHu>
        for (Object[] x : productData) {
            ThongKeSanPham thongKeSanPham = ThongKeSanPham.builder()
                    .tenSanPham(x[0].toString())
                    .soLuong(Integer.valueOf(x[1].toString()))
                    .doanhThu(new BigDecimal(x[2].toString()))
                    .build();
            thongKeSanPhams.add(thongKeSanPham);
        }//
        return thongKeSanPhams;
    }

    @Override
    public List<ThongKeDanhMuc> thongKeDanhMuc7NgayTruoc() {
        // convert từ list<Object[] sang list<ThongKeDoanhTHu>
        List<ThongKeDanhMuc> thongKeDanhMucs = new ArrayList<>();
        for (Object[] x : thongKeRepo.thongKeDanhMuc7NgayTruoc()) {
            ThongKeDanhMuc thongKeDanhMuc = ThongKeDanhMuc.builder()
                    .tenDanhMuc(x[0].toString())
                    .soLuong(Integer.valueOf(x[1].toString()))
                    .doanhThu(new BigDecimal(x[2].toString()))
                    .build();
            thongKeDanhMucs.add(thongKeDanhMuc);
        }//
        return thongKeDanhMucs;
    }

    @Override
    public List<ThongKeDanhMuc> thongKeDanhMuc28NgayTruoc() {
        // convert từ list<Object[] sang list<ThongKeDoanhTHu>
        List<ThongKeDanhMuc> thongKeDanhMucs = new ArrayList<>();
        for (Object[] x : thongKeRepo.thongKeDanhMuc28NgayTruoc()) {
            ThongKeDanhMuc thongKeDanhMuc = ThongKeDanhMuc.builder()
                    .tenDanhMuc(x[0].toString())
                    .soLuong(Integer.valueOf(x[1].toString()))
                    .doanhThu(new BigDecimal(x[2].toString()))
                    .build();
            thongKeDanhMucs.add(thongKeDanhMuc);
        }//
        return thongKeDanhMucs;
    }

    @Override
    public List<ThongKeDanhMuc> thongKeDanhMuc1NamTruoc() {
        // convert từ list<Object[] sang list<ThongKeDoanhTHu>
        List<ThongKeDanhMuc> thongKeDanhMucs = new ArrayList<>();
        for (Object[] x : thongKeRepo.thongKeDanhMuc12ThangTruoc()) {
            ThongKeDanhMuc thongKeDanhMuc = ThongKeDanhMuc.builder()
                    .tenDanhMuc(x[0].toString())
                    .soLuong(Integer.valueOf(x[1].toString()))
                    .doanhThu(new BigDecimal(x[2].toString()))
                    .build();
            thongKeDanhMucs.add(thongKeDanhMuc);
        }//
        return thongKeDanhMucs;
    }

    @Override
    public List<ThongKeDanhMuc> thongKeDanhMuc6ThangTruoc() {
        // convert từ list<Object[] sang list<ThongKeDoanhTHu>
        List<ThongKeDanhMuc> thongKeDanhMucs = new ArrayList<>();
        for (Object[] x : thongKeRepo.thongKeDanhMuc6ThangTruoc()) {
            ThongKeDanhMuc thongKeDanhMuc = ThongKeDanhMuc.builder()
                    .tenDanhMuc(x[0].toString())
                    .soLuong(Integer.valueOf(x[1].toString()))
                    .doanhThu(new BigDecimal(x[2].toString()))
                    .build();
            thongKeDanhMucs.add(thongKeDanhMuc);
        }//
        return thongKeDanhMucs;
    }

    @Override
    public List<ThongKeDanhMuc> thongKeDanhMucTheoKhoang(java.sql.Date startDate, java.sql.Date endDate) {
        List<ThongKeDanhMuc> thongKeDanhMucs = new ArrayList<>();
        // nếu 1 trong 2 tham số hoặc cả 2 tham số = null thì trả về doanh thu ngày hiện tại của hệ thông máy tính
        if (startDate == null || endDate == null || startDate == null && endDate == null){
            for (Object[] x : thongKeRepo.tongThongKeDanhMuc()) {
                ThongKeDanhMuc thongKeDanhMuc = ThongKeDanhMuc.builder()
                        .tenDanhMuc(x[0].toString())
                        .soLuong(Integer.valueOf(x[1].toString()))
                        .doanhThu(new BigDecimal(x[2].toString()))
                        .build();
                thongKeDanhMucs.add(thongKeDanhMuc);
            }
            return thongKeDanhMucs;
        }//

        // nếu cả 2 tham số không null thì trả về doanh thu theo tham số truyền vào
        List<Object[]> categoryData = thongKeRepo.thongKeDanhMucTheoKhoang(startDate, endDate);// khai báo 1 list dữ liệu trả ra từ db
        // convert từ list<Object[] sang list<ThongKeDoanhTHu>
        for (Object[] x : categoryData) {
            ThongKeDanhMuc thongKeDanhMuc = ThongKeDanhMuc.builder()
                    .tenDanhMuc(x[0].toString())
                    .soLuong(Integer.valueOf(x[1].toString()))
                    .doanhThu(new BigDecimal(x[2].toString()))
                    .build();
            thongKeDanhMucs.add(thongKeDanhMuc);
        }//
        return thongKeDanhMucs;
    }
}
