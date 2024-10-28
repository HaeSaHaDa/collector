package solomonm.ugo.collector.dbtoexcel.services.imple;

import org.springframework.stereotype.Service;
import solomonm.ugo.collector.dbtoexcel.util.ExcelFileGenerator;
import solomonm.ugo.collector.dbtoexcel.util.PreviousMonthConfig;
import solomonm.ugo.collector.dbtoexcel.dto.ExcelColDTO;
import solomonm.ugo.collector.dbtoexcel.mapper.ExcelInfoMapper;
import solomonm.ugo.collector.dbtoexcel.services.ExcelInfoService;

import java.util.List;


@Service
public class ExcelinfoServiceImpl implements ExcelInfoService {

    private final ExcelInfoMapper excelInfoMapper;

    public ExcelinfoServiceImpl(ExcelInfoMapper excelInfoMapper) {
        this.excelInfoMapper = excelInfoMapper;
    }

    @Override
    public List<ExcelColDTO> selectData() {

        return excelInfoMapper.selectData(PreviousMonthConfig.lastMonthString, solomonm.ugo.collector.dbtoexcel.util.PreviousMonthConfig.startOfLastMonth, solomonm.ugo.collector.dbtoexcel.util.PreviousMonthConfig.endOfLastMonth);
    }

    /**
     * Excel 파일 생성 메인 메소드
     *
     * @param excelElement Excel에 저장할 데이터 리스트
     */
    public void fileMake(List<ExcelColDTO> excelElement) {
        String filePath = "C:\\Users\\sixth\\Desktop\\test\\LargeExcelFile.xlsx";
        ExcelFileGenerator.generateExcelFile(filePath, PreviousMonthConfig.lastMonthString, excelElement);
    }
}
