package solomonm.ugo.collector.dbtoexcel.services.imple;

import org.springframework.stereotype.Service;
import solomonm.ugo.collector.dbtoexcel.dto.DbinfoDTO;
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
        return excelInfoMapper.selectData();
    }
}
