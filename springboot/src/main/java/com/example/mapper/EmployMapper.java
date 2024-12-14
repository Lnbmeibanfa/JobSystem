package com.example.mapper;
import com.example.entity.Employ;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployMapper {
    int insert(Employ employ);

    @Select("select * from `employ` where id = #{id}")
    Employ selectById(int id);

    @Select("select * from `employ`")
    List<Employ> selectAll();

    List<Employ> selectByPage(Employ employ);

    void update(Employ employ);

    void deleteById(Integer id);

    Employ selectByUsername(String username);
}
