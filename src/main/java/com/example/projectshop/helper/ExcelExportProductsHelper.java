package com.example.projectshop.helper;

import com.example.projectshop.domain.ChiTietSanPham;
import com.example.projectshop.domain.SanPham;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class ExcelExportProductsHelper {
//    public    static String[] HEADERS = {"id","Ten","anh Chinh","Mô Tả","Trạng Thái","Thương Hiệu","Xuất Xứ","Danh Mục","Ngày Tạo","Ngày Cập Nhật"};
//
//    public static String SHEET = "Product";
//
//
//    public static ByteArrayInputStream SanPhamToEcexl(List<SanPham> sanPhams){
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
//            for (SanPham sp : sanPhams) {
//               Row row= motsheet.createRow(rowInx++);
//                String tenthuonghieu = sp.getThuongHieu().getTen();
//                    String tenxuatxu = sp.getXuatXu().getTen();
//                    String tendanhmuc = sp.getDanhMuc().getTen();
//                CellStyle dateStyle = work.createCellStyle();
//                dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy/MM/dd"));
//                row.createCell(0).setCellValue(sp.getId());
//                row.createCell(1).setCellValue(sp.getTen());
//                row.createCell(2).setCellValue(sp.getAnhChinh());
//                row.createCell(3).setCellValue(sp.getMoTa());
//                row.createCell(4).setCellValue(sp.getTrangThai());
//                row.createCell(5).setCellValue(tenthuonghieu);
//                row.createCell(6).setCellValue(tenxuatxu);
//                row.createCell(7).setCellValue(tendanhmuc);
//                Cell cellngaytao = row.createCell(8);
//                cellngaytao.setCellValue(sp.getNgayTao());
//                 cellngaytao.setCellStyle(dateStyle);
//
//                Cell cell1= row.createCell(9);
//                cell1.setCellValue(sp.getNgayCapNhat());
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
