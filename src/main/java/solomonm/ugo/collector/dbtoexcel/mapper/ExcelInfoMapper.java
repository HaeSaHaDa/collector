package solomonm.ugo.collector.dbtoexcel.mapper;

import org.apache.ibatis.annotations.Mapper;
import solomonm.ugo.collector.dbtoexcel.dto.DbinfoDTO;
import solomonm.ugo.collector.dbtoexcel.dto.ExcelColDTO;

import java.util.List;

@Mapper
public interface ExcelInfoMapper {
    public List<ExcelColDTO> selectData();
}
