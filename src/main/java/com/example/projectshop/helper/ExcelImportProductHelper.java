package com.example.projectshop.helper;


import com.example.projectshop.domain.DanhMuc;
import com.example.projectshop.domain.SanPham;
import com.example.projectshop.domain.ThuongHieu;
import com.example.projectshop.domain.Xuatxu;
import com.example.projectshop.repository.DanhMucRepository;
import com.example.projectshop.repository.ThuongHieuRepository;
import com.example.projectshop.repository.XuatXuRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ExcelImportProductHelper {

    public static boolean isValidExcelFile(MultipartFile file) {

        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }


    public static List<SanPham> getProductsDataFromExcel(InputStream inputStream, ThuongHieuRepository thuongHieuRepository, XuatXuRepository xuatXuRepository,
                                                         DanhMucRepository danhMucRepository) {
        List<SanPham> sanPhamlistto = new ArrayList<>();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("Product");
            int rowIndex = 0;
            for (Row row : sheet) {
                if ((rowIndex == 0)) {
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                SanPham sanphams = new SanPham();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex) {

                        case 0 -> {
                            System.out.println("ten" + cell.getStringCellValue());

                            if (!cell.getStringCellValue().equals(null)) {
                                sanphams.setTen(cell.getStringCellValue());

                            }

                        }
                        case 1 -> {
                            System.out.println("anh chinh" + cell.getStringCellValue());
                            sanphams.setAnhChinh(cell.getStringCellValue());

                        }
                        case 2 -> {
                            System.out.println("mo ta " + cell.getStringCellValue());
                            sanphams.setMoTa(cell.getStringCellValue());

                        }
                        case 3 -> {
                            System.out.println("trang thai" + cell.getNumericCellValue());
                            sanphams.setTrangThai((int) cell.getNumericCellValue());


                        }
                        case 4 -> {
                            List<ThuongHieu> thuongHieuList = thuongHieuRepository.findAll();
                            String thuonghieuten = cell.getStringCellValue();
                            int check = 0;
                            for (ThuongHieu th : thuongHieuList) {
                                if (th.getTen().equals(thuonghieuten)) {
                                    sanphams.setThuongHieu(th);
                                    check = 1;
                                    System.out.println("thuong hieu" + th.getTen());
                                    break;
                                }

                            }
                            if (check == 0) {
                                Map.of("error", "Let check excel");
                                sanphams.setThuongHieu(null);
                            }
                        }
                        case 5 -> {
                            List<Xuatxu> xuatxuList = xuatXuRepository.findAll();
                            String xuatxuten = cell.getStringCellValue();
                            int check = 0;
                            for (Xuatxu x : xuatxuList) {
                                if (x.getTen().equals(xuatxuten)) {
                                    sanphams.setXuatXu(x);
                                    check = 1;
                                    System.out.println("xuat xu" + x.getTen());
                                    break;
                                }
                            }
                            if (check == 0) {
                                Map.of("error", "Let check Excel");
                                sanphams.setXuatXu(null);
                            }

                        }
                        case 6 -> {
                            List<DanhMuc> danhMucList = danhMucRepository.findAll();
                            String danhmucten = cell.getStringCellValue();
                            int check = 0;
                            for (DanhMuc x : danhMucList) {
                                if (x.getTen().equals(danhmucten)) {
                                    sanphams.setDanhMuc(x);
                                    check = 1;
                                    System.out.println("danh muc" + x.getTen());
                                    break;
                                }

                            }
                            if (check == 0) {
                                Map.of("error", "Let check excel");
                                sanphams.setDanhMuc(null);
                            }

                        }
                        case 7 -> {
                            java.util.Date dateUtil = cell.getDateCellValue();
                            java.sql.Date date = new java.sql.Date(dateUtil.getTime());
                            sanphams.setNgayTao(date);
                        }
                        case 8 -> {
                            java.util.Date dateUtil = cell.getDateCellValue();
                            java.sql.Date date = new java.sql.Date(dateUtil.getTime());
                            sanphams.setNgayCapNhat(date);
                        }
                        default -> {
                        }
                    }
                    cellIndex++;

                }
                sanPhamlistto.add(sanphams);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return sanPhamlistto;
    }

}
