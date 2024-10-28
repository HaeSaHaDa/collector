package solomonm.ugo.collector.dbtoexcel.services;

import org.springframework.stereotype.Service;
import solomonm.ugo.collector.dbtoexcel.dto.DbinfoDTO;
import solomonm.ugo.collector.dbtoexcel.dto.ExcelColDTO;
import solomonm.ugo.collector.dbtoexcel.mapper.ExcelInfoMapper;

import java.util.List;

public interface ExcelInfoService {
    List<ExcelColDTO> selectData();
    public void fileMake(List<ExcelColDTO> excelElement);
}
