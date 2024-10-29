package solomonm.ugo.collector.dbtoexcel.main;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import solomonm.ugo.collector.dbtoexcel.dto.DbinfoDTO;
import solomonm.ugo.collector.dbtoexcel.dto.ExcelColDTO;
import solomonm.ugo.collector.dbtoexcel.services.ExcelInfoService;
import solomonm.ugo.collector.dbtoexcel.util.PreviousMonthConfig;

import java.io.File;
import java.util.List;

@Slf4j
@Component
@PropertySource(value = "classpath:application.yml", encoding = "UTF-8")
public class DBtoExcelMain implements ApplicationRunner {

    private final ExcelInfoService excelInfoService;
    private DbinfoDTO dbinfoDTO;
    @Value("${filegen.filepath}")
    private String filepath;
    @Value("${filegen.filename}")
    private String filename;
    @Value("${filegen.fileExtension}")
    private String fileExtension;
    @Value("${filegen.fileheader}")
    private List<String> fileheader;

    public DBtoExcelMain(ExcelInfoService excelInfoService) {
        this.excelInfoService = excelInfoService;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("---------------------------------------------------------> [ START ]");

        String filePath = String.format("%s%s%s%s월.%s",
                filepath,
                File.separator,
                filename,
                PreviousMonthConfig.lastMonth_MM,
                fileExtension);

        List<ExcelColDTO> dbData = excelInfoService.selectData();

        if (dbData.isEmpty()) {
            log.warn("로드된 데이터가 없습니다. 엑셀 파일 생성을 중단합니다.");
        } else {
            log.info("데이터가 성공적으로 로드되었습니다. 로드된 데이터 개수: {}", dbData.size());
            excelInfoService.fileMake(fileheader, dbData, filePath, fileExtension);
        }

        log.info("------------------------------------------------------------> [ END ]");
    }
}
