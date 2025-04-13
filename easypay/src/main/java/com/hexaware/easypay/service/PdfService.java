package com.hexaware.easypay.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.hexaware.easypay.model.Payroll;
import com.hexaware.easypay.model.Employee;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PdfService {

    public byte[] generatePayrollPdf(Payroll payroll, Employee employee) throws DocumentException {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            document.open();

            // Add company logo or header
            addHeader(document);
            
            // Add payroll details
            addPayrollDetails(document, payroll, employee);
            
            // Add salary breakdown
            addSalaryBreakdown(document, payroll);
            
            // Add footer
            addFooter(document);

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            document.close();
            throw new DocumentException("Error generating PDF: " + e.getMessage());
        }
    }

    private void addHeader(Document document) throws DocumentException {
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.DARK_GRAY);
        Paragraph title = new Paragraph("EasyPay - Payroll Statement", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Paragraph date = new Paragraph("Generated on: " + dateFormat.format(new Date()), 
                new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL));
        date.setAlignment(Element.ALIGN_RIGHT);
        date.setSpacingAfter(20);
        document.add(date);
    }

    private void addPayrollDetails(Document document, Payroll payroll, Employee employee) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);
        
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Font textFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
        
        // Employee Info
        addTableCell(table, "Employee Information", headerFont, 2);
        addTableCell(table, "Employee Code:", headerFont);
        addTableCell(table, employee.getEmployeeCode(), textFont);
        addTableCell(table, "Employee Name:", headerFont);
        addTableCell(table, employee.getFirstName() + " " + employee.getLastName(), textFont);
        addTableCell(table, "Department:", headerFont);
        addTableCell(table, employee.getDepartment(), textFont);
        addTableCell(table, "Designation:", headerFont);
        addTableCell(table, employee.getDesignation(), textFont);
        
        // Payroll Info
        addTableCell(table, "Payroll Information", headerFont, 2);
        addTableCell(table, "Payroll ID:", headerFont);
        addTableCell(table, String.valueOf(payroll.getId()), textFont);
        addTableCell(table, "Pay Period:", headerFont);
        addTableCell(table, payroll.getPayPeriodStart() + " to " + payroll.getPayPeriodEnd(), textFont);
        addTableCell(table, "Status:", headerFont);
        addTableCell(table, payroll.getStatus().toString(), textFont);
        
        document.add(table);
    }

    private void addSalaryBreakdown(Document document, Payroll payroll) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);
        
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Font textFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
        Font totalFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.DARK_GRAY);
        
        // Salary Breakdown
        addTableCell(table, "Salary Breakdown", headerFont, 2);
        addTableCell(table, "Basic Salary:", headerFont);
        addTableCell(table, "₹" + payroll.getBasicSalary().toString(), textFont);
        addTableCell(table, "Allowances:", headerFont);
        addTableCell(table, "₹" + payroll.getAllowances().toString(), textFont);
        addTableCell(table, "Deductions:", headerFont);
        addTableCell(table, "₹" + payroll.getDeductions().toString(), textFont);
        
        // Net Salary
        addTableCell(table, "Net Salary:", totalFont);
        addTableCell(table, "₹" + payroll.getNetSalary().toString(), totalFont);
        
        document.add(table);
    }
    
    private void addFooter(Document document) throws DocumentException {
        Paragraph footer = new Paragraph("This is a computer-generated document. No signature is required.", 
                new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC));
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);
    }

    private void addTableCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5);
        table.addCell(cell);
    }
    
    private void addTableCell(PdfPTable table, String text, Font font, int colspan) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setColspan(colspan);
        cell.setPadding(5);
        cell.setBackgroundColor(new BaseColor(240, 240, 240));
        table.addCell(cell);
    }
}