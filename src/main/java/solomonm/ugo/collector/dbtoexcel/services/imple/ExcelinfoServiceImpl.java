package solomonm.ugo.collector.dbtoexcel.services.imple;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;
import solomonm.ugo.collector.dbtoexcel.dto.DbinfoDTO;
import solomonm.ugo.collector.dbtoexcel.dto.ExcelColDTO;
import solomonm.ugo.collector.dbtoexcel.mapper.ExcelInfoMapper;
import solomonm.ugo.collector.dbtoexcel.services.ExcelInfoService;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class ExcelinfoServiceImpl implements ExcelInfoService {

    private final ExcelInfoMapper excelInfoMapper;

    public ExcelinfoServiceImpl(ExcelInfoMapper excelInfoMapper) {
        this.excelInfoMapper = excelInfoMapper;
    }

    @Override
    public List<ExcelColDTO> selectData() {
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        String lastMonthString = lastMonth.format(DateTimeFormatter.ofPattern("yyyyMM"));
        LocalDate firstDayOfLastMonth = LocalDate.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDayOfLastMonth = LocalDate.now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());

// 형식을 'yyyyMMdd'로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String startOfLastMonth = firstDayOfLastMonth.format(formatter);
        String endOfLastMonth = lastDayOfLastMonth.format(formatter);
        List<ExcelColDTO> data = excelInfoMapper.selectData(lastMonthString, startOfLastMonth, endOfLastMonth);
        System.out.println(lastMonthString + "//" + startOfLastMonth + "//" + endOfLastMonth);
        System.out.println(data);
        return data;
    }

    /**
     * Excel filemaker
     * @param excelElement
     */
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
//            row.createCell(6).setCellValue(execlinfo.getWeekDay_avg());
//            row.createCell(7).setCellValue(execlinfo.getWeekEnd_avg());
//            row.createCell(8).setCellValue(execlinfo.getAll_avg());
        }

        // 파일로 저장
        try (FileOutputStream fileOut = new FileOutputStream("C:\\Users\\sixth\\Desktop\\test\\LargeExcelFile.xlsx")) {
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
}
