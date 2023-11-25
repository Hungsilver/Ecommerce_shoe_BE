package com.example.projectshop.helper;

import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.MauSac;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

// XUAT EXCEL
public class ExcelExportProductsDetailHelper {
//    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

//    public static String[] HEADERS = {"id", "ma", "So Luong", "Gia Ban", "Ngay Tao", "Trang Thai", "Mau Sac", "Kich Co", "Chat Lieu Giay", "Chat Lieu De Giay", "San Pham", "Ngay Cap Nhat"};
//
//    public static String SHEET = "Product Detail";
//
//    public static ByteArrayInputStream ChiTietSanPhamToEcexl(List<ChiTietSanPham> chiTietSanPhams) {
//
//
//        // tao file execl
//        try (Workbook work = new XSSFWorkbook();
//             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
//
//            CreationHelper creationHelper = work.getCreationHelper();
//
//            // tao sheet
//            Sheet motsheet = work.createSheet(SHEET);
//
//            // set vi tri hang dau tien
//            Row heardersheet = motsheet.createRow(0);
//
//            for (int col = 0; col < HEADERS.length; col++) {
//                Cell cell = heardersheet.createCell(col);
//                cell.setCellValue(HEADERS[col]);
//            }
//            int rowInx = 1;
//
//            for (ChiTietSanPham ctsp : chiTietSanPhams) {
//                Row row = motsheet.createRow(rowInx++);
//
//                BigDecimal giaban = ctsp.getGiaBan();
//
//                String giabanstring = giaban.toString();
//
//                double kichcodouble = ctsp.getKichCo().getSize();
//                String chatlieugiay = ctsp.getChatLieuGiay().getTen();
//                String chatlieudegiay = ctsp.getChatLieuDeGiay().getTen();
//                String sanpham = ctsp.getSanPham().getTen();
//
//                CellStyle dateStyle = work.createCellStyle();
//                dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy/MM/dd"));
//                row.createCell(0).setCellValue(ctsp.getId());
//                row.createCell(1).setCellValue(ctsp.getMa());
//                row.createCell(2).setCellValue(ctsp.getSoLuong());
//                row.createCell(3).setCellValue(giabanstring);
//                Cell cell = row.createCell(4);
//                cell.setCellValue(ctsp.getNgayTao());
//                cell.setCellStyle(dateStyle);
//                row.createCell(5).setCellValue(ctsp.getTrangThai());
//                row.createCell(6).setCellValue(ctsp.getMauSac().getTen());
//                row.createCell(7).setCellValue(kichcodouble);
//                row.createCell(8).setCellValue(chatlieugiay);
//                row.createCell(9).setCellValue(chatlieudegiay);
//                row.createCell(10).setCellValue(sanpham);
//                Cell cell1= row.createCell(11);
//                cell1.setCellValue(ctsp.getNgayCapNhat());
//                cell1.setCellStyle(dateStyle);
//
//
//            }
//            work.write(out);
//            return new ByteArrayInputStream(out.toByteArray());
//        } catch (IOException c) {
//            c.printStackTrace();
//
//            throw new RuntimeException("fail to import data ");
//
//        }
//
//
//    }

}
