package service;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import Utils.JDBCUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ExportToPDF {
    public static void prescriptionToPDF(String strIDPatient) {
        String query="SELECT \n" +
                "    p.name AS 'Tên bệnh nhân',\n" +
                "    p.address AS 'Địa chỉ bệnh nhân',\n" +
                "    p.idCard AS 'ID Card',\n" +
                "    pr.diagnosis AS 'Diagnosis',\n" +
                "    e.name AS 'Tên bác sĩ khám',\n" +
                "    e.birthDate AS 'Ngày sinh',\n" +
                "    e.gender AS 'Gender',\n" +
                "    d.name AS 'Tên thuốc',\n" +
                "    pd.quantity AS 'Số lượng',\n" +
                "    pr.advice AS 'Lời khuyên',\n" +
                "    pr.symptom AS 'Triệu chứng',\n" +
                "    d.dosage AS 'Dosage'\n" +
                "FROM \n" +
                "    Prescription pr\n" +
                "JOIN Patient p ON pr.patient_id = p.id\n" +
                "JOIN Doctor doc ON pr.doctor_id = doc.id\n" +
                "JOIN Employee e ON doc.id = e.id\n" +
                "JOIN PrescriptionDetail pd ON pr.id = pd.prescription_id\n" +
                "JOIN Drug d ON pd.drug_id = d.id\n" +
                "WHERE p.id ="+strIDPatient;

        try {
        	String baseFont="C:/Windows/Fonts/times.ttf";
        	
            Connection conn = JDBCUtil.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            //lấy tên,tuổi,địa chỉ,giới tính,cccd
            //triệu chứng chuẩn đoán
            //lời dặn, hẹn tái khám
            //tên nhân viên/bs

            String name = "", address = "", cccd;
            int age;
            String sex;
            String symptom = "", diagnosis = "";
            String advice = "", appoiment = "";
            String nameDentist = "";
            Date dateBirth = null;

            rs.next();
            name = rs.getString("Tên bệnh nhân");
            address = rs.getString("Địa chỉ bệnh nhân");
            cccd = rs.getString("ID Card");
            diagnosis = rs.getString("Diagnosis");
            symptom=rs.getString("Triệu chứng");
            advice = rs.getString("Lời khuyên");
            appoiment = LocalDate.now().plusDays(7).toString();
            nameDentist = rs.getString("Tên bác sĩ khám");
            dateBirth = rs.getDate("Ngày sinh");
            sex = rs.getBoolean("Gender") ? "Nam" : "Nữ";
            age = LocalDate.now().getYear() - dateBirth.toLocalDate().getYear();
            rs.close();

            rs = stmt.executeQuery(query);

            //tạo tên file theo định dạng DonThoc + cccd+ngay thang nam
            String filePath = "DonThoc_"+cccd+LocalDate.now()+".pdf";
            //
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            //Các font sẽ sử dụng
            BaseFont bsT = BaseFont.createFont(baseFont, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font fontHeader = new Font(bsT, 12);
            Font fontBoldPrescription = new Font(bsT, 14, Font.BOLD);
            Font fontT12Bold = new Font(bsT, 12, Font.BOLD);
            Font fontT12I = new Font(bsT, 12, Font.ITALIC);

            // Tạo bảng
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.setSpacingAfter(10f); // Thêm khoảng cách sau bảng

            // Ô 1
            Phrase phrase1 = new Phrase("Sở Y Tế TP.HCM\nPhòng Khám Nha Khoa LMK", fontHeader);
            PdfPCell cell1 = new PdfPCell(phrase1);
            cell1.setBorder(Rectangle.NO_BORDER);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell1);

            // Ô 2
            Phrase phrase2 = new Phrase("Số 450, Lê Văn Việt, Quận 9\nHotline: 090012109", fontHeader);
            PdfPCell cell2 = new PdfPCell(phrase2);
            cell2.setBorder(Rectangle.NO_BORDER);
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell2);

            document.add(table);

            // Tiêu đề "ĐƠN THUỐC"
            Paragraph paragraph = new Paragraph("ĐƠN THUỐC", fontBoldPrescription);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingBefore(10f); // Thêm khoảng cách trước đoạn văn nhưng k có tác dụng:)
            document.add(paragraph);

            //tạo bảng thông tin người nhận

            PdfPTable tableInfo = new PdfPTable(3);
            tableInfo.setWidthPercentage(80);
            tableInfo.setWidths(new float[]{4, 2, 2});
            tableInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
            //ô1
            PdfPCell cellTableIf1 = new PdfPCell(new Phrase("\nHọ tên: " + name + "\nĐịa chỉ: " + address + "\nCCCD: " + cccd, fontHeader));
            cellTableIf1.setBorder(Rectangle.NO_BORDER);
            cellTableIf1.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellTableIf1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tableInfo.addCell(cellTableIf1);
            //ô2
            PdfPCell cellTableIf2 = new PdfPCell(new Phrase("Tuổi: " + age, fontHeader));
            cellTableIf2.setBorder(Rectangle.NO_BORDER);
            cellTableIf2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTableIf2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tableInfo.addCell(cellTableIf2);
            //ô3
            PdfPCell cellTableIf3 = new PdfPCell(new Phrase("Giới tính: " + sex, fontHeader));
            cellTableIf3.setBorder(Rectangle.NO_BORDER);
            cellTableIf3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellTableIf3.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tableInfo.addCell(cellTableIf3);

            document.add(tableInfo);

            //tạo bảng triệu chứng và chẩn đoán
            PdfPTable tableDiagnose = new PdfPTable(1);
            tableDiagnose.setWidthPercentage(80);
            tableDiagnose.setHorizontalAlignment(Element.ALIGN_CENTER);

            //tạo dòng chứa triệu chứng và chẩn đoán
            Paragraph p = new Paragraph();
            p.add(new Chunk("Triệu chứng: ", fontT12Bold));
            p.add(new Chunk(symptom, fontHeader));
            PdfPCell cellPDFP1 = new PdfPCell(p);
            cellPDFP1.setBorder(Rectangle.NO_BORDER);
            tableDiagnose.addCell(cellPDFP1);

            Paragraph p1 = new Paragraph();
            p1.add(new Chunk("Chẩn đoán: ", fontT12Bold));
            p1.add(new Chunk(diagnosis, fontHeader));
            PdfPCell cellPDFP2 = new PdfPCell(p1);
            cellPDFP2.setBorder(Rectangle.NO_BORDER);
            tableDiagnose.addCell(cellPDFP2);

            document.add(tableDiagnose);

            //Tạo bảng đơn thuốc
            PdfPTable tableMeDicine = new PdfPTable(2);
            tableMeDicine.setWidthPercentage(80);
            tableMeDicine.setWidths(new float[]{7, 3});
            tableMeDicine.setHorizontalAlignment(Element.ALIGN_CENTER);

            Paragraph p2 = new Paragraph();
            p2.add(Chunk.NEWLINE);
            p2.add(new Chunk("Tên thuốc - hoạt chất", fontT12Bold));
            PdfPCell cellPDFP3 = new PdfPCell(p2);
            cellPDFP3.setBorder(Rectangle.NO_BORDER);
            tableMeDicine.addCell(cellPDFP3);

            Paragraph p3 = new Paragraph();
            p3.add(Chunk.NEWLINE);
            p3.add(new Chunk("Số lượng", fontT12Bold));
            PdfPCell cellPDFP4 = new PdfPCell(p3);
            cellPDFP4.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cellPDFP4.setBorder(Rectangle.NO_BORDER);
            tableMeDicine.addCell(cellPDFP4);

            int i = 1;
            while (rs.next()) {
                String dosage = rs.getString("Dosage");
                String nameMedicine = rs.getString("Tên thuốc");
                String quantity = rs.getString("Số lượng");
                Paragraph p4 = new Paragraph();
                p4.add(new Chunk(i + "/ " + nameMedicine, fontT12Bold));
                p4.add(Chunk.NEWLINE);
                p4.add(new Chunk(dosage, fontT12I));
                PdfPCell cellPDFP5 = new PdfPCell(p4);
                cellPDFP5.setBorder(Rectangle.NO_BORDER);
                tableMeDicine.addCell(cellPDFP5);

                Paragraph p5 = new Paragraph();
                p5.add(new Chunk("x" + quantity, fontHeader));
                PdfPCell cellPDFP6 = new PdfPCell(p5);
                cellPDFP6.setBorder(Rectangle.NO_BORDER);
                cellPDFP6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                tableMeDicine.addCell(cellPDFP6);
                ++i;
            }
            document.add(tableMeDicine);

            //tạo bảng lời dặn và hẹn khám
            PdfPTable tableAdvice = new PdfPTable(1);
            tableInfo.setWidthPercentage(80);
            tableInfo.setHorizontalAlignment(Element.ALIGN_CENTER);

            Paragraph p6 = new Paragraph();
            p6.add(Chunk.NEWLINE);
            p6.add(new Chunk("Lời dặn: " + advice, fontHeader));
            PdfPCell cellPDFP7 = new PdfPCell(p6);
            cellPDFP7.setBorder(Rectangle.NO_BORDER);
            cellPDFP7.setHorizontalAlignment(Element.ALIGN_LEFT);
            tableAdvice.addCell(cellPDFP7);

            Paragraph p7 = new Paragraph();
            p7.add(new Chunk("Hẹn tái khám: " + appoiment, fontHeader));
            PdfPCell cellPDFP8 = new PdfPCell(p7);
            cellPDFP8.setBorder(Rectangle.NO_BORDER);
            cellPDFP8.setHorizontalAlignment(Element.ALIGN_LEFT);
            tableAdvice.addCell(cellPDFP8);

            document.add(tableAdvice);

            PdfPTable tableSign = new PdfPTable(2);
            tableSign.setWidthPercentage(80);
            tableSign.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell cellTmp = new PdfPCell();
            cellTmp.setBorder(Rectangle.NO_BORDER);
            tableSign.addCell(cellTmp);

            LocalDate datePre = LocalDate.now();

            Paragraph p8 = new Paragraph();
            p8.add(new Chunk("Ngày " + datePre.getDayOfMonth() + " tháng " + datePre.getMonthValue() + " năm " + datePre.getYear(), fontHeader));
            p8.add(Chunk.NEWLINE);
            p8.add(new Chunk("Bác sĩ khám bệnh", fontT12Bold));
            p8.add(Chunk.NEWLINE);
            p8.add(new Chunk("(Ký, ghi rõ họ tên)", fontT12I));
            p8.add(Chunk.NEWLINE);
            p8.add(Chunk.NEWLINE);
            p8.add(new Chunk("[Đã ký]", fontHeader));
            p8.add(Chunk.NEWLINE);
            p8.add(Chunk.NEWLINE);
            p8.add(new Chunk(nameDentist, fontT12Bold));
            PdfPCell cellPDFP9 = new PdfPCell(p8);
            cellPDFP9.setBorder(Rectangle.NO_BORDER);
            cellPDFP9.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableSign.addCell(cellPDFP9);

            document.add(tableSign);

            document.close();
            System.out.println("Đã tạo file PDF: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void billToPDF(String idPatient) {
        String query="SELECT \n" +
                "    p.name AS 'Tên bệnh nhân',\n" +
                "    p.address AS 'Địa chỉ bệnh nhân',\n" +
                "    pr.id AS 'Prescription ID',\n" +
                "    s.name AS 'Tên dịch vụ',\n" +
                "    s.price AS 'Giá dịch vụ',\n" +
                "    d.name AS 'Tên thuốc',\n" +
                "    pd.quantity AS 'Số lượng',\n" +
                "    d.price AS 'Giá tiền'\n" +
                "FROM \n" +
                "    Prescription pr\n" +
                "JOIN Patient p ON pr.patient_id = p.id\n" +
                "JOIN PrescriptionDetail pd ON pr.id = pd.prescription_id\n" +
                "JOIN Drug d ON pd.drug_id = d.id\n" +
                "JOIN Service s ON pd.service_id = s.id\n" +
                "where p.id="+idPatient;
        try {
            String baseFont="C:/Windows/Fonts/times.ttf";

            Connection conn = JDBCUtil.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            //lưu thông tin của người bệnh
            String name="";
            String address="";
            String billID="";
            LocalDateTime billDate=null;

            //duyệt 1 lần để lấy tên của người bênh
            rs.next();
            name=rs.getString("Tên bệnh nhân");
            address=rs.getString("Địa chỉ bệnh nhân");
            billID=rs.getString("Prescription ID");
//            Timestamp billDateT=rs.getTimestamp("BILL_CURRENT_TIME");
            billDate=LocalDateTime.now();
            rs.close();


            String filePath = "Bill_"+billID+LocalDate.now()+".pdf";

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            //font sử dụng
            BaseFont bsT = BaseFont.createFont(baseFont, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font fontHeader = new Font(bsT, 12);
            Font fontBoldHeader = new Font(bsT, 14, Font.BOLD);
            Font fontT12Bold = new Font(bsT, 12, Font.BOLD);
            Font fontT16Bold = new Font(bsT, 16, Font.BOLD);
            Font fontT12I = new Font(bsT, 12, Font.ITALIC);
            Paragraph pEnter = new Paragraph(" ");
            DecimalFormat formatter = new DecimalFormat("#,###");

            //tạo bảng header
            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(100);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);

            Paragraph pH = new Paragraph();
            pH.add(new Chunk("\nPHÒNG KHÁM NHA KHOA LMK", fontBoldHeader));
            PdfPCell cellH = new PdfPCell(pH);
            cellH.setBorder(Rectangle.NO_BORDER);
            cellH.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cellH);

            Paragraph pI = new Paragraph();
            pI.add(new Chunk("Địa chỉ: 450 Lê Văn Việt, Tăng Nhơn Phú A, TP. Thủ Đức", fontHeader));
            pI.add(Chunk.NEWLINE);
            pI.add(new Chunk("Điện thoại: 090012109", fontHeader));
            PdfPCell cellI = new PdfPCell(pI);
            cellI.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellI.setBorder(Rectangle.NO_BORDER);
            table.addCell(cellI);
            document.add(table);

            document.add(pEnter);
            Paragraph pTitle = new Paragraph();
            pTitle.add(new Chunk("HOÁ ĐƠN KHÁM BỆNH", fontT16Bold));
            pTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(pTitle);

            //tạo bảng lưu thong tin
            PdfPTable tableInfo = new PdfPTable(2);
            tableInfo.setWidthPercentage(80);
            tableInfo.setWidths(new float[]{6, 3});
            tableInfo.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell cellInfo = new PdfPCell(new Paragraph());
            cellInfo.setBorder(Rectangle.NO_BORDER);
            tableInfo.addCell(cellInfo);
            Paragraph pCode = new Paragraph();
            pCode.add(new Chunk("Số: B"+String.format("%03d", Integer.parseInt(billID)), fontHeader));
            PdfPCell cellCode = new PdfPCell(pCode);
            cellCode.setBorder(Rectangle.NO_BORDER);
            cellCode.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tableInfo.addCell(cellCode);

            Paragraph pName = new Paragraph();
            pName.add(new Chunk("Tên khách hàng: "+name, fontHeader));
            pName.setAlignment(Element.ALIGN_LEFT);
            PdfPCell cellName = new PdfPCell(pName);
            cellName.setBorder(Rectangle.NO_BORDER);
            tableInfo.addCell(cellName);
            tableInfo.addCell(cellInfo);
            Paragraph pAddress = new Paragraph();
            pAddress.add(new Chunk("Địa chỉ: "+address, fontHeader));
            pAddress.setAlignment(Element.ALIGN_LEFT);
            PdfPCell cellAddress = new PdfPCell(pAddress);
            cellAddress.setBorder(Rectangle.NO_BORDER);
            tableInfo.addCell(cellAddress);
            tableInfo.addCell(cellInfo);

            document.add(tableInfo);

            //tạo bảng tính tiền
            document.add(pEnter);
            String[] nameCollums = {"TT", "NỘI DUNG DỊCH VỤ", "SL", "ĐƠN GIÁ", "THÀNH TIỀN"};
            PdfPTable tableBill = new PdfPTable(5);
            tableBill.setWidthPercentage(80);
            tableBill.setWidths(new float[]{1, 5, 1, 2, 3});
            tableBill.setHorizontalAlignment(Element.ALIGN_CENTER);

            for (int i = 0; i < nameCollums.length; i++) {
                Paragraph pTT = new Paragraph();
                pTT.add(new Chunk(nameCollums[i], fontT12Bold));
                pTT.setAlignment(Element.ALIGN_CENTER);
                PdfPCell cellTT = new PdfPCell(pTT);
                cellTT.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableBill.addCell(cellTT);
            }
            rs=stmt.executeQuery(query);
            int j=0;
            //lấy tên và số lượng dịch vụ
            Map<String,BigDecimal> listService=new HashMap<>();
            while(rs.next()) {
                listService.put(rs.getString("Tên dịch vụ"),new BigDecimal(rs.getString("Giá dịch vụ")));
            }
            BigDecimal sumTotal=new BigDecimal(0);

            for (String key : listService.keySet()) {
                //lấy tên,giá, bla bla
                String nameService = key;
                int quantity = 1;
                String price = formatter.format(listService.get(key));
                String total = price ;
                BigDecimal total2 = listService.get(key); ;

                Paragraph pTT = new Paragraph();
                pTT.add(new Chunk((j+1)+"", fontHeader));
                pTT.setAlignment(Element.ALIGN_CENTER);
                PdfPCell cellTT = new PdfPCell(pTT);
                cellTT.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableBill.addCell(cellTT);

                Paragraph pContent = new Paragraph();
                pContent.add(new Chunk(nameService, fontHeader));
                pContent.setAlignment(Element.ALIGN_CENTER);
                PdfPCell cellContent = new PdfPCell(pContent);
                cellContent.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableBill.addCell(cellContent);

                Paragraph pNum = new Paragraph();
                pNum.add(new Chunk(quantity+"", fontHeader));
                pNum.setAlignment(Element.ALIGN_CENTER);
                PdfPCell cellNum = new PdfPCell(pNum);
                cellNum.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableBill.addCell(cellNum);

                Paragraph pPrice = new Paragraph();
                pPrice.add(new Chunk(""+price, fontHeader));
                pPrice.setAlignment(Element.ALIGN_CENTER);
                PdfPCell cellprice = new PdfPCell(pPrice);
                cellprice.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableBill.addCell(cellprice);

                Paragraph pTotal = new Paragraph();
                pTotal.add(new Chunk(total+"", fontHeader));
                pTotal.setAlignment(Element.ALIGN_CENTER);
                PdfPCell cellTotal = new PdfPCell(pTotal);
                cellTotal.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableBill.addCell(cellTotal);
                sumTotal=sumTotal.add(total2);
                j++;
            }

            for (int i = 0; i < nameCollums.length+1; i++) {
                Paragraph pTT = new Paragraph();
                pTT.add(new Chunk(" ", fontHeader));
                pTT.setAlignment(Element.ALIGN_CENTER);
                PdfPCell cellTT = new PdfPCell(pTT);
                cellTT.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableBill.addCell(cellTT);
            }
            Paragraph pTT = new Paragraph();
            pTT.add(new Chunk("TỔNG CỘNG", fontT12Bold));
            pTT.setAlignment(Element.ALIGN_CENTER);
            PdfPCell cellTT = new PdfPCell(pTT);
            cellTT.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableBill.addCell(cellTT);

            for (int i = 0; i < 2; i++) {
                pTT = new Paragraph();
                pTT.add(new Chunk(" ", fontHeader));
                pTT.setAlignment(Element.ALIGN_CENTER);
                cellTT = new PdfPCell(pTT);
                cellTT.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableBill.addCell(cellTT);
            }

            pTT = new Paragraph();
            pTT.add(new Chunk(" "+formatter.format(sumTotal), fontHeader));
            pTT.setAlignment(Element.ALIGN_CENTER);
            cellTT = new PdfPCell(pTT);
            cellTT.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableBill.addCell(cellTT);

            document.add(tableBill);

            //tạo bảng chứa bill thuốc
            document.add(pEnter);

            String[] nameCollumsMedicine = {"TT", "TÊN THUỐC", "SL", "ĐƠN GIÁ", "THÀNH TIỀN"};
            PdfPTable tableBillMedicine = new PdfPTable(5);
            tableBillMedicine.setWidthPercentage(80);
            tableBillMedicine.setWidths(new float[]{1, 5, 1, 2, 3});
            tableBillMedicine.setHorizontalAlignment(Element.ALIGN_CENTER);

            for (int i = 0; i < nameCollumsMedicine.length; i++) {
                pTT = new Paragraph();
                pTT.add(new Chunk(nameCollumsMedicine[i], fontT12Bold));
                pTT.setAlignment(Element.ALIGN_CENTER);
                cellTT = new PdfPCell(pTT);
                cellTT.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableBillMedicine.addCell(cellTT);
            }
            int k=0;
            BigDecimal sumMedicine=BigDecimal.ZERO;
            rs=stmt.executeQuery(query);
            while (rs.next()){
                String nameMedicine = rs.getString("Tên thuốc");
                int quantityMedicine = rs.getInt("Số lượng");
                BigDecimal priceMedicine = rs.getBigDecimal("Giá tiền");
                BigDecimal totalMedicine=priceMedicine.multiply(new BigDecimal(quantityMedicine));

                pTT = new Paragraph();
                pTT.add(new Chunk((k+1)+"", fontHeader));
                pTT.setAlignment(Element.ALIGN_CENTER);
                cellTT = new PdfPCell(pTT);
                cellTT.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableBillMedicine.addCell(cellTT);

                Paragraph pContent = new Paragraph();
                pContent.add(new Chunk(nameMedicine, fontHeader));
                pContent.setAlignment(Element.ALIGN_CENTER);
                PdfPCell cellContent = new PdfPCell(pContent);
                cellContent.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableBillMedicine.addCell(cellContent);

                Paragraph pNum = new Paragraph();
                pNum.add(new Chunk(quantityMedicine+"", fontHeader));
                pNum.setAlignment(Element.ALIGN_CENTER);
                PdfPCell cellNum = new PdfPCell(pNum);
                cellNum.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableBillMedicine.addCell(cellNum);

                Paragraph pPrice = new Paragraph();
                pPrice.add(new Chunk(""+formatter.format(priceMedicine), fontHeader));
                pPrice.setAlignment(Element.ALIGN_CENTER);
                PdfPCell cellprice = new PdfPCell(pPrice);
                cellprice.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableBillMedicine.addCell(cellprice);

                Paragraph pTotal = new Paragraph();
                pTotal.add(new Chunk(formatter.format(totalMedicine)+"", fontHeader));
                sumMedicine=sumMedicine.add(totalMedicine);
                pTotal.setAlignment(Element.ALIGN_CENTER);
                PdfPCell cellTotal = new PdfPCell(pTotal);
                cellTotal.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableBillMedicine.addCell(cellTotal);
                k++;
            }
            for (int i = 0; i < nameCollumsMedicine.length+1; i++) {
                pTT = new Paragraph();
                pTT.add(new Chunk(" ", fontHeader));
                pTT.setAlignment(Element.ALIGN_CENTER);
                cellTT = new PdfPCell(pTT);
                cellTT.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableBillMedicine.addCell(cellTT);
            }
            pTT = new Paragraph();
            pTT.add(new Chunk("TỔNG CỘNG", fontT12Bold));
            pTT.setAlignment(Element.ALIGN_CENTER);
            cellTT = new PdfPCell(pTT);
            cellTT.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableBillMedicine.addCell(cellTT);

            for (int i = 0; i < 2; i++) {
                pTT = new Paragraph();
                pTT.add(new Chunk(" ", fontHeader));
                pTT.setAlignment(Element.ALIGN_CENTER);
                cellTT = new PdfPCell(pTT);
                cellTT.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableBillMedicine.addCell(cellTT);
            }

            pTT = new Paragraph();
            pTT.add(new Chunk(" "+formatter.format(sumMedicine), fontHeader));
            pTT.setAlignment(Element.ALIGN_CENTER);
            cellTT = new PdfPCell(pTT);
            cellTT.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableBillMedicine.addCell(cellTT);

            document.add(tableBillMedicine);

            PdfPTable tableTotle=new PdfPTable(1);
            tableTotle.setWidthPercentage(80);
            tableTotle.setHorizontalAlignment(Element.ALIGN_CENTER);

            Paragraph pTotal = new Paragraph();
            document.add(pEnter);
            pTotal.add(new Chunk("TỔNG SỐ TIỀN PHẢI THANH TOÁN: ", fontT12Bold));
            pTotal.add(new Chunk((formatter.format(sumMedicine.add(sumTotal)))+" VND", fontHeader));
            PdfPCell cellTotal = new PdfPCell(pTotal);
            cellTotal.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellTotal.setBorder(Rectangle.NO_BORDER);
            tableTotle.addCell(cellTotal);
            document.add(tableTotle);

            document.add(pEnter);
            PdfPTable tableEnd=new PdfPTable(2);
            tableEnd.setWidthPercentage(80);
            tableEnd.setHorizontalAlignment(Element.ALIGN_CENTER);

            Paragraph pEnd1 = new Paragraph();
            pEnd1.add(new Chunk("Bệnh nhân", fontHeader));
            pEnd1.add(Chunk.NEWLINE);
            pEnd1.add(new Chunk("(Ký, ghi rõ họ tên)",fontHeader));
            PdfPCell cellEnd1 = new PdfPCell(pEnd1);
            cellEnd1.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellEnd1.setVerticalAlignment(Element.ALIGN_BOTTOM);
            cellEnd1.setBorder(Rectangle.NO_BORDER);
            tableEnd.addCell(cellEnd1);

            Paragraph pEnd2 = new Paragraph();
            pEnd2.add(new Chunk("Ngày "+billDate.getDayOfMonth()+" tháng "+billDate.getMonthValue()+" năm "+billDate.getYear(), fontHeader));
            pEnd2.add(Chunk.NEWLINE);
            pEnd2.add(Chunk.NEWLINE);
            pEnd2.add(new Chunk("Nhân viên thu ngân", fontHeader));
            pEnd2.add(Chunk.NEWLINE);
            pEnd2.add(new Chunk("(Ký, ghi rõ họ tên)",fontHeader));
            PdfPCell cellEnd2 = new PdfPCell(pEnd2);
            cellEnd2.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellEnd2.setBorder(Rectangle.NO_BORDER);
            tableEnd.addCell(cellEnd2);

            document.add(tableEnd);

            document.close();
            System.out.println("Đã tạo file "+filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public static void prescriptionAllToPDF() {
        String filePath = "list_don_thuoc.pdf";
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            //Các font sẽ sử dụng
            BaseFont bsT = BaseFont.createFont("C:/Windows/Fonts/times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font fontHeader = new Font(bsT, 12);
            Font fontBoldPrescription = new Font(bsT, 14, Font.BOLD);
            Font fontT12Bold = new Font(bsT, 12, Font.BOLD);
            Font fontT12I = new Font(bsT, 12, Font.ITALIC);

            int sizeListMedicine = 5;
            for (int j = sizeListMedicine; j > 0; j--) {
                // Tạo bảng
                PdfPTable table = new PdfPTable(2);
                table.setWidthPercentage(100);
                table.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.setSpacingAfter(10f); // Thêm khoảng cách sau bảng

                // Ô 1
                Phrase phrase1 = new Phrase("Sở Y Tế TP.HCM\nPhòng Khám Nha Khoa UTC2", fontHeader);
                PdfPCell cell1 = new PdfPCell(phrase1);
                cell1.setBorder(Rectangle.NO_BORDER);
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell1);

                // Ô 2
                Phrase phrase2 = new Phrase("Số 450, Lê Văn Việt, Quận 9\nHotline: 090012109", fontHeader);
                PdfPCell cell2 = new PdfPCell(phrase2);
                cell2.setBorder(Rectangle.NO_BORDER);
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell2);

                document.add(table);

                // Tiêu đề "ĐƠN THUỐC"
                Paragraph paragraph = new Paragraph("ĐƠN THUỐC", fontBoldPrescription);
                paragraph.setAlignment(Element.ALIGN_CENTER);
                paragraph.setSpacingBefore(10f); // Thêm khoảng cách trước đoạn văn nhưng k có tác dụng:)
                document.add(paragraph);

                //  Thông tin của người bệnh
                String name = "Ngô Minh Khôi";
                int age = 20;
                String gender = "Nam";
                String address = "TP.HCM";
                String ID = "082205026341";
                //tạo bảng thông tin người nhận

                PdfPTable tableInfo = new PdfPTable(3);
                tableInfo.setWidthPercentage(80);
                tableInfo.setWidths(new float[]{4, 2, 2});
                tableInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
                //ô1
                PdfPCell cellTableIf1 = new PdfPCell(new Phrase("\nHọ tên: " + name + "\nĐịa chỉ: " + address + "\nCCCD: " + ID, fontHeader));
                cellTableIf1.setBorder(Rectangle.NO_BORDER);
                cellTableIf1.setHorizontalAlignment(Element.ALIGN_LEFT);
                cellTableIf1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableInfo.addCell(cellTableIf1);
                //ô2
                PdfPCell cellTableIf2 = new PdfPCell(new Phrase("Tuổi: " + age, fontHeader));
                cellTableIf2.setBorder(Rectangle.NO_BORDER);
                cellTableIf2.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellTableIf2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableInfo.addCell(cellTableIf2);
                //ô3
                PdfPCell cellTableIf3 = new PdfPCell(new Phrase("Giới tính: " + gender, fontHeader));
                cellTableIf3.setBorder(Rectangle.NO_BORDER);
                cellTableIf3.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellTableIf3.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tableInfo.addCell(cellTableIf3);

                document.add(tableInfo);

                //Tạo các biến triệu chứng và chuẩn đoán
                String trieuChung = "Đau buốt, vôi răng bám nhiều";
                String chuanDoan = "Mòn men răng";

                //tạo bảng triệu chứng và chẩn đoán
                PdfPTable tableDiagnose = new PdfPTable(1);
                tableDiagnose.setWidthPercentage(80);
                tableDiagnose.setHorizontalAlignment(Element.ALIGN_CENTER);

                //tạo dòng chứa triệu chứng và chẩn đoán
                Paragraph p = new Paragraph();
                p.add(new Chunk("Triệu chứng: ", fontT12Bold));
                p.add(new Chunk(trieuChung, fontHeader));
                PdfPCell cellPDFP1 = new PdfPCell(p);
                cellPDFP1.setBorder(Rectangle.NO_BORDER);
                tableDiagnose.addCell(cellPDFP1);

                Paragraph p1 = new Paragraph();
                p1.add(new Chunk("Chẩn đoán: ", fontT12Bold));
                p1.add(new Chunk(chuanDoan, fontHeader));
                PdfPCell cellPDFP2 = new PdfPCell(p1);
                cellPDFP2.setBorder(Rectangle.NO_BORDER);
                tableDiagnose.addCell(cellPDFP2);

                document.add(tableDiagnose);

                //Tạo bảng đơn thuốc
                PdfPTable tableMeDicine = new PdfPTable(2);
                tableMeDicine.setWidthPercentage(80);
                tableMeDicine.setWidths(new float[]{7, 3});
                tableMeDicine.setHorizontalAlignment(Element.ALIGN_CENTER);

                Paragraph p2 = new Paragraph();
                p2.add(Chunk.NEWLINE);
                p2.add(new Chunk("Tên thuốc - hoạt chất", fontT12Bold));
                PdfPCell cellPDFP3 = new PdfPCell(p2);
                cellPDFP3.setBorder(Rectangle.NO_BORDER);
                tableMeDicine.addCell(cellPDFP3);

                Paragraph p3 = new Paragraph();
                p3.add(Chunk.NEWLINE);
                p3.add(new Chunk("Số lượng", fontT12Bold));
                PdfPCell cellPDFP4 = new PdfPCell(p3);
                cellPDFP4.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cellPDFP4.setBorder(Rectangle.NO_BORDER);
                tableMeDicine.addCell(cellPDFP4);

                int numMedical = 4;
                for (int i = 0; i < numMedical; i++) {
                    Paragraph p4 = new Paragraph();
                    p4.add(new Chunk((i + 1) + "/ Tên thuốc cần truyền vào", fontT12Bold));
                    p4.add(Chunk.NEWLINE);
                    p4.add(new Chunk("2 viên/ngày", fontT12I));
                    PdfPCell cellPDFP5 = new PdfPCell(p4);
                    cellPDFP5.setBorder(Rectangle.NO_BORDER);
                    tableMeDicine.addCell(cellPDFP5);

                    Paragraph p5 = new Paragraph();
                    p5.add(new Chunk("x12", fontHeader));
                    PdfPCell cellPDFP6 = new PdfPCell(p5);
                    cellPDFP6.setBorder(Rectangle.NO_BORDER);
                    cellPDFP6.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    tableMeDicine.addCell(cellPDFP6);
                }
                document.add(tableMeDicine);

                //tạo biến lời khuyên và dặn dò khám
                String advice = "Mình là siu nhân gao";
                String appoiment = "01/04/2025";

                //tạo bảng lời dặn và hẹn khám
                PdfPTable tableAdvice = new PdfPTable(1);
                tableInfo.setWidthPercentage(80);
                tableInfo.setHorizontalAlignment(Element.ALIGN_CENTER);

                Paragraph p6 = new Paragraph();
                p6.add(Chunk.NEWLINE);
                p6.add(new Chunk("Lời dặn: " + advice, fontHeader));
                PdfPCell cellPDFP7 = new PdfPCell(p6);
                cellPDFP7.setBorder(Rectangle.NO_BORDER);
                cellPDFP7.setHorizontalAlignment(Element.ALIGN_LEFT);
                tableAdvice.addCell(cellPDFP7);

                Paragraph p7 = new Paragraph();
                p7.add(new Chunk("Hẹn tái khám: " + appoiment, fontHeader));
                PdfPCell cellPDFP8 = new PdfPCell(p7);
                cellPDFP8.setBorder(Rectangle.NO_BORDER);
                cellPDFP8.setHorizontalAlignment(Element.ALIGN_LEFT);
                tableAdvice.addCell(cellPDFP8);

                document.add(tableAdvice);

                PdfPTable tableSign = new PdfPTable(2);
                tableSign.setWidthPercentage(80);
                tableSign.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell cellTmp = new PdfPCell();
                cellTmp.setBorder(Rectangle.NO_BORDER);
                tableSign.addCell(cellTmp);

                Paragraph p8 = new Paragraph();
                p8.add(new Chunk("Ngày 30 tháng 4 năm 2025", fontHeader));
                p8.add(Chunk.NEWLINE);
                p8.add(new Chunk("Bác sĩ khám bệnh", fontT12Bold));
                p8.add(Chunk.NEWLINE);
                p8.add(new Chunk("(Ký, ghi rõ họ tên)", fontT12I));
                p8.add(Chunk.NEWLINE);
                p8.add(Chunk.NEWLINE);
                p8.add(new Chunk("[Đã ký]", fontHeader));
                p8.add(Chunk.NEWLINE);
                p8.add(Chunk.NEWLINE);
                p8.add(new Chunk("BS. Đàm Hoàng Lam", fontT12Bold));
                PdfPCell cellPDFP9 = new PdfPCell(p8);
                cellPDFP9.setBorder(Rectangle.NO_BORDER);
                cellPDFP9.setHorizontalAlignment(Element.ALIGN_CENTER);
                tableSign.addCell(cellPDFP9);

                document.add(tableSign);
                document.newPage();
            }
            document.close();
            System.out.println("Đã tạo file PDF: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
    	//đây là test
    	
        //strIDPatient sẽ là nơi truyền vào id của người bệnh
    	//lí do chọn String là để tránh trường hợp id quá dài
        String strIDPatient=1+"";

        ExportToPDF.billToPDF(strIDPatient);
        ExportToPDF.prescriptionToPDF(strIDPatient);
    }
}