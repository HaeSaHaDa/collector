package solomonm.ugo.collector.dbtoexcel.main;


import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import solomonm.ugo.collector.dbtoexcel.dto.DbinfoDTO;
import solomonm.ugo.collector.dbtoexcel.dto.ExcelColDTO;
import solomonm.ugo.collector.dbtoexcel.services.ExcelInfoService;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@PropertySource(value = "classpath:application.yml", encoding = "UTF-8")
public class DBtoExcelMain implements ApplicationRunner {

    private final ExcelInfoService excelInfoService;
    private DbinfoDTO dbinfoDTO;

    public DBtoExcelMain(ExcelInfoService excelInfoService) {
        this.excelInfoService = excelInfoService;
    }


    public List<ExcelColDTO> dblist(){

        List<ExcelColDTO> data = excelInfoService.selectData();

        for (int i = 0; i < data.size(); i++){

            System.out.println(data.get(i));
        }


        return data;
    }

    public void fileMake(){
        Workbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet("DataSheet");
        CellStyle boldStyle = workbook.createCellStyle();
        Font boldFont = workbook.createFont();
        boldStyle.setFont(boldFont);
        Row row = null;

        row = sheet.createRow(0);
        row.createCell(0).setCellValue("202302");
        row.createCell(1).setCellValue("ROAD_NAME");
        row.createCell(2).setCellValue("DIR_NAME");
        row.createCell(3).setCellValue("ST_NAME");
        row.createCell(4).setCellValue("EC_NAME");
        row.createCell(5).setCellValue("DISTANCE");
        row.createCell(6).setCellValue("평일평균");
        row.createCell(7).setCellValue("주말평균");
        row.createCell(8).setCellValue("전체평균");

        // 1000개의 행을 생성
        for (int i = 1; i < 1000; i++) {
            row = sheet.createRow(i);
            row.createCell(0).setCellValue("Row " + i);
            row.createCell(1).setCellValue(i);
        }

        // 파일로 저장
        try (FileOutputStream fileOut = new FileOutputStream("D:\\myProject\\SolomonPj\\LargeExcelFile.xlsx")) {
            workbook.write(fileOut);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 자원 해제
        if (workbook instanceof SXSSFWorkbook) {
            ((SXSSFWorkbook) workbook).dispose();
        }

        System.out.println("대용량 엑셀 파일이 생성되었습니다.");
    }




    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("-------------------------------->");
        dblist();
        fileMake();

    }
}
