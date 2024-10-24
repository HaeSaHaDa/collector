package solomonm.ugo.collector.dbtoexcel.main;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import solomonm.ugo.collector.dbtoexcel.dto.DbinfoDTO;
import solomonm.ugo.collector.dbtoexcel.dto.ExcelColDTO;
import solomonm.ugo.collector.dbtoexcel.services.ExcelInfoService;

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

        for (int i = 0; i<data.size(); i++){
            System.out.println(data.get(i));
        }


        return data;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("-------------------------------->");
        dblist();

    }
}
