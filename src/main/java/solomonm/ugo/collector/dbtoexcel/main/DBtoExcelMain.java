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


    public List<ExcelColDTO> dblist() {

        List<ExcelColDTO> data = excelInfoService.selectData();

        for (int i = 0; i < data.size(); i++) {

            System.out.println(data.get(i));
        }


        return data;
    }

    public void fileMake(List<ExcelColDTO> excelElement) {
        Workbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet("DataSheet");
        CellStyle boldStyle = workbook.createCellStyle();
        Font boldFont = workbook.createFont();
        boldStyle.setFont(boldFont);
        ExcelColDTO execlinfo = null;
        Row row = null;

        row = sheet.createRow(0);
        row.createCell(0).setCellValue("202302");
        row.createCell(1).setCellValue("ROAD_NAME");
        row.createCell(2).setCellValue("DIR_NAME");
        row.createCell(3).setCellValue("ST_NAME");
        row.createCell(4).setCellValue("ED_NAME");
        row.createCell(5).setCellValue("DISTANCE");
        row.createCell(6).setCellValue("평일평균");
        row.createCell(7).setCellValue("주말평균");
        row.createCell(8).setCellValue("전체평균");

        // 1000개의 행을 생성
        for (int i = 1; i < excelElement.size(); i++) {
            execlinfo = excelElement.get(i - 1);
            row = sheet.createRow(i);
            row.createCell(0).setCellValue("202302");
            row.createCell(1).setCellValue(execlinfo.getRoad_name());
            row.createCell(2).setCellValue(execlinfo.getDir_name());
            row.createCell(3).setCellValue(execlinfo.getSt_name());
            row.createCell(4).setCellValue(execlinfo.getEd_name());
            row.createCell(5).setCellValue(execlinfo.getDistance());
//            row.createCell(6).setCellValue(execlinfo.getWeekDay_avg().isNaN()?0:execlinfo.getWeekDay_avg());
//            row.createCell(7).setCellValue(execlinfo.getWeekEnd_avg().isNaN()?0:execlinfo.getWeekEnd_avg());
//            row.createCell(8).setCellValue(execlinfo.getAll_avg().isNaN()?0:execlinfo.getAll_avg());
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
        fileMake(dblist());

    }
}
