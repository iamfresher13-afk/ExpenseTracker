package com.example.expensetracker.util;

import android.content.Context;
import android.os.Environment;

import com.example.expensetracker.db.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class ExcelExporter {

    // Writes all entries to an .xlsx file in the app's Documents folder and returns the file.
    public static File export(Context context, List<Entry> entries) throws Exception {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Entries");

        // Header row
        Row header = sheet.createRow(0);
        String[] cols = {"ID", "Type", "Amount", "Date", "Note"};
        for (int i = 0; i < cols.length; i++) {
            Cell c = header.createCell(i);
            c.setCellValue(cols[i]);
        }

        int rowIdx = 1;
        for (Entry e : entries) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(e.id);
            row.createCell(1).setCellValue(e.type);
            row.createCell(2).setCellValue(e.amount);
            row.createCell(3).setCellValue(e.date);
            row.createCell(4).setCellValue(e.note == null ? "" : e.note);
        }

        File dir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        if (dir != null && !dir.exists()) {
            dir.mkdirs();
        }
        File outFile = new File(dir, "expenses_export.xlsx");
        try (FileOutputStream fos = new FileOutputStream(outFile)) {
            wb.write(fos);
        }
        wb.close();
        return outFile;
    }
}
