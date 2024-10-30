package solomonm.ugo.collector.dbtoexcel.services.imple;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import solomonm.ugo.collector.dbtoexcel.dto.ExcelColDTO;
import solomonm.ugo.collector.dbtoexcel.mapper.ExcelInfoMapper;
import solomonm.ugo.collector.dbtoexcel.services.ExcelInfoService;
import solomonm.ugo.collector.dbtoexcel.util.ExcelFileGenerator;
import solomonm.ugo.collector.dbtoexcel.util.PreviousMonthConfig;

import java.util.List;


@Slf4j
@Service
public class ExcelinfoServiceImpl implements ExcelInfoService {

    private final ExcelInfoMapper excelInfoMapper;
    private final ExcelFileGenerator excelFileGenerator;

    public ExcelinfoServiceImpl(ExcelInfoMapper excelInfoMapper, ExcelFileGenerator excelFileGenerator) {
        this.excelInfoMapper = excelInfoMapper;
        this.excelFileGenerator = excelFileGenerator;
    }

    @Override
    public List<ExcelColDTO> selectData() {
        log.info("db로부터 데이터 수집.");
        return excelInfoMapper.selectData(
                PreviousMonthConfig.lastMonth_yyyyMM,
                PreviousMonthConfig.firstDay_yyyyMMdd,
                PreviousMonthConfig.lastDay_yyyyMMdd
        );
    }


    /**
     * Excel 파일 생성 메인 메소드
     * 파일 확장자가 xlsx인 경우, xls인 경우로 나뉜어 생성된다.
     *
     * @param filePath  - 파일 경로
     * @param extension - 파일 확장자
     */
    @Override
    public void fileMake(List<String> fileheader, List<ExcelColDTO> dbData, String filePath, String extension) {
        try {
            if (extension.equals("xlsx")) {
                log.info("xlsx 파일 생성(경로: {})", filePath);
                excelFileGenerator.generate_XLSX_File(
                        fileheader,
                        filePath,
                        PreviousMonthConfig.lastMonth_yyyyMM,
                        dbData
                );
                log.info("엑셀 파일이 성공적으로 생성되었습니다. 경로: {}", filePath);

            } else if (extension.equals("xls") || extension.equals("cell")) {
                log.info("xls 파일 생성(경로: {})", filePath);
                excelFileGenerator.generate_XLS_File(
                        fileheader,
                        filePath,
                        PreviousMonthConfig.lastMonth_yyyyMM,
                        dbData
                );
                log.info("엑셀 파일이 성공적으로 생성되었습니다. 경로: {}", filePath);

            } else {
                log.error("데이터 확장자는 'xlsx, xls, cell' 중 하나를 선택해 주세요.");
            }

        } catch (Exception e) {
            log.error("파일 생성 중 오류 발생: {}", e.getMessage());
        }
    }


}
