package com.cafe.server.services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe.server.entities.Bill;
import com.cafe.server.jwt.JwtAuthenticationFilter;
import com.cafe.server.repositories.BillRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private JwtAuthenticationFilter filter;

    public Bill generateReport(Bill bill) throws FileNotFoundException, DocumentException, JSONException {
        String filename = generateUuid();
        Bill bi = new Bill();
        bi.setName(bill.getName());
        bi.setUuid(filename);
        bi.setContactnumber(bill.getContactnumber());
        bi.setTotal(bill.getTotal());
        bi.setEmail(bill.getEmail());
        bi.setPaymentmethod(bill.getPaymentmethod());
        bi.setCreatedBy(filter.getCurrentUser());

        bi.setProductdetails(bill.getProductdetails());

        billRepository.save(bi);
        String data = " Name: " + bill.getName() + "\n" + " Contact Number: " + bill.getContactnumber() + "\n"
                + " Email: "
                + bill.getEmail() + "\n" + " Payment Method: " + bill.getPaymentmethod();

        // create a new document
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\Chintan\\Downloads\\" + filename + ".pdf"));

        document.open();
        setRectangleInPdf(document);

        // add header
        Paragraph chunk = new Paragraph("Cafe Management System", getFont("Header"));

        chunk.setAlignment(Element.ALIGN_CENTER);

        document.add(chunk);

        Paragraph paragraph = new Paragraph(data + "\n\n", getFont("Data"));
        document.add(paragraph);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        addTableHeader(table);

        JSONArray array = new JSONArray((String) bill.getProductdetails());

        for (int i = 0; i < array.length(); i++) {
            JSONObject product = array.getJSONObject(i);

            String productName = product.getString("name");
            String category = product.getString("category");
            int quantity = product.getInt("quantity");
            double price = product.getDouble("price");
            double subTotal = quantity * price;

            PdfPCell productNameCell = new PdfPCell(new Phrase(productName));
            PdfPCell categoryCell = new PdfPCell(new Phrase(category));
            PdfPCell quantityCell = new PdfPCell(new Phrase(String.valueOf(quantity)));
            PdfPCell priceCell = new PdfPCell(new Phrase(String.valueOf(price)));
            PdfPCell subTotalCell = new PdfPCell(new Phrase(String.valueOf(subTotal)));

            table.addCell(productNameCell);
            table.addCell(categoryCell);
            table.addCell(quantityCell);
            table.addCell(priceCell);
            table.addCell(subTotalCell);
        }

        document.add(table);

        Paragraph footer = new Paragraph(
                "Total: " + bill.getTotal() + "\n" + "Thank you for visiting. Please visit again!!", getFont("Data"));

        document.add(footer);
        document.close();
        return bi;
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Name", "Category", "Quantity", "Price", "Sub Total")
                .forEach(column -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(column));
                    header.setBackgroundColor(BaseColor.YELLOW);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_CENTER);

                    table.addCell(header);
                });
    }

    private Font getFont(String type) {
        switch (type) {
            case "Header":
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
                headerFont.setStyle(Font.BOLD);
                return headerFont;
            case "Data":
                Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, BaseColor.BLACK);
                dataFont.setStyle(Font.BOLD);
                return dataFont;
            default:
                return new Font();
        }
    }

    private void setRectangleInPdf(Document document) throws DocumentException {
        Rectangle rect = new Rectangle(577, 825, 18, 15);
        rect.enableBorderSide(1);
        rect.enableBorderSide(2);
        rect.enableBorderSide(4);
        rect.enableBorderSide(8);
        rect.setBorderColor(BaseColor.BLACK);
        rect.setBorderWidth(1);
        document.add(rect);
    }

    private String generateUuid() {
        Date date = new Date();
        long time = date.getTime();
        return "BILL-" + time;
    }
}