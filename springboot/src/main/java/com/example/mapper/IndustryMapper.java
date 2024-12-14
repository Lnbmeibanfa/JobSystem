package com.example.mapper;
import com.example.entity.Industry;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface IndustryMapper {
    int insert(Industry industry);

    @Select("select * from `industry`")
    List<Industry> selectAll();

    List<Industry> selectByPage(Industry industry);

    void update(Industry industry);

    void deleteById(Integer id);

    Industry selectByTitle(Industry industry);
}
