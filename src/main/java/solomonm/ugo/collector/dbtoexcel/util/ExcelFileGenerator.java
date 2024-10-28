package solomonm.ugo.collector.dbtoexcel.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import solomonm.ugo.collector.dbtoexcel.dto.ExcelColDTO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelFileGenerator {
    public static void generateExcelFile(String filePath, String month, List<ExcelColDTO> data) {
        try (Workbook workbook = new SXSSFWorkbook(); FileOutputStream fileOut = new FileOutputStream(filePath)) {
            Sheet sheet = workbook.createSheet("DataSheet");
            createHeaderRow(sheet, workbook);
            populateDataRows(sheet, month, data);

            workbook.write(fileOut);
            System.out.println("대용량 엑셀 파일이 생성되었습니다.");
        } catch (IOException e) {
            throw new RuntimeException("엑셀 파일 생성 중 오류가 발생했습니다.", e);
        }
    }

    private static void createHeaderRow(Sheet sheet, Workbook workbook) {
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Month", "ROAD_NAME", "DIR_NAME", "ST_NAME", "ED_NAME", "DISTANCE", "평일평균", "주말평균", "전체평균"};

        CellStyle boldStyle = workbook.createCellStyle();
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        boldStyle.setFont(boldFont);

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(boldStyle);
        }
    }

    private static void populateDataRows(Sheet sheet, String month, List<ExcelColDTO> data) {
        for (int i = 0; i < data.size(); i++) {
            ExcelColDTO dto = data.get(i);
            Row row = sheet.createRow(i + 1);

            row.createCell(0).setCellValue(month);
            row.createCell(1).setCellValue(dto.getRoad_name());
            row.createCell(2).setCellValue(dto.getDir_name());
            row.createCell(3).setCellValue(dto.getSt_name());
            row.createCell(4).setCellValue(dto.getEd_name());
            row.createCell(5).setCellValue(dto.getDistance() != null ? dto.getDistance() : 0.0);
            row.createCell(6).setCellValue(dto.getWeekDay_avg() != null ? dto.getWeekDay_avg() : 0.0);
            row.createCell(7).setCellValue(dto.getWeekEnd_avg() != null ? dto.getWeekEnd_avg() : 0.0);
            row.createCell(8).setCellValue(dto.getAll_avg() != null ? dto.getAll_avg() : 0.0);
        }
    }
}
