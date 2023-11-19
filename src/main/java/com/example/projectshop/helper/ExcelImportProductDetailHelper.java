package com.example.projectshop.helper;


import com.example.projectshop.domain.*;
import com.example.projectshop.repository.*;
import com.example.projectshop.service.impl.MauSacSeviceImpl;
import org.apache.poi.ss.formula.atp.Switch;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;

//   NHAP EXCEL CTSP
public class ExcelImportProductDetailHelper {

//    public static MauSacRepository mauSacRepository;
//    public static ChiTietSanPhamRepository chiTietSanPhamRepository;

    public static boolean isValidExcelFile(MultipartFile file) {

        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }


    public static List<ChiTietSanPham> getProductDetailDataFromExcel(InputStream inputStream, MauSacRepository mauSacRepository, KichCoRepository kichCoRepository,
                                                                     ChatLieuGiayRepository chatLieuGiayRepository, ChatLieuDeGiayRepository chatLieuDeGiayRepository,SanPhamRepository sanPhamRepository) {
        List<ChiTietSanPham> chiTietSanPhams = new ArrayList<>();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("ProductDetail");
            int rowIndex = 0;
            for (Row row : sheet) {
                if ((rowIndex == 0)) {
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                ChiTietSanPham ctsp = new ChiTietSanPham();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex) {
                        case 0 -> ctsp.setId((int) cell.getNumericCellValue());
                        case 1 -> ctsp.setMa(cell.getStringCellValue());
                        case 2 -> {
                            ctsp.setSoLuong((int) cell.getNumericCellValue());
                        }
                        case 3 -> {
                            String giabanstring = cell.getStringCellValue();
                            BigDecimal epkieu = new BigDecimal(giabanstring);
                            ctsp.setGiaBan(epkieu);
                        }
                        case 4 -> {
                            java.util.Date dateUtil = cell.getDateCellValue();
                            Date datesql = new Date(dateUtil.getTime());
                            ctsp.setNgayTao(datesql);
                        }
                        case 5 -> ctsp.setTrangThai((int) cell.getNumericCellValue());

                        case 6 -> {
                            List<MauSac> mauSacs = mauSacRepository.findAll();

                            String mausaccell = cell.getStringCellValue();
                            for (MauSac x : mauSacs) {
                                if (x.getTen().equals(mausaccell)) {
                                    ctsp.setMauSac(x);
                                    break;
                                } else {
                                    Map.of("Error", "let check Excel !");
                                    ctsp.setMauSac(null);
                                }
                            }
                        }
                        case 7 -> {
                            List<KichCo> kichCos = kichCoRepository.findAll();
                            int kichcoint = (int) cell.getNumericCellValue();
                            for (KichCo c : kichCos) {
                                if (c.getSize().equals(kichcoint)) {
                                    ctsp.setKichCo(c);
                                    break;
                                } else {
                                    Map.of("error", "Let check excel");
                                    ctsp.setKichCo(null);
                                }
                            }

                        }
                        case 8 -> {
                            List<ChatLieuGiay> chatLieuGiays = chatLieuGiayRepository.findAll();
                            String chatlieugiay = cell.getStringCellValue();
                            for (ChatLieuGiay x : chatLieuGiays) {
                                if (x.getTen().equals(chatlieugiay)) {
                                    ctsp.setChatLieuGiay(x);
                                    break;
                                } else {
                                    Map.of("error", "Let check excel");
                                    ctsp.setChatLieuGiay(null);
                                }
                            }
                        }
                        case 9 -> {
                            List<ChatLieuDeGiay> deGiays = chatLieuDeGiayRepository.findAll();
                            String degiay = cell.getStringCellValue();
                            for (ChatLieuDeGiay x : deGiays) {
                                if (x.getTen().equals(degiay)) {
                                    ctsp.setChatLieuDeGiay(x);
                                    break;
                                } else {
                                    Map.of("error", "Let check excel");
                                    ctsp.setChatLieuDeGiay(null);
                                }
                            }
                        }
                        case 10 ->{
                            List<SanPham> sanPhams = sanPhamRepository.findAll();
                            String tensanpham = cell.getStringCellValue();
                            for (SanPham x : sanPhams) {
                                if (x.getTen().equals(tensanpham)) {
                                    ctsp.setSanPham(x);
                                    break;
                                } else {
                                    Map.of("error", "Let check excel");
                                    ctsp.setSanPham(null);
                                }
                            }

                        }
                        case 11 -> {
                            java.util.Date dateUtil = cell.getDateCellValue();
                            Date date = new Date(dateUtil.getTime());
                            ctsp.setNgayCapNhat(date);
                        }
                        default -> {
                        }
                    }
                    cellIndex++;
                }
                chiTietSanPhams.add(ctsp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chiTietSanPhams;
    }


}
