package solomonm.ugo.collector.dbtoexcel.mapper;

import org.apache.ibatis.annotations.Mapper;
import solomonm.ugo.collector.dbtoexcel.dto.ExcelColDTO;

import java.util.List;

@Mapper
public interface ExcelInfoMapper {
    List<ExcelColDTO> selectData(
            String lastMonthString,
            String startOfLastMonth,
            String endOfLastMonth
    );
}
