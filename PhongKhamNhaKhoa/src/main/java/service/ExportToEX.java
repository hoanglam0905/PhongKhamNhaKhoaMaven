package service;

import Utils.JDBCUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class ExportToEX {
    public static void exportServiceReport() {
        String filePath = "Revenue\\"+ LocalDate.now()+".xlsx";
        try (Workbook wb = new XSSFWorkbook()) {
            String sql="SELECT\n" +
                    "  DATE(p.preDate) AS `Ngày`,\n" +
                    "  pt.name       AS `Tên bệnh nhân`,\n" +
                    "  e.name        AS `Người bác sĩ`,\n" +
                    "  COALESCE(SUM(dr.quantity * d.price), 0) AS `Tổng tiền thuốc`,\n" +
                    "  COALESCE(SUM(ps.quantity * s.price), 0) AS `Tổng tiền dịch vụ`,\n" +
                    "  COALESCE(SUM(dr.quantity * d.price), 0)\n" +
                    "  + COALESCE(SUM(ps.quantity * s.price), 0)   AS `Thành tiền`\n" +
                    "FROM Prescription p\n" +
                    "-- lấy tên bệnh nhân\n" +
                    "JOIN Patient pt\n" +
                    "  ON p.patient_id = pt.id\n" +
                    "-- lấy tên bác sĩ (qua bảng Doctor → Employee)\n" +
                    "JOIN Doctor doc\n" +
                    "  ON p.doctor_id = doc.id\n" +
                    "JOIN Employee e\n" +
                    "  ON doc.id = e.id\n" +
                    "-- chi tiết thuốc (có thể không có, nên LEFT JOIN)\n" +
                    "LEFT JOIN PrescriptionDrugDetail dr\n" +
                    "  ON p.id = dr.prescription_id\n" +
                    "LEFT JOIN Drug d\n" +
                    "  ON dr.drug_id = d.id\n" +
                    "-- chi tiết dịch vụ (có thể không có, nên LEFT JOIN)\n" +
                    "LEFT JOIN PrescriptionServiceDetail ps\n" +
                    "  ON p.id = ps.prescription_id\n" +
                    "LEFT JOIN Service s\n" +
                    "  ON ps.service_id = s.id\n" +
                    "WHERE p.paymentStatus = 'Đã thanh toán'\n" +
                    "GROUP BY p.id, DATE(p.preDate), pt.name, e.name\n" +
                    "ORDER BY DATE(p.preDate);\n";
            Sheet sheet = wb.createSheet("Báo cáo doanh thu");

            Connection con= JDBCUtil.getConnection();
            Statement statement= con.createStatement();
            ResultSet rs=statement.executeQuery(sql);
            // Title
            CellStyle titleStyleT = wb.createCellStyle();
            Font titleFontT = wb.createFont();
            titleFontT.setBold(true);
            titleFontT.setFontHeightInPoints((short)14);
            titleFontT.setColor(IndexedColors.RED.getIndex());
            titleStyleT.setFont(titleFontT);
            titleStyleT.setAlignment(HorizontalAlignment.CENTER);
            Row titleRow = sheet.createRow(0);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("Báo cáo doanh thu");
            titleCell.setCellStyle(titleStyleT);

            sheet.addMergedRegion(new CellRangeAddress(0,0,0,6));

            // Header
            String[] headers = {
                    "Ngày","Tên bệnh nhân","Tên bác sĩ","Tổng tiền thuốc",
                    "Tổng tiền dịch vụ","Thành tiền"
            };
            Row headerRow = sheet.createRow(1);
            CellStyle headerStyle = wb.createCellStyle();
            Font headerFont = wb.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GOLD.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);

            for (int i = 0; i < headers.length; i++) {
                Cell c = headerRow.createCell(i);
                c.setCellValue(headers[i]);
                c.setCellStyle(headerStyle);
            }

            // date style
            CellStyle dateStyle = wb.createCellStyle();
            CreationHelper ch = wb.getCreationHelper();
            dateStyle.setDataFormat(ch.createDataFormat().getFormat("dd/MM/yyyy"));

            // Data rows
            CellStyle borderStyle = wb.createCellStyle();
            borderStyle.setBorderTop(BorderStyle.THIN);
            borderStyle.setBorderBottom(BorderStyle.THIN);
            borderStyle.setBorderLeft(BorderStyle.THIN);
            borderStyle.setBorderRight(BorderStyle.THIN);

            CellStyle dateBorderStyle = wb.createCellStyle();
            dateBorderStyle.cloneStyleFrom(dateStyle);
            dateBorderStyle.setBorderTop(BorderStyle.THIN);
            dateBorderStyle.setBorderBottom(BorderStyle.THIN);
            dateBorderStyle.setBorderLeft(BorderStyle.THIN);
            dateBorderStyle.setBorderRight(BorderStyle.THIN);
            int rowIdx = 2;
            while (rs.next()) {
                Row row = sheet.createRow(rowIdx++);

                // cột Ngày
                Cell c0 = row.createCell(0);
                c0.setCellValue(rs.getDate("Ngày"));
                c0.setCellStyle(dateBorderStyle);

                // cột Tên bệnh nhân
                Cell c1 = row.createCell(1);
                c1.setCellValue(rs.getString("Tên bệnh nhân"));
                c1.setCellStyle(borderStyle);

                // cột Người bác sĩ
                Cell c2 = row.createCell(2);
                c2.setCellValue(rs.getString("Người bác sĩ"));
                c2.setCellStyle(borderStyle);

                // cột Tổng tiền thuốc
                Cell c3 = row.createCell(3);
                c3.setCellValue(rs.getDouble("Tổng tiền thuốc"));
                c3.setCellStyle(borderStyle);

                // cột Tổng tiền dịch vụ
                Cell c4 = row.createCell(4);
                c4.setCellValue(rs.getDouble("Tổng tiền dịch vụ"));
                c4.setCellStyle(borderStyle);

                // cột Thành tiền
                Cell c5 = row.createCell(5);
                c5.setCellValue(rs.getDouble("Thành tiền"));
                c5.setCellStyle(borderStyle);
            }
            rs.close();
            statement.close();
            con.close();

            sql="SELECT\n" +
                    "  COALESCE(SUM(dr.quantity * d.price), 0) AS `Tổng tiền thuốc`,\n" +
                    "  COALESCE(SUM(ps.quantity * s.price), 0) AS `Tổng tiền dịch vụ`,\n" +
                    "  COALESCE(SUM(dr.quantity * d.price), 0)\n" +
                    "  + COALESCE(SUM(ps.quantity * s.price), 0) AS `Tổng thành tiền`\n" +
                    "FROM Prescription p\n" +
                    "LEFT JOIN PrescriptionDrugDetail dr\n" +
                    "  ON p.id = dr.prescription_id\n" +
                    "LEFT JOIN Drug d\n" +
                    "  ON dr.drug_id = d.id\n" +
                    "LEFT JOIN PrescriptionServiceDetail ps\n" +
                    "  ON p.id = ps.prescription_id\n" +
                    "LEFT JOIN Service s\n" +
                    "  ON ps.service_id = s.id\n" +
                    "WHERE p.paymentStatus = 'Đã thanh toán';";
            Connection con2= JDBCUtil.getConnection();
            Statement statement2= con2.createStatement();
            ResultSet rs2=statement2.executeQuery(sql);

            //Total row
            int totalRowNum = rowIdx;
            Row totalRow = sheet.createRow(totalRowNum);
            Cell totalLabelCell = totalRow.createCell(0);
            totalLabelCell.setCellValue("Tổng");
            CellStyle totalLabelStyle = wb.createCellStyle();
            Font totalFont = wb.createFont();
            totalFont.setBold(true);
            totalLabelStyle.setFont(totalFont);
            totalLabelStyle.setAlignment(HorizontalAlignment.CENTER);
            totalLabelCell.setCellStyle(totalLabelStyle);

            sheet.addMergedRegion(new CellRangeAddress(totalRowNum,totalRowNum,0,2));

            // sum style
            CellStyle sumStyle = wb.createCellStyle();
            sumStyle.setFont(totalFont);
            sumStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
            sumStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            sumStyle.setBorderTop(BorderStyle.THIN);
            sumStyle.setBorderBottom(BorderStyle.THIN);
            sumStyle.setBorderLeft(BorderStyle.THIN);
            sumStyle.setBorderRight(BorderStyle.THIN);

            if (rs2.next()) {
                double totalThuoc= rs2.getDouble("Tổng tiền thuốc");
                double totalDichVu= rs2.getDouble("Tổng tiền dịch vụ");
                double totalThanhTien= rs2.getDouble("Tổng thành tiền");

                Cell cellThuoc= totalRow.createCell(3);
                cellThuoc.setCellValue(totalThuoc);
                cellThuoc.setCellStyle(sumStyle);

                Cell cellDV= totalRow.createCell(4);
                cellDV.setCellValue(totalDichVu);
                cellDV.setCellStyle(sumStyle);

                Cell cellTT= totalRow.createCell(5);
                cellTT.setCellValue(totalThanhTien);
                cellTT.setCellStyle(sumStyle);
            }
            // tình trạng để trống
            totalRow.createCell(8).setCellValue("");

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            // Ghi file
            try (FileOutputStream out = new FileOutputStream(filePath)) {
                wb.write(out);
            }
            System.out.println("Xuất Excel thành công: " + filePath);
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(new File(filePath));
            } else {
                System.out.println("Hệ thống không hỗ trợ mở file.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        ExportToEX.exportServiceReport();
    }
}
