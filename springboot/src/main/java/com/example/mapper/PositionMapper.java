package com.example.mapper;
import com.example.entity.Notice;
import com.example.entity.Position;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PositionMapper {
    int insert(Position position);

    List<Position> selectAll(Position position);

    @Select("select * from `position` where id = #{id}")
    Position selectById(Integer id);

    List<Position> selectByPage(Position position);

    void update(Position position);

    void deleteById(Integer id);

    Position selectByName(Position position);
}
