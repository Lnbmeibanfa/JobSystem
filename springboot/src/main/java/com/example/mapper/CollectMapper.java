package com.example.mapper;
import com.example.entity.Collect;
import com.example.entity.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CollectMapper {
    int insert(Collect collect);

    @Select("select * from `collect`")
    List<Collect> selectAll();

    List<Collect> selectByPage(Collect collect);

    void update(Collect collect);

    void deleteById(Integer id);

}
