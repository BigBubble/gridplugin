package me.pengbo.mapper;

import java.util.List;
import me.pengbo.entity.N;
import me.pengbo.entity.NExample;
import org.apache.ibatis.annotations.Param;

public interface NMapper {
    int countByExample(NExample example);

    int deleteByExample(NExample example);

    int deleteByPrimaryKey(Double n);

    int insert(N record);

    int insertSelective(N record);

    List<N> selectByExample(NExample example);

    int updateByExampleSelective(@Param("record") N record, @Param("example") NExample example);

    int updateByExample(@Param("record") N record, @Param("example") NExample example);
}