package com.example.mapper;
import com.example.entity.Submit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SubmitMapper {
    int insert(Submit submit);

    List<Submit> selectAll(Integer userId);

    List<Submit> selectByPage(Submit submit);

    void update(Submit submit);

    void deleteById(Integer id);

    @Select("select * from `submit` where user_id = #{userId} and position_id = #{positionId}")
    List<Submit> selectByUserIdAndPositionId(Integer userId, Integer positionId);
}
