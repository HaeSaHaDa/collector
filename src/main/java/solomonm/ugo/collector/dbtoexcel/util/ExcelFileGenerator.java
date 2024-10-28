package solomonm.ugo.collector.dbtoexcel.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import solomonm.ugo.collector.dbtoexcel.dto.ExcelColDTO;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Slf4j
public class ExcelFileGenerator {

    /**
     * Excel title 부분 작성
     * @param sheet
     * @param workbook
     */
    private static void createHeaderRow(List<String> fileheader, Sheet sheet, Workbook workbook) {
        Row headerRow = sheet.createRow(0);
//        String[] headers = {"Month", "ROAD_NAME", "DIR_NAME", "ST_NAME", "ED_NAME", "DISTANCE", "평일평균", "주말평균", "전체평균"};

        CellStyle boldStyle = workbook.createCellStyle();
        Font boldFont = workbook.createFont();
        boldFont.setBold(true);
        boldFont.setFontName("Arial");
        boldFont.setFontHeightInPoints((short) 10);
        boldStyle.setFont(boldFont);

        for (int i = 0; i < fileheader.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(fileheader.get(i));
            cell.setCellStyle(boldStyle);
        }
    }

    /**
     * Excel data 입력 부분 작성
     * @param sheet
     * @param month
     * @param dbData
     */
    private static void populateDataRows(Sheet sheet, String month, List<ExcelColDTO> dbData) {
        final AtomicInteger rowIndex = new AtomicInteger(1);

        IntStream.range(1, dbData.size() + 1).forEach(i -> {

                    ExcelColDTO dto = dbData.get(i - 1); // 인덱스에 맞는 데이터 가져오기

                    Row row = sheet.createRow(rowIndex.getAndIncrement());
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
        );
    }

    /**
     * xlsx 파일 생성 메서드
     * @param filePath
     * @param month
     * @param data
     */
    public static void generateXLSXFile(List<String> fileheader, String filePath, String month, List<ExcelColDTO> data) {
        try (Workbook workbook
                     = new SXSSFWorkbook();
             BufferedOutputStream fileOut
                     = new BufferedOutputStream(new FileOutputStream(filePath));
        ) {
            Sheet sheet = workbook.createSheet("DataSheet");
            createHeaderRow(fileheader, sheet, workbook);
            populateDataRows(sheet, month, data);

            workbook.write(fileOut);

            log.info("xlsx 파일이 생성되었습니다.");
        } catch (IOException e) {
            log.info("xlsx 파일 생성 중 오류가 발생했습니다. {}",e);
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            log.warn("전달된 데이터가 유효하지 않습니다: {}", e.getMessage());
            throw e; // 예외를 다시 던져 호출자에게 알림
        }
    }

    /**
     * xls 파일 생성 메서드
     * @param filePath
     * @param month
     * @param data
     */
    public static void generateXLSFile(List<String> fileheader,String filePath, String month, List<ExcelColDTO> data) {
        try (Workbook workbook
                     = new HSSFWorkbook();
             BufferedOutputStream fileOut
                     = new BufferedOutputStream(new FileOutputStream(filePath));
        ) {
            Sheet sheet = workbook.createSheet("DataSheet");
            createHeaderRow(fileheader, sheet, workbook);
            populateDataRows(sheet, month, data);

            workbook.write(fileOut);

            log.info("xls 파일이 생성되었습니다.");
        } catch (IOException e) {
            throw new RuntimeException("xls 파일 생성 중 오류가 발생했습니다.", e);
        } catch (IllegalArgumentException e) {
            log.warn("전달된 데이터가 유효하지 않습니다: {}", e.getMessage());
            throw e; // 예외를 다시 던져 호출자에게 알림
        }
    }
}
